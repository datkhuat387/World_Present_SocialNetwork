package com.example.world_present_socialnetwork.model

data class Friendships(
    var _id:String,
    var idUser:String,
    var idFriend:String,
    var status:Int? = null,
    var createAt:String? = null
){}
