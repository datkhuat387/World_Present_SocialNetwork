package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.UserInfo
import com.example.world_present_socialnetwork.model.UserInfoExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoController {
    private val apiService: ApiService = RetrofitClient.apiService

    fun createUserInfo(idUser: String, callback: (UserInfo?, String?)->Unit){
        apiService.createUserInfo(idUser).enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful){
                    val userInfo = response.body()
                    callback(userInfo, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
    fun getUserInfo(idUser: String, callback: (UserInfoExtend?, String?) -> Unit){
        apiService.getUserInfo(idUser).enqueue(object : Callback<UserInfoExtend>{
            override fun onResponse(
                call: Call<UserInfoExtend>,
                response: Response<UserInfoExtend>
            ) {
                if(response.isSuccessful){
                    val userInfo = response.body()
                    callback(userInfo, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<UserInfoExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}