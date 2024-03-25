package com.example.world_present_socialnetwork.model

data class User(
    var _id:String,
    var email:String,
    var password:String,
    var fullname:String,
    var idAccountType:String,
    var avatar:String? = null,
    var createAt:String? = null,
    var updateAt:String? = null
){}