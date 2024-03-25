package com.example.world_present_socialnetwork.model

data class Comments(
    var _id:String,
    var idUser:String,
    var idPost:String,
    var content:String? = null,
    var createAt:String? = null,
    var updateAt:String? = null
){}
