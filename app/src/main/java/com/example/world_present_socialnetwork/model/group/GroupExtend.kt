package com.example.world_present_socialnetwork.model.group

import com.example.world_present_socialnetwork.model.user.User

data class GroupExtend(
    var _id:String? = null,
    var name:String? = null,
    var avatar:String? = null,
    var coverImage: String? = null,
    var description: String? = null,
    var creatorId: User? = null,
    var memberCount: Int? = null,
    var status: Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
)
