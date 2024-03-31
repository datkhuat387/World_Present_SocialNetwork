package com.example.world_present_socialnetwork.model

data class User(
    var _id:String? = null,
    var email:String? = null,
    var phone:String? = null,
    var password:String? = null,
    var fullname:String? = null,
    var status:Int? = null,
    var idAccountType:String? = null,
    var avatar:String? = null,
    var createAt:String? = null,
    var updateAt:String? = null
){}