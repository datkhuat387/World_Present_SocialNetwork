package com.example.world_present_socialnetwork.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemPostBinding
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.model.User
import com.example.world_present_socialnetwork.utils.Common
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class PostAdapter:RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val listPost = mutableListOf<PostsExtend>()
    private var postlistener:PostListener? = null
    private var currentIdUser: String? = null
//    private val baseURL:String = "http://192.168.0.106:3000"
    fun updateDataPost(list: MutableList<PostsExtend>){
        this.listPost.clear()
        this.listPost.addAll(list)
        notifyDataSetChanged()
    }
    fun setUserId(id: String) {
        currentIdUser = id
    }
    fun setListener(listener:PostListener){
        this.postlistener = listener
    }
    inner class PostViewHolder(val binding:ItemPostBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listPost.size
    }
    interface PostListener{
        fun onClickComment(idPost: String)
        fun onClickLike(idPost: String)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if(holder is PostViewHolder){
            if(listPost.isNotEmpty()){
                val item = listPost[position]

                holder.binding.tvLike.setOnClickListener {
                    item._id?.let { it1 -> postlistener?.onClickLike(it1) }
                }
                holder.binding.tvComment.setOnClickListener {
                    item._id?.let { it1 -> postlistener?.onClickComment(it1) }
                }
                holder.binding.apply {
                    if(item.image == null||item.image == ""){
                        imagePost.visibility = View.GONE
                    }else{
                        Glide.with(holder.itemView.context)
                            .load(Common.baseURL+item.image)
                            .placeholder(R.drawable.image_default)
                            .error(R.drawable.image_default)
                            .into(imagePost)
                    }
                    if(item.content == null||item.content == ""){
                        tvContent.visibility = View.GONE
                        val layoutParams = imagePost.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.topMargin = 50
                        imagePost.layoutParams = layoutParams
                    }
                    /// check like post ?
                    val isLikedIdUSer = item.like?.any { like -> like.idUser._id == currentIdUser}
                    if(isLikedIdUSer == true){
                        tvLike.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.blue))
                        tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like,0,0,0)
                    }else{
                        tvLike.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.gray_5))
                        tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_default,0,0,0)
                    }

                    tvContent.text = item.content
                    tvDate.text = item.createAt?.let { Common.formatDateTime(it) }
                    tvName.text = item.idUser.fullname
                    /// like
                    if(item.likeCount == 0){
                        tvCountLike.visibility = View.GONE
                    }else{
                        tvCountLike.visibility = View.VISIBLE
                        tvCountLike.text = item.likeCount.toString()+" lượt thích"
                    }
                    /// cmt
                    if(item.commentCount == 0){
                        tvCountCmt.visibility = View.GONE
                    }else{
                        tvCountCmt.visibility = View.VISIBLE
                        tvCountCmt.text = item.commentCount.toString()+" bình luận"
                    }
//                    Glide.with(holder.itemView.context)
//                        .load(Common.baseURL+item.idUser.avatar)
//                        .placeholder(R.drawable.avatar_profile)
//                        .error(R.drawable.avatar_profile)
//                        .into(imageAvt)
                }
            }
        }
    }
    fun countLike(holder: PostViewHolder,position: Int){
        val item = listPost[position]
        if(item.likeCount == 0){
            holder.binding.tvCountLike.visibility = View.GONE
        }else{
            holder.binding.tvCountLike.text = item.likeCount.toString()+" lượt thích"
        }
    }

}