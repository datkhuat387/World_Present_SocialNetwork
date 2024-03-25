package com.example.world_present_socialnetwork.model

data class Likes(
    var _id:String,
    var idUser:String,
    var idPost:String,
    var createAt:String? = null
){}
