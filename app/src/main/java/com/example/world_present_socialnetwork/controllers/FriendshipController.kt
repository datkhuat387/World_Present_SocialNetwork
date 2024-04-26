package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.friend.Friendships
import com.example.world_present_socialnetwork.model.friend.FriendshipsExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendshipController {
    private val apiService: ApiService = RetrofitClient.apiService
    fun addFriend(idUser: String, idFriend: String, callback: (Friendships?, String?)->Unit){
        val friendships = Friendships(null,idUser,idFriend,null,null,null)
        apiService.addFriend(friendships).enqueue(object : Callback<Friendships>{
            override fun onResponse(call: Call<Friendships>, response: Response<Friendships>) {
                if(response.isSuccessful){
                    val friend = response.body()
                    callback(friend, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Friendships>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun unFriend(idUser: String,callback: (String?,String?)->Unit){
        apiService.unFriend(idUser).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    callback("Đã hủy kết bạn", null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun friend(idUser: String, idFriend: String, callback: (Friendships?, String?) -> Unit){
        apiService.friend(idUser, idFriend).enqueue(object : Callback<Friendships>{
            override fun onResponse(call: Call<Friendships>, response: Response<Friendships>) {
                if(response.isSuccessful){
                    val friend = response.body()
                    callback(friend,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Friendships>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getListWaitConfirm(idUser: String, callback: (MutableList<FriendshipsExtend>?, String?) -> Unit){
        apiService.getListWaitConFirm(idUser).enqueue(object : Callback<MutableList<FriendshipsExtend>>{
            override fun onResponse(
                call: Call<MutableList<FriendshipsExtend>>,
                response: Response<MutableList<FriendshipsExtend>>
            ) {
                if(response.isSuccessful){
                    val list = response.body()
                    callback(list, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<FriendshipsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getListIsWaitConfirm(idUser: String, callback: (MutableList<FriendshipsExtend>?, String?) -> Unit){
        apiService.getListIsWaitConFirm(idUser).enqueue(object : Callback<MutableList<FriendshipsExtend>>{
            override fun onResponse(
                call: Call<MutableList<FriendshipsExtend>>,
                response: Response<MutableList<FriendshipsExtend>>
            ) {
                if(response.isSuccessful){
                    val list = response.body()
                    callback(list, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<FriendshipsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun notConFirmFriend(id: String, callback: (String?,String?) -> Unit){
        apiService.notConfirmFriend(id).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    callback("Đã xóa lời mời", null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(null, t.message)
            }
        })

    }
    fun confirmFriend(id: String, callback: (Friendships?, String?) -> Unit){
        apiService.confirmFriend(id).enqueue(object : Callback<Friendships>{
            override fun onResponse(call: Call<Friendships>, response: Response<Friendships>) {
                if(response.isSuccessful){
                    val friend = response.body()
                    callback(friend, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Friendships>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getListFriend(idUser: String, callback: (MutableList<FriendshipsExtend>?, String?) -> Unit){
        apiService.getListFriend(idUser).enqueue(object : Callback<MutableList<FriendshipsExtend>>{
            override fun onResponse(
                call: Call<MutableList<FriendshipsExtend>>,
                response: Response<MutableList<FriendshipsExtend>>
            ) {
                if(response.isSuccessful){
                    val friend = response.body()
                    callback(friend, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<FriendshipsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getListFriendById(idUser: String,idUserAt: String, callback: (MutableList<FriendshipsExtend>?, String?) -> Unit){
        apiService.getListFriendById(idUser,idUserAt).enqueue(object : Callback<MutableList<FriendshipsExtend>>{
            override fun onResponse(
                call: Call<MutableList<FriendshipsExtend>>,
                response: Response<MutableList<FriendshipsExtend>>
            ) {
                if(response.isSuccessful){
                    val friend = response.body()
                    callback(friend, null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<FriendshipsExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}