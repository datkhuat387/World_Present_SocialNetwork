package com.example.world_present_socialnetwork.model

data class Friendships(
    var _id:String? = null,
    var idUser:String? = null,
    var idFriend:String,
    var status:Int? = null,
    var createAt:String? = null
){}
