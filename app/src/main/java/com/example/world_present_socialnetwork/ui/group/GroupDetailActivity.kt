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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.PostGroupAdapter
import com.example.world_present_socialnetwork.controllers.GroupController
import com.example.world_present_socialnetwork.controllers.GroupMemberController
import com.example.world_present_socialnetwork.controllers.LikeController
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.ActivityGroupDetailBinding
import com.example.world_present_socialnetwork.model.like.LikeExtend
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.ui.comment.CommentActivity
import com.example.world_present_socialnetwork.ui.post.addPost.AddPostActivity
import com.example.world_present_socialnetwork.ui.post.updatePost.UpdatePostActivity
import com.example.world_present_socialnetwork.ui.user.profile.MyProfileActivity
import com.example.world_present_socialnetwork.ui.user.profile.ProfileActivity
import com.example.world_present_socialnetwork.utils.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupDetailBinding
    private val groupController = GroupController()
    private val postController = PostController()
    private val likeController = LikeController()
    private val groupMemberController = GroupMemberController()
    private lateinit var postGroupAdapter: PostGroupAdapter
    private var listPost: MutableList<PostsExtend> = mutableListOf()
    private var idGroup: String? = null
    private var idUser: String? = null
    private var isJoin: Boolean? = false
    private var idGroupMember: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postGroupAdapter = PostGroupAdapter()
        binding.rcvPost.adapter = postGroupAdapter

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        idGroup = intent.getStringExtra("idGroup")
        idGroup?.let { getGroupDetail(it) }
        idGroup?.let { idUser?.let { it1 -> getPostByIdGroup(it, it1) } }
        idUser?.let { idGroup?.let { it1 -> getJoin(it1, it) } }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnJoinGroup.setOnClickListener {
            if(isJoin==true){
                idGroupMember?.let { it1 -> outGroup(it1) }
            }else{
                idGroup?.let { it1 -> idUser?.let { it2 -> joinGroup(it1, it2) } }
            }
        }
        binding.tvPost.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            intent.putExtra("idGroup",idGroup)
            startActivity(intent)
        }
        postGroupAdapter.setListener(object : PostGroupAdapter.PostListener{
            override fun onClickComment(idPost: String) {
                if(isJoin==true){
                    val intent = Intent(this@GroupDetailActivity, CommentActivity::class.java)
                    intent.putExtra("idPost",idPost)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@GroupDetailActivity,"Bạn chưa tham gia nhóm",Toast.LENGTH_SHORT).show()
                }

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
                val popupMenu = PopupMenu(this@GroupDetailActivity,view)
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
                            val intent = Intent(this@GroupDetailActivity, UpdatePostActivity::class.java)
                            intent.putExtra("idPost",post._id)
                            startActivity(intent)
                            Toast.makeText(this@GroupDetailActivity, "update: ${post._id}", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete ->{
                            removePost(post)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_savePost ->{
                            Toast.makeText(this@GroupDetailActivity, "savePost: ${post._id}", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popupMenu.show()
            }

            override fun onClickProfile(idUserAt: String) {
                if(idUser==idUserAt){
                    val intent = Intent(this@GroupDetailActivity, MyProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@GroupDetailActivity, ProfileActivity::class.java)
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
    private fun getJoin(idGroup: String,idUser: String){
        groupMemberController.getJoin(idGroup,idUser){group, error->
            if(group!=null){
                idGroupMember = group._id
                if(group.status==0){
                    binding.btnInvite.visibility = View.GONE
                    binding.btnJoinGroup.text = "Chờ phê duyệt"
                    binding.btnJoinGroup.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                    binding.btnJoinGroup.setTextColor(ContextCompat.getColor(this,R.color.black))
                    binding.btnJoinGroup.setBackgroundDrawable(
                        ContextCompat.getDrawable(this,
                        R.drawable.shape_btn_profile_2))
                }else if(group.status==1){
                    isJoin = true
                    binding.btnInvite.visibility = View.VISIBLE
                    binding.btnJoinGroup.text = "Đã tham gia"
                    binding.btnJoinGroup.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                    binding.btnJoinGroup.setTextColor(ContextCompat.getColor(this,R.color.black))
                    binding.btnJoinGroup.setBackgroundDrawable(
                        ContextCompat.getDrawable(this,
                            R.drawable.shape_btn_profile_2))
                }else if(group.status==2){
                    Toast.makeText(this,"Bạn đã bị chặn",Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this,"Lỗi",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else{
//                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun joinGroup(idGroup: String,idUser: String){
        groupMemberController.joinGroup(idGroup,idUser){groupMember,error->
            if(groupMember!=null){
                binding.btnInvite.visibility = View.GONE
                binding.btnJoinGroup.text = "Chờ phê duyệt"
                binding.btnJoinGroup.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                binding.btnJoinGroup.setTextColor(ContextCompat.getColor(this,R.color.black))
                binding.btnJoinGroup.setBackgroundDrawable(
                    ContextCompat.getDrawable(this,
                        R.drawable.shape_btn_profile_2))
            }else{
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun outGroup(idGroupMember: String){
        groupMemberController.outGroup(idGroupMember){string, error->
            if(string!=null){
                Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
                isJoin = false
                binding.btnInvite.visibility = View.GONE
                binding.btnJoinGroup.text = "Tham gia nhóm"
                binding.btnJoinGroup.setBackgroundColor(ContextCompat.getColor(this,R.color.blue))
                binding.btnJoinGroup.setTextColor(ContextCompat.getColor(this,R.color.white))
                binding.btnJoinGroup.setBackgroundDrawable(
                    ContextCompat.getDrawable(this,
                        R.drawable.shape_btn_profile))
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