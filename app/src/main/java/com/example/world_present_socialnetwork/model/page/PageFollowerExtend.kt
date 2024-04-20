package com.example.world_present_socialnetwork.model.page

import com.example.world_present_socialnetwork.model.user.User

data class PageFollowerExtend(
    var _id: String? = null,
    var idUser: User? = null,
    var idPage: Page? = null,
    var status: Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
)
