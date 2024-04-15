package com.example.world_present_socialnetwork.ui.user.changePassword

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private var idUser: String? = null
    private val userController = UserController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnConfirm.setOnClickListener {
            val currentPassword = binding.tilCurrentPasswd.editText?.text.toString()
            val newPassword = binding.tilNewPasswd.editText?.text.toString()
            if(currentPassword.isEmpty()||newPassword.isEmpty()){
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "Mật khẩu cũ hoặc mật khẩu mới không được để trống",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            idUser?.let { it1 -> changePassword(it1,currentPassword,newPassword) }
        }
    }
    private fun changePassword(idUser: String, currentPassword: String, newPassword: String){
        userController.changePassword(idUser, currentPassword,newPassword){user, error->
            if(user!=null){
                Toast.makeText(this@ChangePasswordActivity, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@ChangePasswordActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}