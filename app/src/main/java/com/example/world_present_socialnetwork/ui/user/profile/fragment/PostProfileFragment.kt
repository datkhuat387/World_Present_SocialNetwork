package com.example.world_present_socialnetwork.ui.user.profile.fragment

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
import com.example.world_present_socialnetwork.adapter.FriendAdapter
import com.example.world_present_socialnetwork.adapter.PostAdapter
import com.example.world_present_socialnetwork.controllers.FriendshipController
import com.example.world_present_socialnetwork.controllers.LikeController
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.FragmentPostProfileBinding
import com.example.world_present_socialnetwork.model.friend.FriendshipsExtend
import com.example.world_present_socialnetwork.model.like.LikeExtend
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.ui.comment.CommentActivity
import com.example.world_present_socialnetwork.ui.friend.ListFriendActivity
import com.example.world_present_socialnetwork.ui.group.GroupDetailActivity
import com.example.world_present_socialnetwork.ui.group.MyGroupActivity
import com.example.world_present_socialnetwork.ui.post.updatePost.UpdatePostActivity
import com.example.world_present_socialnetwork.ui.user.profile.MyProfileActivity
import com.example.world_present_socialnetwork.ui.user.profile.ProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostProfileFragment(val idUserAt: String) : Fragment() {
    private var _binding: FragmentPostProfileBinding? = null
    private lateinit var postAdapter: PostAdapter
    private lateinit var friendAdapter: FriendAdapter
    private var listPost: MutableList<PostsExtend> = mutableListOf()
    private var listFriend: MutableList<FriendshipsExtend> = mutableListOf()
    private val postController = PostController()
    private val likeController = LikeController()
    private val friendshipController = FriendshipController()
    private var idUser:String? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPostProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendAdapter = FriendAdapter()
        postAdapter = PostAdapter()

        binding.rcvListFriend.adapter = friendAdapter
        binding.recycleviewPost.adapter = postAdapter


        val sharedPreferences = activity?.getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUser?.let { getPostByIdUser(it,idUserAt) }
        getListFriend(idUserAt)
        binding.btnAllFriend.setOnClickListener {
            val intent = Intent(requireContext(),ListFriendActivity::class.java)
            intent.putExtra("idUserAt",idUserAt)
            startActivity(intent)
        }
        postAdapter.setListener(object : PostAdapter.PostListener{
            override fun onClickComment(idPost: String) {
                val intent = Intent(requireContext(), CommentActivity::class.java)
                intent.putExtra("idPost",idPost)
                startActivity(intent)
            }

            override fun onClickLike(like: LikeExtend?, post: PostsExtend) {
                if(like==null){
                    idUser?.let { idUser ->
                        CoroutineScope(Dispatchers.Main).launch {
                            postAdapter.setUserId(idUser)
                            likePost(idUser,post)
                        }
                    }
                    Log.e("TAG main", "Like: $idUser")
                    Log.e("TAG main", "Like: ${post._id}")
                }else{
                    removeLikePost(like,post)
                    Log.e("TAG main remove", "Like: $id")
                    Log.e("TAG  remove", "Like: ${post._id}")
                }
            }
            override fun onClickMenu(post: PostsExtend, isOwner: Boolean, view:View) {
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
                            val intent = Intent(requireContext(), UpdatePostActivity::class.java)
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
//                if(idUser==idUserAt){
//                    val intent = Intent(requireContext(), MyProfileActivity::class.java)
//                    intent.putExtra("idUserAt",idUserAt)
//                    startActivity(intent)
//                }else{
//                    val intent = Intent(requireContext(), ProfileActivity::class.java)
//                    intent.putExtra("idUserAt",idUserAt)
//                    startActivity(intent)
//                }
            }

            override fun onClickGroup(idGroup: String, idCreator: String) {
//                if(idUser==idCreator){
//                    val intent = Intent(requireContext(), MyGroupActivity::class.java)
//                    intent.putExtra("idGroup",idGroup)
//                    startActivity(intent)
//                }else{
//                    val intent = Intent(requireContext(), GroupDetailActivity::class.java)
//                    intent.putExtra("idGroup",idGroup)
//                    startActivity(intent)
//                }
            }
        })

    }
    private fun getPostByIdUser(idUser: String,idUserAt: String){
        postController.getPostByIdUser(idUserAt,idUser){list,error->
            if(list!=null){
                postAdapter.setUserId(idUser)
                listPost = list.toMutableList()
                postAdapter.updateDataPost(listPost)
            }else{
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getListFriend(idUserAt: String){
        friendshipController.getListFriend(idUserAt){friend, error->
            if(friend!=null){
                friendAdapter.setUserId(idUserAt)
                listFriend = friend
                friendAdapter.updateDataPost(listFriend)
            }else{
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun likePost(idUser:String,post: PostsExtend){
        post._id?.let {
            likeController.likePost(idUser, it){ like, error->
                if(like!=null){
                    val updateLike = listPost.find { it._id == post._id }
                    updateLike?.isLiked = true
                    updateLike?.like?.add(like)
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
    private fun removeLikePost(like: LikeExtend, post: PostsExtend){
        like._id?.let {
            likeController.removeLike(it){ like, error->
                if(like!=null){
                    val updateLike = listPost.find { it._id == post._id }
                    updateLike?.isLiked = false
                    updateLike?.like?.remove(like)
                    updateLike?.likeCount = updateLike?.likeCount?.minus(1)
                    postAdapter.updateDataPost(listPost)
                    Toast.makeText(requireContext(), "Bạn đã bỏ thích bài viết.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Like: $like" )
                }else{
                    Toast.makeText(requireContext(), "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Like: $error" )
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
        idUser?.let { getPostByIdUser(it,idUserAt) }
    }
}