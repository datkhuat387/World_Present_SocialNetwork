package com.example.world_present_socialnetwork.model.comment

import com.example.world_present_socialnetwork.model.user.User

data class CommentsExtend(
    var _id:String? = null,
    var idUser: User,
    var idPost:String? = null,
    var isEditing: Boolean? = null,
    var comment:String? = null,
    var status:Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
){}
