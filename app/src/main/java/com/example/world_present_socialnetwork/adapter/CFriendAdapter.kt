package com.example.world_present_socialnetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ItemConfirmFriendBinding
import com.example.world_present_socialnetwork.model.friend.FriendshipsExtend
import com.example.world_present_socialnetwork.utils.Common

class CFriendAdapter: RecyclerView.Adapter<CFriendAdapter.CFriendViewHolder>() {
    private var isLimitedView = true
    private val limitItemCount = 5
    private val listCFriend = mutableListOf<FriendshipsExtend>()
    private var cFriendListener: CFriendListener? =  null

    fun updateCFriend(list: MutableList<FriendshipsExtend>){
        this.listCFriend.clear()
        this.listCFriend.addAll(list)
        notifyDataSetChanged()
    }
    inner class CFriendViewHolder(val binding:ItemConfirmFriendBinding): ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CFriendViewHolder {
        return CFriendViewHolder(ItemConfirmFriendBinding.inflate(LayoutInflater.from(parent.context)))
    }
    fun setListener(listener: CFriendListener){
        this.cFriendListener = listener
    }
    interface CFriendListener{
        fun onClickConfirm(friendshipsExtend: FriendshipsExtend)
        fun onClickDelete(friendshipsExtend: FriendshipsExtend)
        fun onClickProfile(idUserAt: String)
    }
    override fun onBindViewHolder(holder: CFriendViewHolder, position: Int) {
        if(listCFriend.isNotEmpty()){
            val item = listCFriend[position]

            holder.binding.btnConfirm.setOnClickListener {
                cFriendListener!!.onClickConfirm(item)
            }
            holder.binding.btnDelete.setOnClickListener {
                cFriendListener!!.onClickDelete(item)
            }
            holder.binding.imageAvt.setOnClickListener {
                item.idUser!!._id?.let { it1 -> cFriendListener!!.onClickProfile(it1) }
            }
            holder.binding.tvFullname.setOnClickListener {
                item.idUser!!._id?.let { it1 -> cFriendListener!!.onClickProfile(it1) }
            }
            holder.binding.apply {
                Glide.with(holder.itemView.context)
                    .load(Common.baseURL+ item.idUser!!.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(imageAvt)
                tvFullname.text = item.idUser!!.fullname
            }
        }
    }

    override fun getItemCount(): Int {
        if (isLimitedView) {
            return minOf(limitItemCount, listCFriend.size)
        }
        return listCFriend.size
    }

}