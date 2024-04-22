package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemGroupBinding
import com.example.world_present_socialnetwork.model.group.Group
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.utils.Common

class GroupAdapter: RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {
    private val listGr = mutableListOf<Group>()
    private var groupListener: GroupListener? = null
    fun updateDataPost(list: List<Group>){
        this.listGr.clear()
        this.listGr.addAll(list)
        notifyDataSetChanged()
    }
    inner class GroupViewHolder(val binding: ItemGroupBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listGr.size
    }
    fun setListener(listener: GroupListener){
        this.groupListener = listener
    }
    interface GroupListener{
        fun onClickGroup(idGroup: String)
    }
    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        if(listGr.isNotEmpty()){
            val item = listGr[position]
            holder.itemView.setOnClickListener {
                item._id?.let { it1 -> groupListener?.onClickGroup(it1) }
            }
            holder.binding.apply {
                Glide.with(holder.itemView.context)
                    .load(Common.baseURL+item.coverImage)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(imgCoverGr)

                tvNameGr.text = item.name
                tvUpdateDate.text = item.updateAt
            }
        }
    }
}