package com.example.world_present_socialnetwork.network

import com.example.world_present_socialnetwork.model.LoginRequest
import com.example.world_present_socialnetwork.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<User>
    //
    @POST("api//createAccount")
    fun register(@Body loginRequest: LoginRequest): Call<User>
    //
    @GET("api/user/{userId}")
    fun getUser(@Path("userId") userId: String): Call<User>

}