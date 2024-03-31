package com.example.world_present_socialnetwork.controllers

import android.media.Image
import com.example.world_present_socialnetwork.model.Posts
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostController {
    private val apiService: ApiService = RetrofitClient.apiService

    fun getAllPost(callback: (List<PostsExtend>?, String?) -> Unit){
        apiService.getAllPost().enqueue(object : Callback<List<PostsExtend>>{
            override fun onResponse(call: Call<List<PostsExtend>>, response: Response<List<PostsExtend>>) {
                if(response.isSuccessful){
                    val list = response.body()
                    callback(list,null)
                }else{
                    callback(null, response.errorBody()?.string() ?: "Lỗi")
                }
            }

            override fun onFailure(call: Call<List<PostsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }

//    fun createPost(idUser: String, content: String, callback: (Posts?, String?) -> Unit) {
//        val post = Posts(null,idUser, content,null,null,null,null)
//        apiService.createPost(post).enqueue(object : Callback<Posts> {
//            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
//                if (response.isSuccessful) {
//                    val posts = response.body()
//                    callback(posts, null)
//                } else {
//                    callback(null, response.errorBody()?.string() ?: "Lỗi")
//                }
//            }
//
//            override fun onFailure(call: Call<Posts>, t: Throwable) {
//                callback(null, t.message)
//            }
//        })
//    }
    fun createPost(idUser: String, content: String, image: File?, callback: (Posts?, String?) -> Unit) {
        val idUserPart = idUser.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentPart = content.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = if (image != null) {
            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", image.name, requestFile)
        } else {
            null
        }

        apiService.createPost(idUserPart, contentPart, imagePart).enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    callback(post, null)
                } else {
                    callback(null, response.errorBody()?.string() ?: "Lỗi")
                }
            }

            override fun onFailure(call: Call<Posts>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
}