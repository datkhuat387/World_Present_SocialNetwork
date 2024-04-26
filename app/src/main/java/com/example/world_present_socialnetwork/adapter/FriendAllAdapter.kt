package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemFriendAllBinding
import com.example.world_present_socialnetwork.model.friend.FriendshipsExtend
import com.example.world_present_socialnetwork.utils.Common

class FriendAllAdapter: RecyclerView.Adapter<FriendAllAdapter.FriendAllViewHolder>() {
    private val listFriend = mutableListOf<FriendshipsExtend>()
    private var currentIdUser: String? = null
    private var friendAllListener: FriendAllListener? = null

    fun updateDataPost(list: MutableList<FriendshipsExtend>){
        this.listFriend.clear()
        this.listFriend.addAll(list)
        notifyDataSetChanged()
    }
    fun setUserId(id: String) {
        currentIdUser = id
    }
    fun setListener(listener: FriendAllListener){
        this.friendAllListener = listener
    }
    interface FriendAllListener{
        fun onClickFriend(idUserAt: String)
    }
    inner class FriendAllViewHolder(val binding: ItemFriendAllBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAllViewHolder {
        return FriendAllViewHolder(ItemFriendAllBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return listFriend.size
    }

    override fun onBindViewHolder(holder: FriendAllViewHolder, position: Int) {
        if(listFriend.isNotEmpty()){
            val item = listFriend[position]
            holder.itemView.setOnClickListener {
                if(item.idFriend?._id ==currentIdUser){
                    item.idUser?._id?.let { it1 -> friendAllListener?.onClickFriend(it1) }
                }else{
                    item.idFriend?._id?.let { it1 -> friendAllListener?.onClickFriend(it1) }
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
}