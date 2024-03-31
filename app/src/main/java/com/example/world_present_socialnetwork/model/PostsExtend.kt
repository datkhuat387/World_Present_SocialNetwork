package com.example.world_present_socialnetwork.model

data class PostsExtend(
    var _id:String? = null,
    var idUser:User,
    var content:String? = null,
    var image:String? = null,
    var video:String? = null,
    var createAt:String? = null,
    var updateAt:String? = null

){}
