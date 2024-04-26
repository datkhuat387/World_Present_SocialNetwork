package com.example.world_present_socialnetwork.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemPostBinding
import com.example.world_present_socialnetwork.model.like.LikeExtend
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.utils.Common

class PostGroupAdapter: RecyclerView.Adapter<PostGroupAdapter.PostGroupViewHolder>() {
    private val listPost = mutableListOf<PostsExtend>()
    private var postListener: PostListener? = null
    private var currentIdUser: String? = null

    fun updateDataPost(list: MutableList<PostsExtend>){
        this.listPost.clear()
        this.listPost.addAll(list)
        notifyDataSetChanged()
    }
    fun setUserId(id: String) {
        currentIdUser = id
    }
    fun setListener(listener: PostListener){
        this.postListener = listener
    }
    inner class PostGroupViewHolder(val binding: ItemPostBinding):
        RecyclerView.ViewHolder(binding.root)

    interface PostListener{
        fun onClickComment(idPost: String)
        fun onClickLike(like: LikeExtend?, post: PostsExtend)
        fun onClickMenu(post: PostsExtend, isOwner: Boolean, view: View)
        fun onClickProfile(idUserAt: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostGroupViewHolder {
        return PostGroupViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    override fun onBindViewHolder(holder: PostGroupViewHolder, position: Int) {
        if(listPost.isNotEmpty()){
            val item = listPost[position]

            holder.binding.imageAvt.setOnClickListener {
                item.idUser._id?.let { it1 -> postListener?.onClickProfile(it1) }
            }
            holder.binding.tvName.setOnClickListener {
                item.idUser._id?.let { it1 -> postListener?.onClickProfile(it1) }
            }
//            holder.binding.tvName2.setOnClickListener {
//                item.idUser._id?.let { it1 -> postlistener?.onClickProfile(it1) }
//            }
//            holder.binding.tvGroup.setOnClickListener {
//                item.idGroup?.creatorId?.let { it1 ->
//                    item.idGroup!!._id?.let { it2 ->
//                        postlistener?.onClickGroup(
//                            it2,
//                            it1
//                        )
//                    }
//                }
//            }

            holder.binding.tvLike.setOnClickListener {
                val currentUserId = currentIdUser

                var likeIsCLick: LikeExtend? = null

                item.like?.forEach { like ->
                    if (like.idUser._id == currentUserId) {
                        likeIsCLick = like
                        return@forEach
                    }
                }

                postListener?.onClickLike(likeIsCLick, item)
            }
            holder.binding.tvComment.setOnClickListener {
                item._id?.let { it1 -> postListener?.onClickComment(it1) }
            }
            holder.binding.icEdit.setOnClickListener {
                item.let { it1 -> item.isOwner?.let { it2 ->
                    postListener?.onClickMenu(it1,
                        it2,holder.binding.icEdit)
                } }
            }

            holder.binding.apply {
                // image post
                if(item.image == null||item.image == ""){
                    imagePost.visibility = View.GONE
                }else{
                    imagePost.visibility = View.VISIBLE
                    Glide.with(holder.itemView.context)
                        .load(Common.baseURL+item.image)
                        .placeholder(R.drawable.image_default)
                        .error(R.drawable.image_default)
                        .into(imagePost)
                }
                // content in post
                if(item.content == null||item.content == ""){
                    tvContent.visibility = View.GONE
                    val layoutParams = imagePost.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.topMargin = 50
                    imagePost.layoutParams = layoutParams
                }
                /// check like post ?
//                val isLikedIdUSer = item.like?.any { like -> like.idUser._id == currentIdUser}
                if(item.isLiked == true){
                    tvCountLike.visibility = View.VISIBLE
                    tvCountLike.text = item.likeCount.toString()+" lượt thích"

                    tvLike.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like,0,0,0)
                }else{
                    tvLike.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.gray_5))
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_default,0,0,0)
                }
                ///
                tvContent.text = item.content
                if(item.idGroup!=null){
                    relativeLayout.visibility = View.GONE
                    tvGroup.visibility = View.GONE
                    tvName2.visibility = View.GONE
                    tvDate2.visibility = View.GONE

                    imageAvt.visibility = View.VISIBLE
                    tvName.visibility = View.VISIBLE
                    tvDate.visibility = View.VISIBLE
                    // avatar user
                    Glide.with(holder.itemView.context)
                        .load(Common.baseURL+item.idUser.avatar)
                        .placeholder(R.drawable.avatar_profile)
                        .error(R.drawable.avatar_profile)
                        .into(imageAvt)
                    tvDate.text = item.createAt?.let { Common.formatDateTime(it) }
                    tvName.text = item.idUser.fullname
                }
                /// like
                if(item.likeCount == 0){
                    tvCountLike.visibility = View.INVISIBLE
                }else{
                    tvCountLike.visibility = View.VISIBLE
                    tvCountLike.text = item.likeCount.toString()+" lượt thích"
                }
                /// cmt
                if(item.commentCount == 0){
                    tvCountCmt.visibility = View.INVISIBLE
                }else{
                    tvCountCmt.visibility = View.VISIBLE
                    tvCountCmt.text = item.commentCount.toString()+" bình luận"
                }
            }
        }
    }
}