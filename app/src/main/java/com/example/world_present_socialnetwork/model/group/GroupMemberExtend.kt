package com.example.world_present_socialnetwork.model.group

import com.example.world_present_socialnetwork.model.user.User

data class GroupMemberExtend(
    var _id: String? = null,
    var idUser: User? = null,
    var idGroup: Group? = null,
    var status: Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
)
