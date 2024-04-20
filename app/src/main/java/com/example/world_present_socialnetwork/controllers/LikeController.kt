package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.like.Like
import com.example.world_present_socialnetwork.model.like.LikeExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeController {
    private val apiService: ApiService = RetrofitClient.apiService
    fun likePost(idUser: String, idPost:String, callback: (LikeExtend?, String?)->Unit){
        val like = Like(null,idUser,idPost,null)
        apiService.like(like).enqueue(object : Callback<LikeExtend>{
            override fun onResponse(call: Call<LikeExtend>, response: Response<LikeExtend>) {
                val like = response.body()
                callback(like,null)
            }

            override fun onFailure(call: Call<LikeExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun removeLike(id:String,callback: (LikeExtend?, String?)->Unit){
        apiService.removeLike(id).enqueue(object : Callback<LikeExtend>{
            override fun onResponse(call: Call<LikeExtend>, response: Response<LikeExtend>) {
                val like = response.body()
                callback(like,null)
            }

            override fun onFailure(call: Call<LikeExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getListLikeByIdPost(idPost: String,callback: (List<LikeExtend>?, String?)->Unit){
        apiService.getListLikeByIdPost(idPost).enqueue(object : Callback<List<LikeExtend>>{
            override fun onResponse(
                call: Call<List<LikeExtend>>,
                response: Response<List<LikeExtend>>
            ) {
                val listLike = response.body()
                callback(listLike,null)
            }

            override fun onFailure(call: Call<List<LikeExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}