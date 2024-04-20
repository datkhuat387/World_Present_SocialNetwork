package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.user.LoginRequest
import com.example.world_present_socialnetwork.model.user.User
import com.example.world_present_socialnetwork.model.user.UserChangePasswd
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
    fun updateUser(idUser: String, email: String, phone: String, callback: (User?, String?) -> Unit){
        val user = User(
            null,
            email,
            phone,
            null,
            null,
            null,
            null,
            null,
            null,
            null)
        apiService.updateUser(idUser,user).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    callback(user, null)
                }else{
                    callback(null,response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun updateFullname(idUser: String, fullname: String, callback: (User?, String?) -> Unit){
        val user = User(
            null,
           null,
            null,
            null,
            fullname,
            null,
            null,
            null,
            null,
            null)
        apiService.updateFullname(idUser, user).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    callback(user, null)
                }else{
                    callback(null,response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
    fun updateAvatar(idUser: String, avatar: File?, callback: (User?, String?) -> Unit){
        val avatarPart = if (avatar != null) {
            val requestFile = avatar.asRequestBody("avatar/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("avatar", avatar.name, requestFile)
        } else {
            null
        }
        apiService.updateAvatar(idUser, avatarPart).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    callback(user, null)
                }else{
                    callback(null,response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
    fun changePassword(idUser: String, currentPassword: String, newPassword: String, callback: (User?, String?) -> Unit){
        val changePasswd = UserChangePasswd(currentPassword, newPassword)
        apiService.userChangePasswd(idUser,changePasswd).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    callback(user, null)
                }else{
                    callback(null,response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}