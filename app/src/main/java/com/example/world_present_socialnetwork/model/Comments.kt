package com.example.world_present_socialnetwork.model

data class Comments(
    var _id:String? = null,
    var idUser:String? = null,
    var idPost:String? = null,
    var isEditing: Boolean? = null,
    var comment:String? = null,
    var status:Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
){}
