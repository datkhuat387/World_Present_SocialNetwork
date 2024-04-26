package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.post.Posts
import com.example.world_present_socialnetwork.model.post.PostsExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostController {
    private val apiService: ApiService = RetrofitClient.apiService

    fun getAllPost(idUser: String,callback: (List<PostsExtend>?, String?) -> Unit){
        apiService.getAllPost(idUser).enqueue(object : Callback<List<PostsExtend>>{
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
    fun createPostGroup(idUser: String,idGroup: String, content: String, image: File?, callback: (Posts?, String?) -> Unit) {
        val idUserPart = idUser.toRequestBody("text/plain".toMediaTypeOrNull())
        val idGroupPart = idGroup.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentPart = content.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = if (image != null) {
            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", image.name, requestFile)
        } else {
            null
        }

        apiService.createPostGroup(idUserPart,idGroupPart, contentPart, imagePart).enqueue(object : Callback<Posts> {
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
    fun updatePost(idPost: String, content: String, image: File?, callback: (Posts?, String?) -> Unit) {
        val idPostPart = idPost.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentPart = content.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = if (image != null) {
            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", image.name, requestFile)
        } else {
            null
        }

        apiService.updatePost(idPost, contentPart, imagePart).enqueue(object : Callback<Posts> {
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
    fun removePost(idPost: String, callback: (String?, String?) -> Unit){
        apiService.deletePost(idPost).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback("Đã xóa bài viết", null)
                } else {
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(null, t.message)
            }


        })
    }

    fun getDetailPost(idPost: String, callback: (PostsExtend?, String?) -> Unit){
        apiService.getDetailPost(idPost).enqueue(object : Callback<PostsExtend>{
            override fun onResponse(call: Call<PostsExtend>, response: Response<PostsExtend>) {
                if (response.isSuccessful) {
                    val detailPost = response.body()
                    callback(detailPost, null)
                } else {
                    callback(null, response.errorBody()?.string() ?: "Lỗi")
                }
            }

            override fun onFailure(call: Call<PostsExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }

    fun getPostByIdUser(idUserAt: String,idUser: String, callback: (List<PostsExtend>?, String?) -> Unit){
        apiService.getPostByIdUser(idUserAt,idUser).enqueue(object : Callback<List<PostsExtend>>{
            override fun onResponse(
                call: Call<List<PostsExtend>>,
                response: Response<List<PostsExtend>>
            ) {
                if(response.isSuccessful){
                    val post = response.body()
                    callback(post,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<List<PostsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getPostByIdGroup(idGroup: String, idUser: String, callback: (List<PostsExtend>?, String?) -> Unit){
        apiService.getPostByIdGroup(idGroup,idUser).enqueue(object : Callback<List<PostsExtend>>{
            override fun onResponse(
                call: Call<List<PostsExtend>>,
                response: Response<List<PostsExtend>>
            ) {
                if(response.isSuccessful){
                    val post = response.body()
                    callback(post,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<List<PostsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}