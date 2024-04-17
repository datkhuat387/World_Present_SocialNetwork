package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.Comments
import com.example.world_present_socialnetwork.model.CommentsExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentController {
    private val apiService: ApiService = RetrofitClient.apiService
    fun comment(idUser:String, idPost:String,comment:String, callback: (CommentsExtend?,String?)-> Unit){
        val comment = Comments(null, idUser, idPost, comment,null,null,null)
        apiService.comment(comment).enqueue(object : Callback<CommentsExtend>{
            override fun onResponse(call: Call<CommentsExtend>, response: Response<CommentsExtend>) {
                val comment = response.body()
                callback(comment,null)
            }

            override fun onFailure(call: Call<CommentsExtend>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
    fun updateComment(idComment: String, comment:String, callback: (CommentsExtend?, String?) -> Unit){
        val comment = Comments(null,null,null,comment,null,null,null)
        apiService.updateComment(idComment,comment).enqueue(object : Callback<CommentsExtend>{
            override fun onResponse(call: Call<CommentsExtend>, response: Response<CommentsExtend>) {
                val comment = response.body()
                callback(comment,null)
            }

            override fun onFailure(call: Call<CommentsExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun removeComment(idComment: String, callback: (CommentsExtend?, String?) -> Unit){
        apiService.deleteComment(idComment).enqueue(object : Callback<CommentsExtend>{
            override fun onResponse(call: Call<CommentsExtend>, response: Response<CommentsExtend>) {
                val comment = response.body()
                callback(comment,null)
            }

            override fun onFailure(call: Call<CommentsExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getListCommentByIdPost(idPost: String, callback: (MutableList<CommentsExtend>?, String?) -> Unit) {
        apiService.getComment(idPost).enqueue(object : Callback<MutableList<CommentsExtend>> {
            override fun onResponse(
                call: Call<MutableList<CommentsExtend>>,
                response: Response<MutableList<CommentsExtend>>
            ) {
                val listCmt = response.body()
                callback(listCmt, null)
            }

            override fun onFailure(call: Call<MutableList<CommentsExtend>>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
}
