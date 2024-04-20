package com.example.world_present_socialnetwork.model.post

data class Posts(
    var _id:String? = null,
    var idGroup:String? = null,
    var idPage: String? = null,
    var idUser:String? = null,
    var isOwner: Boolean? = null,
    var isLiked: Boolean? = null,
    var content:String? = null,
    var image:String? = null,
    var video:String? = null,
    var comment: List<String>? = null,
    var like: List<String>? = null,
    var commentCount:Int? = null,
    var likeCount:Int? = null,
    var status:Int? = null,
    var createAt:String? = null,
    var updateAt:String? = null
)
