package com.example.world_present_socialnetwork.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.internal.notify

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter
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

            override fun onClickLike(idPost: String) {
                idUser?.let { idUser ->
                    CoroutineScope(Dispatchers.Main).launch {
                        likePost(idUser, idPost)
                        getAllPost(idUser)
                        postAdapter.notifyDataSetChanged()
                    }
                }
                Log.e("TAG main", "Like: $idUser")
                Log.e("TAG main", "Like: $idPost")
            }

            override fun onClickMenu(idPost: String,isOwner: Boolean,view:View) {
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
                            Toast.makeText(requireContext(), "update: $idPost", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete ->{
                            Toast.makeText(requireContext(), "delete: $idPost", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popupMenu.show()
            }
        })
        binding.swipeToRefresh.setOnRefreshListener {
            if(binding.swipeToRefresh.isRefreshing){
                idUser?.let { getAllPost(it) }
            }
        }
        idUser?.let { getAllPost(it) }
    }
    private fun getAllPost(idUser:String){
        postController.getAllPost(idUser) { list, error ->
            if (list != null) {
                // Cập nhật danh sách bài viết trên postAdapter
                postAdapter.updateDataPost(list as MutableList<PostsExtend>)
                binding.swipeToRefresh.isRefreshing = false
                idUser.let { postAdapter.setUserId(it) }
                Log.e("TAG", "getPost" )
            } else {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "getPost: $error" )
            }
        }
    }
    private fun likePost(idUser:String,idPost:String){
        likeController.likePost(idUser,idPost){like,error->
            if(like!=null){
                Toast.makeText(requireContext(), "Bạn đã thích bài viết.", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Like: $like" )
                postAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(requireContext(), "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Like: $error" )
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