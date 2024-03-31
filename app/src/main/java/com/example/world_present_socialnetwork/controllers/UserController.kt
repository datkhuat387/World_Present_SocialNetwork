package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.LoginRequest
import com.example.world_present_socialnetwork.model.User
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController {
    private val apiService: ApiService = RetrofitClient.apiService

    // login
    fun login(username: String, password: String, callback: (User?, String?) -> Unit){
        val loginRequest = LoginRequest(username, password)
        apiService.login(loginRequest).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    callback(user, null)
                } else {
                    callback(null, response.errorBody()?.string() ?: "Đăng nhập thất bại")
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
    // register
    fun register(username: String,password: String,fullname: String, callback: (User?, String?) -> Unit){
        val registerRequest = LoginRequest(username, password, fullname)
        apiService.register(registerRequest).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    callback(user, null)
                }else{
                    callback(null,response.errorBody()?.string() ?: "Đăng ký thất bại")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getUser(id: String, callback: (User?, String?) -> Unit){
        apiService.getUser(id).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    callback(user, null)
                }else{
                    callback(null,response.errorBody()?.string() ?: "Lỗi thông tin tài khoản" )
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}