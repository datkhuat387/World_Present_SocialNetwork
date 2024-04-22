package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.group.Group
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupController {
    private val apiService: ApiService = RetrofitClient.apiService

    fun createGroup(idUser:String, name: String, description: String, callback: (Group?,String?)->Unit){
        val group = Group(
            null,
            name,
            null,
            null,
            description,
            null,
            null,
            null,
            null,
            null)
        apiService.createGroup(idUser, group).enqueue(object : Callback<Group>{
            override fun onResponse(call: Call<Group>, response: Response<Group>) {
                if(response.isSuccessful){
                    val group = response.body()
                    callback(group,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Group>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun getMyGroupManage(idUser: String, callback: (List<Group>?, String?) -> Unit){
        apiService.getMyGroupManage(idUser).enqueue(object : Callback<List<Group>>{
            override fun onResponse(call: Call<List<Group>>, response: Response<List<Group>>) {
                if(response.isSuccessful){
                    val listGroup = response.body()
                    callback(listGroup,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<List<Group>>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
    fun getGroupDetail(idGroup: String, callback: (Group?, String?) -> Unit){
        apiService.getGroupDetail(idGroup).enqueue(object : Callback<Group>{
            override fun onResponse(call: Call<Group>, response: Response<Group>) {
                if(response.isSuccessful){
                    val group = response.body()
                    callback(group,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Group>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}