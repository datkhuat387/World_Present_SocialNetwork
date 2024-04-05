package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemCommentBinding
import com.example.world_present_socialnetwork.model.CommentsExtend
import com.example.world_present_socialnetwork.utils.Common

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private val listCmt = mutableListOf<CommentsExtend>()
    private var commentListener: CommentListener? = null
    fun updateComment(list: MutableList<CommentsExtend>){
        this.listCmt.clear()
        this.listCmt.addAll(list)
        notifyDataSetChanged()
    }

    inner class CommentViewHolder(val binding:ItemCommentBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listCmt.size
    }
    fun setListener(listener:CommentListener){
        this.commentListener = listener
    }

    interface CommentListener{
        fun onLongClickComment(idComment: String)
    }
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        if(listCmt.isNotEmpty()){
            val item = listCmt[position]

            holder.binding.tvComment.setOnLongClickListener{
                item._id?.let { it1 -> commentListener?.onLongClickComment(it1) }
                true
            }
            holder.binding.apply {
                Glide.with(holder.itemView.context)
                    .load(Common.baseURL+item.idUser.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(imageAvt)

                tvFullname.text = item.idUser.fullname
                tvComment.text = item.comment
                tvAt.text = item.updateAt?.let { Common.formatDateTime(it) }
            }
        }
    }
}