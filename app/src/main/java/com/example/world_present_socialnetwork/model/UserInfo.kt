package com.example.world_present_socialnetwork.model

data class UserInfo(
    var _id: String? = null,
    var isActive: Boolean? = null,
    var dateOfBirth: String? = null,
    var highSchool: String? = null,
    var college_university: String? = null,
    var workingAt: String? = null,
    var provinceCityAt: String? = null,
    var postSave: List<String>? = null,
    var relationship: String? = null,
    var socialLinks: String? = null,
    var createAt: String? = null,
    var updateAt: String? = null
)