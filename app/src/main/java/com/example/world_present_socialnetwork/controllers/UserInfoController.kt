package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.UserInfo
import com.example.world_present_socialnetwork.model.UserInfoExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
    fun updateCoverImage(id: String, coverImage: File?, callback: (UserInfo?, String?) -> Unit){
        val coverImagePart = if (coverImage != null) {
            val requestFile = coverImage.asRequestBody("coverImage/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("coverImage", coverImage.name, requestFile)
        } else {
            null
        }
        apiService.updateCoverImage(id,coverImagePart).enqueue(object : Callback<UserInfo>{
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
}