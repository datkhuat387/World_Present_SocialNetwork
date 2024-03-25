package com.example.world_present_socialnetwork.model

data class Posts(
    var _id:String,
    var idUser:String,
    var content:String? = null,
    var image:String? = null,
    var createAt:String? = null,
    var updateAt:String? = null

){}
