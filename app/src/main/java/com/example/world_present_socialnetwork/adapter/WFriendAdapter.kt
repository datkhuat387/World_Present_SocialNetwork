package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemWaitConfirmBinding
import com.example.world_present_socialnetwork.model.FriendshipsExtend
import com.example.world_present_socialnetwork.utils.Common

class WFriendAdapter: RecyclerView.Adapter<WFriendAdapter.WFriendViewHolder>() {
    private val listWFriend = mutableListOf<FriendshipsExtend>()
    private var wFriendListener: WFriendListener? =  null
    fun updateWFriend(list: MutableList<FriendshipsExtend>){
        this.listWFriend.clear()
        this.listWFriend.addAll(list)
        notifyDataSetChanged()
    }
    inner class WFriendViewHolder(val binding: ItemWaitConfirmBinding): ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WFriendViewHolder {
        return WFriendViewHolder(ItemWaitConfirmBinding.inflate(LayoutInflater.from(parent.context)))
    }
    fun setListener(listener: WFriendListener){
        this.wFriendListener = listener
    }
    interface WFriendListener{
        fun onClickCancel(friendshipsExtend: FriendshipsExtend)
        fun onClickProfile(idUserAt: String)
    }
    override fun onBindViewHolder(holder: WFriendViewHolder, position: Int) {
        if(listWFriend.isNotEmpty()){
            val item = listWFriend[position]

            holder.binding.btnCancel.setOnClickListener {
                wFriendListener!!.onClickCancel(item)
            }
            holder.binding.imageAvt.setOnClickListener {
                item.idFriend!!._id?.let { it1 -> wFriendListener!!.onClickProfile(it1) }
            }
            holder.binding.tvFullname.setOnClickListener {
                item.idFriend!!._id?.let { it1 -> wFriendListener!!.onClickProfile(it1) }
            }
            holder.binding.apply {
                Glide.with(holder.itemView.context)
                    .load(Common.baseURL+ item.idUser!!.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(imageAvt)
                tvFullname.text = item.idFriend!!.fullname
            }
        }
    }

    override fun getItemCount(): Int {
        return listWFriend.size
    }
}