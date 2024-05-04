package com.example.world_present_socialnetwork.ui.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.PostAdapter
import com.example.world_present_socialnetwork.adapter.PostGroupAdapter
import com.example.world_present_socialnetwork.controllers.GroupController
import com.example.world_present_socialnetwork.controllers.LikeController
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.ActivityMyGroupBinding
import com.example.world_present_socialnetwork.model.like.LikeExtend
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.ui.comment.CommentActivity
import com.example.world_present_socialnetwork.ui.group.manage.ManageActivity
import com.example.world_present_socialnetwork.ui.post.addPost.AddPostActivity
import com.example.world_present_socialnetwork.ui.post.updatePost.UpdatePostActivity
import com.example.world_present_socialnetwork.ui.user.profile.MyProfileActivity
import com.example.world_present_socialnetwork.ui.user.profile.ProfileActivity
import com.example.world_present_socialnetwork.utils.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyGroupBinding
    private val groupController = GroupController()
    private val postController = PostController()
    private val likeController = LikeController()
    private lateinit var postGroupAdapter: PostGroupAdapter
    private var listPost: MutableList<PostsExtend> = mutableListOf()
    private var idGroup: String? = null
    private var idUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postGroupAdapter = PostGroupAdapter()
        binding.rcvPost.adapter = postGroupAdapter

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        idGroup = intent.getStringExtra("idGroup")
        idGroup?.let { getGroupDetail(it) }
        idGroup?.let { idUser?.let { it1 -> getPostByIdGroup(it, it1) } }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnManage.setOnClickListener {
            val intent = Intent(this,ManageActivity::class.java)
            startActivity(intent)
        }
        binding.tvPost.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            intent.putExtra("idGroup",idGroup)
            startActivity(intent)
        }
        postGroupAdapter.setListener(object : PostGroupAdapter.PostListener{
            override fun onClickComment(idPost: String) {
                val intent = Intent(this@MyGroupActivity, CommentActivity::class.java)
                intent.putExtra("idPost",idPost)
                startActivity(intent)
            }

            override fun onClickLike(like: LikeExtend?, post: PostsExtend) {
                if(like==null){
                    idUser?.let { idUser ->
                        CoroutineScope(Dispatchers.Main).launch {
                            postGroupAdapter.setUserId(idUser)
                            likePost(idUser,post)
                        }
                    }
                    Log.e("TAG main", "Like: $idUser")
                    Log.e("TAG main", "Like: ${post._id}")
                }else{
                    removeLikePost(like,post)
//                    Log.e("TAG main remove", "Like: $id")
                    Log.e("TAG  remove", "Like: ${post._id}")
                }
            }
            override fun onClickMenu(post: PostsExtend, isOwner: Boolean, view: View) {
                val popupMenu = PopupMenu(this@MyGroupActivity,view)
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
                            val intent = Intent(this@MyGroupActivity, UpdatePostActivity::class.java)
                            intent.putExtra("idPost",post._id)
                            startActivity(intent)
                            Toast.makeText(this@MyGroupActivity, "update: ${post._id}", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete ->{
                            removePost(post)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_savePost ->{
                            Toast.makeText(this@MyGroupActivity, "savePost: ${post._id}", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popupMenu.show()
            }

            override fun onClickProfile(idUserAt: String) {
                if(idUser==idUserAt){
                    val intent = Intent(this@MyGroupActivity, MyProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@MyGroupActivity, ProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }
            }
        })
        binding.swipeToRefresh.setOnRefreshListener {
            if(binding.swipeToRefresh.isRefreshing){
                onResume()
            }
        }
    }
    private fun getGroupDetail(idGroup: String){
        groupController.getGroupDetail(idGroup){group,error->
            if(group!=null){
                Glide.with(applicationContext)
                    .load(Common.baseURL+ group.coverImage)
                    .placeholder(R.drawable.cover_image_default)
                    .error(R.drawable.cover_image_default)
                    .into(binding.imgCoverGr)

                Glide.with(applicationContext)
                    .load(Common.baseURL+ group.coverImage)
                    .placeholder(R.drawable.cover_image_default)
                    .error(R.drawable.cover_image_default)
                    .into(binding.imgCoverGrEx)

                binding.tvNameGr.text = group.name
                binding.tvNameGr2.text = group.name
            }else{
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPostByIdGroup(idGroup: String, idUser: String){
        postController.getPostByIdGroup(idGroup,idUser){postEx, error->
            if(postEx!=null){
                postGroupAdapter.setUserId(idUser)
                listPost = postEx.toMutableList()
                postGroupAdapter.updateDataPost(listPost)
                binding.swipeToRefresh.isRefreshing = false
            }else{
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
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
                    postGroupAdapter.updateDataPost(listPost)
                    Toast.makeText(this, "Bạn đã thích bài viết.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Like: $like" )
                }else{
                    Toast.makeText(this, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
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
                    postGroupAdapter.updateDataPost(listPost)
                    Toast.makeText(this, "Bạn đã bỏ thích bài viết.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "Like: $like" )
                }else{
                    Toast.makeText(this, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
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
                    postGroupAdapter.updateDataPost(listPost)
                    Toast.makeText(this, "Đã xóa bài viết.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "removePost: $removePost" )
                }else{
                    Toast.makeText(this, "$error .", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "removePost: $error" )
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        idGroup?.let { getGroupDetail(it) }
        idGroup?.let { idUser?.let { it1 -> getPostByIdGroup(it, it1) } }
    }
}