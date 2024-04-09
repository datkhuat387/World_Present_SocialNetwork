package com.example.world_present_socialnetwork.network

import com.example.world_present_socialnetwork.model.Comments
import com.example.world_present_socialnetwork.model.CommentsExtend
import com.example.world_present_socialnetwork.model.Like
import com.example.world_present_socialnetwork.model.LikeExtend
import com.example.world_present_socialnetwork.model.LoginRequest
import com.example.world_present_socialnetwork.model.Posts
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.model.User
import com.example.world_present_socialnetwork.model.UserChangePasswd
import com.example.world_present_socialnetwork.model.UserInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    //------------------------- User ------------------------------//
    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<User>
    @POST("api/createAccount")
    fun register(@Body loginRequest: LoginRequest): Call<User>
    @GET("api/user/{userId}")
    fun getUser(@Path("userId") userId: String): Call<User>
    @PUT("api/user/{idUser}")
    fun updateUser(
        @Path("idUser") idUser: User,
        @Body user: User
    ): Call<User>
    @PUT("api/userChangePasswd/{idUser}")
    fun userChangePasswd(
        @Path("idUser") idUser: User,
        @Body userChangePasswd: UserChangePasswd
    ): Call<User>
    //------------------------ UserInfo --------------------------//
    @POST("api/createUserInfo/{idUser}")
    fun createUserInfo(@Path("idUser") idUser: String): Call<UserInfo>
    @GET("api/userInfo/{idUser}")
    fun getUserInfo(@Path("idUser") idUser: String): Call<UserInfo>
    //------------------------- Post -----------------------------//
    @GET("api/getAllPost/{idUser}")
    fun getAllPost(@Path("idUser") idUser: String): Call<List<PostsExtend>>
    @Multipart
    @POST("api/createPost")
    fun createPost(
        @Part("idUser") idUser: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): Call<Posts>
    @Multipart
    @PUT("api/updatePost/{id}")
    fun updatePost(
        @Path("id") id: String,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): Call<Posts>
    @DELETE("api/post/{id}")
    fun deletePost(@Path("id") id: String): Call<Void>
    @GET("api/detailPost/{id}")
    fun getDetailPost(@Path("id") id: String): Call<PostsExtend>
    //-------------------------- Like -----------------------------//
    @POST("api/like")
    fun like(@Body like: Like): Call<Like>
    @DELETE("api/removeLike/{id}")
    fun removeLike(@Path("id") id: String): Call<LikeExtend>
    @GET("api/listLikeByIdPost/{idPost}")
    fun getListLikeByIdPost(@Path("idPost") idPost: String): Call<List<LikeExtend>>

//    @POST("api/createPost")
////    fun createPost(@Body posts: Posts): Call<Posts>
//    @Multipart
//    fun createPost(
//        @Part("idUser") idUser: String,
//        @Part("content") content: String,
//        @Part image: MultipartBody.Part?
//    ): Call<RequestBody>
    //-------------------------- Comment ----------------------------//
    @POST("api/comment")
    fun comment(@Body comments: Comments): Call<Comments>
    @GET("api/comment/{idPost}")
    fun getComment(@Path("idPost") idPost: String): Call<MutableList<CommentsExtend>>
    @PUT("api/comment/{id}")
    fun updateComment(
        @Path("id") id: String,
        @Body comments: Comments
    ): Call<Comments>
    @DELETE("api/comment/{id}")
    fun deleteComment(@Path("id") id: String): Call<Comments>
}