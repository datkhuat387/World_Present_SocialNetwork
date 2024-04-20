package com.example.world_present_socialnetwork.model.friend

import com.example.world_present_socialnetwork.model.user.User

data class FriendshipsExtend(
    var _id:String? = null,
    var idUser: User? = null,
    var idFriend: User? = null,
    var isBlock: List<String>? = null,
    var status:Int? = null,
    var createAt:String? = null
){}
