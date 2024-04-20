package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemCommentBinding
import com.example.world_present_socialnetwork.model.comment.CommentsExtend
import com.example.world_present_socialnetwork.utils.Common

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private val listCmt = mutableListOf<CommentsExtend>()
    private var commentListener: CommentListener? = null
    private var currentIdUser: String? = null
    private var isEditTing:Boolean = false
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
    fun setUserId(id: String) {
        currentIdUser = id
    }
    fun setIsEditing(isEdit: Boolean){
        isEditTing = isEdit
    }
    interface CommentListener{
        fun onLongClickComment(commentsExtend: CommentsExtend, isOwner: Boolean, view: View)
        fun onClickUpdateComment(commentsExtend: CommentsExtend, newComment: String)
        fun onClickCancelComment(commentsExtend: CommentsExtend)
    }
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        if(listCmt.isNotEmpty()){
            val item = listCmt[position]
            var isOwner:Boolean = listCmt.any {
                item.idUser._id == currentIdUser
            }
            holder.binding.tvComment.setOnLongClickListener{
                commentListener?.onLongClickComment(item,isOwner, holder.binding.tvFullname)
                true
            }
            holder.binding.btnCancel.setOnClickListener {
                commentListener?.onClickCancelComment(item)
            }
            holder.binding.btnConfirm.setOnClickListener {
                val newComment = holder.binding.edComment.text.toString()
                newComment.let { it1 -> commentListener?.onClickUpdateComment(item, it1) }
            }
            holder.binding.apply {
                Glide.with(holder.itemView.context)
                    .load(Common.baseURL+item.idUser.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(imageAvt)

                if(item.isEditing == true){
                    holder.binding.edComment.setText(item.comment)
                    lnrCmt.visibility = View.GONE
                    lnrRep.visibility = View.GONE
                    lnrEdtUpdate.visibility = View.VISIBLE
                }else{
                    lnrCmt.visibility = View.VISIBLE
                    lnrRep.visibility = View.VISIBLE
                    lnrEdtUpdate.visibility = View.GONE
                }
                tvFullname.text = item.idUser.fullname
                tvComment.text = item.comment
                tvAt.text = item.updateAt?.let { Common.formatDateTime(it) }
            }
        }
    }
}