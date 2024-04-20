package com.example.world_present_socialnetwork.model.user

data class LoginRequest(
    var username: String? = null,
    var password: String? = null,
    var fullname: String? = null
)
