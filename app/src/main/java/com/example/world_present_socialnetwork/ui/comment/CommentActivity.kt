package com.example.world_present_socialnetwork.ui.comment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.CommentAdapter
import com.example.world_present_socialnetwork.controllers.CommentController
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.ActivityCommentBinding
import com.example.world_present_socialnetwork.model.CommentsExtend
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
    private var listComment: MutableList<CommentsExtend> = mutableListOf()
    private var popupWindow: PopupWindow? = null
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

        binding.imgBack.setOnClickListener {
            finish()
        }
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
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        commentAdapter.setListener(object :CommentAdapter.CommentListener{
            override fun onLongClickComment(
                commentsExtend: CommentsExtend,
                isOwner: Boolean,
                view: View
            ) {
                val popupMenu = PopupMenu(this@CommentActivity,view)
                popupMenu.inflate(R.menu.menu_comment)
                Log.e("TAG", "onLongClickComment:$isOwner ", )
                if(isOwner){
                    popupMenu.menu.findItem(R.id.menu_update_cmt).isVisible = true
                    popupMenu.menu.findItem(R.id.menu_delete_cmt).isVisible = true
                    popupMenu.menu.findItem(R.id.menu_copy_cmt).isVisible = true
                    popupMenu.menu.findItem(R.id.menu_report_cmt).isVisible = false
                }else{
                    popupMenu.menu.findItem(R.id.menu_update_cmt).isVisible = false
                    popupMenu.menu.findItem(R.id.menu_delete_cmt).isVisible = false
                    popupMenu.menu.findItem(R.id.menu_copy_cmt).isVisible = true
                    popupMenu.menu.findItem(R.id.menu_report_cmt).isVisible = true
                }
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_update_cmt ->{
                            binding.lnrEdtCmt.visibility = View.INVISIBLE
                            val updateCmt = listComment.find { it._id==commentsExtend._id}
                            updateCmt?.isEditing = true
                            commentAdapter.updateComment(listComment)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_delete_cmt ->{
                            deleteComment(commentsExtend)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_copy_cmt ->{
                            val commentText = commentsExtend.comment ?: ""
                            val clipData = ClipData.newPlainText("comment", commentText)
                            clipboardManager.setPrimaryClip(clipData)

                            Toast.makeText(this@CommentActivity, "Đã sao chép", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_report_cmt ->{
                            Toast.makeText(this@CommentActivity, "Báo cáo", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popupMenu.show()
            }

            override fun onClickUpdateComment(commentsExtend: CommentsExtend, newComment: String) {
                updateComment(commentsExtend,newComment)
            }

            override fun onClickCancelComment(commentsExtend: CommentsExtend) {
                binding.lnrEdtCmt.visibility = View.VISIBLE
                val updateCmt = listComment.find { it._id==commentsExtend._id}
                updateCmt?.isEditing = false
                commentAdapter.updateComment(listComment)
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
//            idPost?.let { it1 -> getDetailPost(it1) }
//            Toast.makeText(this@CommentActivity, "Text: $textCmt",Toast.LENGTH_SHORT).show()
        }
    }

    private fun comment(idPost: String, idUser: String, comment: String){
        commentController.comment(idUser,idPost,comment){comment, error->
            if(comment!=null){
                binding.edCmt.text.clear()
                binding.edCmt.isEnabled = false
                binding.imgSend.isEnabled = false
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.edCmt.isEnabled = true
                    binding.imgSend.isEnabled = true
                }, 1000)
                listComment.add(comment)
                commentAdapter.updateComment(listComment)
                Toast.makeText(this@CommentActivity, "Đã bình luận bài viết", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@CommentActivity, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateComment(commentsExtend: CommentsExtend,comment: String){
        commentsExtend._id?.let {
            commentController.updateComment(it,comment){ commentExtend, error->
                if(commentExtend!=null){
                    val updateCmt = listComment.find { it._id==commentExtend._id }
                    updateCmt?.comment = comment
                    updateCmt?.isEditing = false
                    commentAdapter.updateComment(listComment)
                    binding.lnrEdtCmt.visibility = View.VISIBLE
                }else{
                    Log.e("TAG", "updateComment: $comment", )
                    Toast.makeText(this@CommentActivity, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun dialogUpdate(commentsExtend: CommentsExtend) {
        val contentView = LayoutInflater.from(this)
            .inflate(R.layout.popup_edit_comment, null)

        val editText = contentView.findViewById<EditText>(R.id.ed_comment)
        editText.setText(commentsExtend.comment)

        val btnCancel = contentView.findViewById<AppCompatButton>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            popupWindow?.dismiss()
            adjustBackgroundAlpha(1.0f)
        }

        val btnConfirm = contentView.findViewById<AppCompatButton>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            val editedText = editText.text.toString()
            updateComment(commentsExtend, editedText)
            popupWindow?.dismiss()
            binding.lnrEdtCmt.visibility = View.VISIBLE
            adjustBackgroundAlpha(1.0f)
        }

        popupWindow = PopupWindow(
            contentView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val rootView = window.decorView.rootView
        val layoutParams = rootView.layoutParams as WindowManager.LayoutParams
        layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.4f
        windowManager.updateViewLayout(rootView, layoutParams)

        popupWindow?.showAtLocation(rootView, Gravity.CENTER, 0, 0)
        adjustBackgroundAlpha(0.5f)
    }

    private fun adjustBackgroundAlpha(alpha: Float) {
        val container = findViewById<View>(android.R.id.content).rootView
        container.alpha = alpha
    }
    private fun deleteComment(commentsExtend: CommentsExtend){
        commentsExtend._id?.let {
            commentController.removeComment(it){ comments, error ->
                if(comments!=null){
                    listComment.remove(comments)
                    commentAdapter.updateComment(listComment)
                    Toast.makeText(this@CommentActivity, "Đã xóa bình luận", Toast.LENGTH_SHORT).show()
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
            if(detailPost!=null){
                Glide.with(this)
                    .load(Common.baseURL+detailPost.idUser.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(binding.imageAvt)
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
                listComment = detailPost.comment!!
                idUser?.let { commentAdapter.setUserId(it) }
                commentAdapter.updateComment(listComment)
            }else{
                Toast.makeText(this@CommentActivity, "Đã xảy ra lỗi cle: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}