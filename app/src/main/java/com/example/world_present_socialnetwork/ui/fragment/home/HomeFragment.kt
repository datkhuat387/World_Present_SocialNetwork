package com.example.world_present_socialnetwork.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.PostAdapter
import com.example.world_present_socialnetwork.controllers.LikeController
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.FragmentHomeBinding
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.ui.comment.CommentActivity
import com.example.world_present_socialnetwork.ui.post.addPost.AddPostActivity
import com.example.world_present_socialnetwork.ui.post.updatePost.UpdatePostActivity
import com.example.world_present_socialnetwork.ui.user.profile.MyProfileActivity
import com.example.world_present_socialnetwork.ui.user.profile.ProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter
    private var listPost: MutableList<PostsExtend> = mutableListOf()
    private val postController = PostController()
    private val likeController = LikeController()
    private var idUser:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postAdapter = PostAdapter()
        binding.recycleviewPost.adapter = postAdapter

        val sharedPreferences = activity?.getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        binding.shimmerFLHome.visibility = View.VISIBLE
        binding.shimmerFLHome.startShimmer()
        idUser?.let { getAllPost(it) }
//        shimmer.stopShimmer()
        binding.imgAddPost.setOnClickListener {
            val intent = Intent(requireContext(),AddPostActivity::class.java)
            startActivity(intent)
        }
        binding.imgSearch.setOnClickListener {

        }
        postAdapter.setListener(object : PostAdapter.PostListener{
            override fun onClickComment(idPost: String) {
                val intent = Intent(requireContext(), CommentActivity::class.java)
                intent.putExtra("idPost",idPost)
                startActivity(intent)
            }

            override fun onClickLike(post: PostsExtend) {
                idUser?.let { idUser ->
                    CoroutineScope(Dispatchers.Main).launch {
                        postAdapter.setUserId(idUser)
                        likePost(idUser,post)
                    }
                }
                Log.e("TAG main", "Like: $idUser")
                Log.e("TAG main", "Like: ${post._id}")
            }
            override fun onClickMenu(post: PostsExtend,isOwner: Boolean,view:View) {
                val popupMenu = PopupMenu(requireContext(),view)
                Log.e("TAG", "isPost: $isOwner" )
                popupMenu.inflate(R.menu.menu_post)
                if(isOwner){
                    popupMenu.menu.findItem(R.id.menu_update).isVisible = true
                    popupMenu.menu.findItem(R.id.menu_delete).isVisible = true
                    popupMenu.menu.findItem(R.id.menu_savePost).isVisible = true
                }else{
                    popupMenu.menu.findItem(R.id.menu_update).isVisible = false
                    popupMenu.menu.findItem(R.id.menu_delete).isVisible = false
                    popupMenu.menu.findItem(R.id.menu_savePost).isVisible = true
                }
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_update ->{
                            val intent = Intent(requireContext(),UpdatePostActivity::class.java)
                            intent.putExtra("idPost",post._id)
                            startActivity(intent)
                            Toast.makeText(requireContext(), "update: ${post._id}", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete ->{
                            removePost(post)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_savePost ->{
                            Toast.makeText(requireContext(), "savePost: ${post._id}", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popupMenu.show()
            }

            override fun onClickProfile(idUserAt: String) {
                if(idUser==idUserAt){
                    val intent = Intent(requireContext(), MyProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }else{
                    val intent = Intent(requireContext(), ProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }

            }
        })
        binding.swipeToRefresh.setOnRefreshListener {
            if(binding.swipeToRefresh.isRefreshing){
                idUser?.let { getAllPost(it) }
            }
        }
    }
    private fun getAllPost(idUser:String){
        postController.getAllPost(idUser) { list, error ->
            activity?.runOnUiThread {
                if (list != null) {
                    postAdapter.setUserId(idUser)
                    listPost = list.toMutableList()
                    postAdapter.updateDataPost(listPost)
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("TAG", "getPost" )
                } else {
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "getPost: $error" )
                }
            }
        }
    }
    private fun likePost(idUser:String,post: PostsExtend){
        post._id?.let {
            likeController.likePost(idUser, it){ like, error->
                activity?.runOnUiThread {
                    if(like!=null){
                        val updateLike = listPost.find { it._id == post._id }
                        updateLike?.isLiked = true
                        updateLike?.likeCount = updateLike?.likeCount?.plus(1)
                        postAdapter.updateDataPost(listPost)
                        Toast.makeText(requireContext(), "Bạn đã thích bài viết.", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "Like: $like" )
                    }else{
                        Toast.makeText(requireContext(), "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "Like: $error" )
                    }
                }
            }
        }
    }
    private fun removePost(post: PostsExtend){
        post._id?.let {
            postController.removePost(it){ removePost, error->
                if(removePost!=null){
                    listPost.remove(post)
                    postAdapter.updateDataPost(listPost)
                    Toast.makeText(requireContext(), "Đã xóa bài viết.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "removePost: $removePost" )
                }else{
                    Toast.makeText(requireContext(), "$error .", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "removePost: $error" )
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        idUser?.let { getAllPost(it) }
        Log.e("HomeFragment", "onResume" )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("HomeFragment", "onDestroy" )
        _binding = null
    }
}