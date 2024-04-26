package com.example.world_present_socialnetwork.controllers

import com.example.world_present_socialnetwork.model.group.GroupMember
import com.example.world_present_socialnetwork.model.group.GroupMemberExtend
import com.example.world_present_socialnetwork.network.ApiService
import com.example.world_present_socialnetwork.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupMemberController {
    private val apiService: ApiService = RetrofitClient.apiService

    fun joinGroup(idGroup: String, idUser: String, callback: (GroupMember?,String?)->Unit){
        val groupMember = GroupMember(null,idUser, idGroup,null,null,null)
        apiService.joinGroup(groupMember).enqueue(object : Callback<GroupMember>{
            override fun onResponse(call: Call<GroupMember>, response: Response<GroupMember>) {
                if(response.isSuccessful){
                    val groupMember = response.body()
                    callback(groupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<GroupMember>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun getJoin(idGroup: String, idUser: String, callback: (GroupMember?,String?)->Unit){
        apiService.getJoin(idGroup, idUser).enqueue(object : Callback<GroupMember>{
            override fun onResponse(call: Call<GroupMember>, response: Response<GroupMember>) {
                if(response.isSuccessful){
                    val groupMember = response.body()
                    callback(groupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<GroupMember>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun listWaitJoin(idGroup: String, callback: (MutableList<GroupMemberExtend>?, String?) -> Unit){
        apiService.listWaitJoin(idGroup).enqueue(object : Callback<MutableList<GroupMemberExtend>>{
            override fun onResponse(
                call: Call<MutableList<GroupMemberExtend>>,
                response: Response<MutableList<GroupMemberExtend>>
            ) {
                if(response.isSuccessful){
                    val listGroupMember = response.body()
                    callback(listGroupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<GroupMemberExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun listMember(idGroup: String, callback: (MutableList<GroupMemberExtend>?, String?) -> Unit){
        apiService.listMember(idGroup).enqueue(object : Callback<MutableList<GroupMemberExtend>>{
            override fun onResponse(
                call: Call<MutableList<GroupMemberExtend>>,
                response: Response<MutableList<GroupMemberExtend>>
            ) {
                if(response.isSuccessful){
                    val listGroupMember = response.body()
                    callback(listGroupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<GroupMemberExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun listMemberBan(idGroup: String, callback: (MutableList<GroupMemberExtend>?, String?) -> Unit){
        apiService.listMemberBan(idGroup).enqueue(object : Callback<MutableList<GroupMemberExtend>>{
            override fun onResponse(
                call: Call<MutableList<GroupMemberExtend>>,
                response: Response<MutableList<GroupMemberExtend>>
            ) {
                if(response.isSuccessful){
                    val listGroupMember = response.body()
                    callback(listGroupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<GroupMemberExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun listJoinedGroup(idUser: String, callback: (MutableList<GroupMemberExtend>?, String?) -> Unit){
        apiService.listJoinedGroup(idUser).enqueue(object : Callback<MutableList<GroupMemberExtend>>{
            override fun onResponse(
                call: Call<MutableList<GroupMemberExtend>>,
                response: Response<MutableList<GroupMemberExtend>>
            ) {
                if(response.isSuccessful){
                    val listGroup = response.body()
                    callback(listGroup,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<MutableList<GroupMemberExtend>>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }

    fun confirmJoin(idGroupMember: String, callback: (GroupMember?, String?) -> Unit){
        apiService.confirmJoin(idGroupMember).enqueue(object : Callback<GroupMember>{
            override fun onResponse(call: Call<GroupMember>, response: Response<GroupMember>) {
                if(response.isSuccessful){
                    val groupMember = response.body()
                    callback(groupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<GroupMember>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun cancelJoin(idGroupMember: String, callback: (GroupMemberExtend?, String?) -> Unit){
        apiService.cancelJoin(idGroupMember).enqueue(object : Callback<GroupMemberExtend>{
            override fun onResponse(call: Call<GroupMemberExtend>, response: Response<GroupMemberExtend>) {
                if(response.isSuccessful){
                    val groupMember = response.body()
                    callback(groupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<GroupMemberExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun banMember(idGroupMember: String, callback: (GroupMemberExtend?, String?) -> Unit){
        apiService.banMember(idGroupMember).enqueue(object : Callback<GroupMemberExtend>{
            override fun onResponse(call: Call<GroupMemberExtend>, response: Response<GroupMemberExtend>) {
                if(response.isSuccessful){
                    val groupMember = response.body()
                    callback(groupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<GroupMemberExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun kickMember(idGroupMember: String, callback: (GroupMemberExtend?, String?) -> Unit){
        apiService.kickMember(idGroupMember).enqueue(object : Callback<GroupMemberExtend>{
            override fun onResponse(call: Call<GroupMemberExtend>, response: Response<GroupMemberExtend>) {
                if(response.isSuccessful){
                    val groupMember = response.body()
                    callback(groupMember,null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<GroupMemberExtend>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
    fun outGroup(idGroupMember: String, callback: (String?, String?) -> Unit){
        apiService.outGroup(idGroupMember).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    callback("Đã rời khỏi nhóm",null)
                }else{
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(null, t.message)
            }

        })
    }
}