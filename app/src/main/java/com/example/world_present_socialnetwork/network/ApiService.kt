package com.example.world_present_socialnetwork.network

import com.example.world_present_socialnetwork.model.LoginRequest
import com.example.world_present_socialnetwork.model.Posts
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<User>
    @POST("api/createAccount")
    fun register(@Body loginRequest: LoginRequest): Call<User>
    @GET("api/user/{userId}")
    fun getUser(@Path("userId") userId: String): Call<User>

    @GET("api/getAllPost")
    fun getAllPost(): Call<List<PostsExtend>>
    @Multipart
    @POST("api/createPost")
    fun createPost(
        @Part("idUser") idUser: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): Call<Posts>


//    @POST("api/createPost")
////    fun createPost(@Body posts: Posts): Call<Posts>
//    @Multipart
//    fun createPost(
//        @Part("idUser") idUser: String,
//        @Part("content") content: String,
//        @Part image: MultipartBody.Part?
//    ): Call<RequestBody>
}