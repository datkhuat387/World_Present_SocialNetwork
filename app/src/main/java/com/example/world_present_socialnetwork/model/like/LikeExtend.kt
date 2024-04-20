package com.example.world_present_socialnetwork.model.like

import com.example.world_present_socialnetwork.model.user.User

data class LikeExtend(
    var _id:String? = null,
    var idUser: User,
    var idPost:String? = null,
    var idPage: String? = null,
    var createAt:String? = null
)
