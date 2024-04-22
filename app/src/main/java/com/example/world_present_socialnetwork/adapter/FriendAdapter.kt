package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemFriendBinding
import com.example.world_present_socialnetwork.model.friend.FriendshipsExtend
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.utils.Common

class FriendAdapter: RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    private val listFriend = mutableListOf<FriendshipsExtend>()
    private var currentIdUser: String? = null
    private var friendListener: FriendListener? = null
    private var isLimitedView = true
    private val limitItemCount = 6
    fun updateDataPost(list: MutableList<FriendshipsExtend>){
        this.listFriend.clear()
        this.listFriend.addAll(list)
        notifyDataSetChanged()
    }
    fun setUserId(id: String) {
        currentIdUser = id
    }
    inner class FriendViewHolder(val binding: ItemFriendBinding): ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(ItemFriendBinding.inflate(LayoutInflater.from(parent.context)))
    }
    fun setListener(listener: FriendListener){
        this.friendListener = listener
    }
    interface FriendListener{
        fun onClickFriend(idUserAt: String)
    }
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        if(listFriend.isNotEmpty()){
            val item = listFriend[position]

            holder.itemView.setOnClickListener {
                if(item.idFriend?._id ==currentIdUser){
                    item.idUser?._id?.let { it1 -> friendListener?.onClickFriend(it1) }
                }else{
                    item.idFriend?._id?.let { it1 -> friendListener?.onClickFriend(it1) }
                }

            }
            holder.binding.apply {
                if(item.idFriend?._id ==currentIdUser){
                    Glide.with(holder.itemView.context)
                        .load(Common.baseURL+ item.idUser!!.avatar)
                        .placeholder(R.drawable.avatar_profile)
                        .error(R.drawable.avatar_profile)
                        .into(imgAvatar)
                    tvFullname.text = item.idUser?.fullname
                }
                if(item.idUser?._id==currentIdUser){
                    Glide.with(holder.itemView.context)
                        .load(Common.baseURL+ item.idFriend!!.avatar)
                        .placeholder(R.drawable.avatar_profile)
                        .error(R.drawable.avatar_profile)
                        .into(imgAvatar)
                    tvFullname.text = item.idFriend?.fullname
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (isLimitedView) {
            return minOf(limitItemCount, listFriend.size)
        }
        return listFriend.size
    }
}