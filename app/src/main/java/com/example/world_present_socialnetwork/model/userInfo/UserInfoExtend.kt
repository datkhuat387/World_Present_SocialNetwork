package com.example.world_present_socialnetwork.model.userInfo

import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.model.relationship.RelationshipExtend

data class UserInfoExtend(
    var _id: String? = null,
    var isActive: Boolean? = null,
    var dateOfBirth: String? = null,
    var highSchool: String? = null,
    var collegeUniversity: String? = null,
    var workingAt: String? = null,
    var provinceCityAt: String? = null,
    var coverImage: String? = null,
    var postSave: MutableList<PostsExtend>? = arrayListOf(),
    var relationship: RelationshipExtend? = null,
    var socialLinks: String? = null,
    var createAt: String? = null,
    var updateAt: String? = null
)