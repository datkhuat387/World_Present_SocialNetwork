package com.example.world_present_socialnetwork.ui.comment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.CommentAdapter
import com.example.world_present_socialnetwork.controllers.CommentController
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.ActivityCommentBinding
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.utils.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private var idPost: String? = null
    private var idUser: String? = null
    private lateinit var commentAdapter: CommentAdapter
    private val commentController = CommentController()
    private val postController = PostController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idPost = intent.getStringExtra("idPost")
        idUser = sharedPreferences?.getString("id","")
        ///
        commentAdapter = CommentAdapter()
        binding.rcvListCmt.adapter = commentAdapter
        binding.rcvListCmt.layoutManager = GridLayoutManager(this,1)

        idPost?.let { getDetailPost(it) }
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val currentText = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.imgSend.visibility = View.VISIBLE
                } else {
                    binding.imgSend.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val newText = s.toString()
            }

        }
        binding.edCmt.addTextChangedListener(textWatcher)
        commentAdapter.setListener(object :CommentAdapter.CommentListener{
            override fun onLongClickComment(idComment: String) {
                TODO("Not yet implemented")
            }

        })
        binding.imgSend.setOnClickListener {
            val textCmt = binding.edCmt.text.toString()
            if(textCmt.isEmpty()){
                Toast.makeText(
                    this@CommentActivity,
                    "Bạn chưa nhập bình luận",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            idPost?.let { it1 -> idUser?.let { it2 -> comment(it1, it2,textCmt) } }
            idPost?.let { it1 -> getDetailPost(it1) }
//            Toast.makeText(this@CommentActivity, "Text: $textCmt",Toast.LENGTH_SHORT).show()
        }
    }

    private fun comment(idPost: String, idUser: String, comment: String){
        commentController.comment(idUser,idPost,comment){comment, error->
            runOnUiThread {
                if(comment!=null){
                    binding.edCmt.text.clear()
                    binding.edCmt.isEnabled = false
                    binding.imgSend.isEnabled = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.edCmt.isEnabled = true
                        binding.imgSend.isEnabled = true
                    }, 1000)
                    Toast.makeText(this@CommentActivity, "Đã bình luận bài viết", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@CommentActivity, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getListComment(idPost: String){
        commentController.getListCommentByIdPost(idPost){listCmt, error->
            if(listCmt!=null){
                commentAdapter.updateComment(listCmt)
            }else{
                Toast.makeText(this@CommentActivity, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getDetailPost(idPost: String){
        postController.getDetailPost(idPost){detailPost, error->
            runOnUiThread {
                if(detailPost!=null){
                    binding.tvName.text = detailPost.idUser.fullname
                    binding.tvDate.text = detailPost.updateAt?.let { Common.formatDateTime(it) }
                    binding.tvContent.text = detailPost.content
                    ////////
                    if(detailPost.content == null||detailPost.content == ""){
                        binding.tvContent.visibility = View.GONE
                        val layoutParams = binding.imagePost.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.topMargin = 50
                        binding.imagePost.layoutParams = layoutParams
                    }
                    ////////
                    if(detailPost.image == null||detailPost.image == ""){
                        binding.imagePost.visibility = View.GONE
                    }else{
                        binding.imagePost.visibility = View.VISIBLE
                        Glide.with(applicationContext)
                            .load(Common.baseURL+detailPost.image)
                            .placeholder(R.drawable.image_default)
                            .error(R.drawable.image_default)
                            .into(binding.imagePost)
                    }
                    ////////
                    val isLikedIdUSer = detailPost.like?.any { like -> like.idUser._id == idUser}
                    if(isLikedIdUSer == true){
                        binding.tvLike.setTextColor(ContextCompat.getColor(applicationContext,R.color.blue))
                        binding.tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like,0,0,0)
                    }else{
                        binding.tvLike.setTextColor(ContextCompat.getColor(applicationContext,R.color.gray_5))
                        binding.tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_default,0,0,0)
                    }
                    binding.tvCountLike.text = detailPost.likeCount.toString()+" lượt thích"
                    binding.tvCountCmt.text = detailPost.commentCount.toString()+" bình luận"
                    val listCmt = detailPost.comment
                    if (listCmt != null) {
                        commentAdapter.updateComment(listCmt)
                    }
                }else{
                    Toast.makeText(this@CommentActivity, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}