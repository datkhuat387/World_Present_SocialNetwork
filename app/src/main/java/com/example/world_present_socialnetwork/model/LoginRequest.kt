package com.example.world_present_socialnetwork.model

data class LoginRequest(
    var email: String,
    var password: String,
    var fullname: String? = null
)
