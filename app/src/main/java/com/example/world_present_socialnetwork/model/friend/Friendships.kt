package com.example.world_present_socialnetwork.model.friend

data class Friendships(
    var _id:String? = null,
    var idUser:String? = null,
    var idFriend:String? = null,
    var isBlock: List<String>? = null,
    var status:Int? = null,
    var createAt:String? = null
){}
