package com.example.world_present_socialnetwork.model.page

data class Page(
    var _id: String? = null,
    var name:String? = null,
    var avatar:String? = null,
    var coverImage: String? = null,
    var description: String? = null,
    var creatorId: String? = null,
    var likeCount: Int? = null,
    var followCount: Int? = null,
    var status: Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
)
