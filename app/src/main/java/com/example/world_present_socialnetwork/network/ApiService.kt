package com.example.world_present_socialnetwork.network

import com.example.world_present_socialnetwork.model.Comments
import com.example.world_present_socialnetwork.model.CommentsExtend
import com.example.world_present_socialnetwork.model.Friendships
import com.example.world_present_socialnetwork.model.FriendshipsExtend
import com.example.world_present_socialnetwork.model.Like
import com.example.world_present_socialnetwork.model.LikeExtend
import com.example.world_present_socialnetwork.model.LoginRequest
import com.example.world_present_socialnetwork.model.Posts
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.model.User
import com.example.world_present_socialnetwork.model.UserChangePasswd
import com.example.world_present_socialnetwork.model.UserInfo
import com.example.world_present_socialnetwork.model.UserInfoExtend
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
        @Path("idUser") idUser: String,
        @Body user: User
    ): Call<User>
    @PUT("api/userChangePasswd/{idUser}")
    fun userChangePasswd(
        @Path("idUser") idUser: String,
        @Body userChangePasswd: UserChangePasswd
    ): Call<User>
    @PUT("api/updateFullname/{idUser}")
    fun updateFullname(
        @Path("idUser") idUser: String,
        @Body user: User): Call<User>
    @Multipart
    @PUT("api/updateAvatar/{idUser}")
    fun updateAvatar(
        @Path("idUser") idUser: String,
        @Part avatar: MultipartBody.Part?
    ): Call<User>
    //------------------------ UserInfo --------------------------//
    @POST("api/createUserInfo/{idUser}")
    fun createUserInfo(@Path("idUser") idUser: String): Call<UserInfo>
    @GET("api/userInfo/{idUser}")
    fun getUserInfo(@Path("idUser") idUser: String): Call<UserInfoExtend>
    @Multipart
    @PUT("api/updateCoverImage/{id}")
    fun updateCoverImage(
        @Path("id") id: String,
        @Part coverImage: MultipartBody.Part?
    ): Call<UserInfo>
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
    fun like(@Body like: Like): Call<LikeExtend>
    @DELETE("api/removeLike/{id}")
    fun removeLike(@Path("id") id: String): Call<LikeExtend>
    @GET("api/listLikeByIdPost/{idPost}")
    fun getListLikeByIdPost(@Path("idPost") idPost: String): Call<List<LikeExtend>>
    //-------------------------- Comment ----------------------------//
    @POST("api/comment")
    fun comment(@Body comments: Comments): Call<CommentsExtend>
    @GET("api/comment/{idPost}")
    fun getComment(@Path("idPost") idPost: String): Call<MutableList<CommentsExtend>>
    @PUT("api/comment/{id}")
    fun updateComment(
        @Path("id") id: String,
        @Body comments: Comments
    ): Call<CommentsExtend>
    @DELETE("api/comment/{id}")
    fun deleteComment(@Path("id") id: String): Call<CommentsExtend>
    //------------------------- Friend ------------------------------//
    @POST("api/addFriend")
    fun addFriend(@Body friendships: Friendships): Call<Friendships>
    @DELETE("api/unFriend/{idUser}")
    fun unFriend(@Path("idUser") idUser: String): Call<Void>
    @GET("api/friend/{idUser}/{idFriend}")
    fun friend(@Path("idUser") idUser: String,
               @Path("idFriend") idFriend: String
    ): Call<Friendships>
    @GET("api/listFriendWaitConfirm/{idUser}")
    fun getListWaitConFirm(@Path("idUser") idUser: String): Call<MutableList<FriendshipsExtend>>
    @GET("api/listFriendIsWaitConfirm/{idUser}")
    fun getListIsWaitConFirm(@Path("idUser") idUser: String): Call<MutableList<FriendshipsExtend>>
    @DELETE("api/notConfirmAddFriend/{id}")
    fun notConfirmFriend(@Path("id") id: String): Call<Void>
    @PUT("api/confirmAddFriend/{id}")
    fun confirmFriend(@Path("id") id: String): Call<Friendships>
}