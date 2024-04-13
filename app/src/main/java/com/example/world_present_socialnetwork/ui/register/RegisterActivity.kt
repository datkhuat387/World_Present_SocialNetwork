package com.example.world_present_socialnetwork.ui.register

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.controllers.UserInfoController
import com.example.world_present_socialnetwork.databinding.ActivityRegisterBinding
import com.example.world_present_socialnetwork.model.User


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val userController = UserController()
    private val userInfoController = UserInfoController()
    private var idUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // register
        register()
        // back login
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
    private fun register(){
        binding.btnRegister.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val fullname = binding.tilFullname.editText?.text.toString()

            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(
                    this@RegisterActivity,
                    "Tên đăng nhập hoặc mật khẩu không được để trống",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(fullname.isEmpty()){
                Toast.makeText(
                    this@RegisterActivity,
                    "Họ và tên không được để trống",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            userController.register(email,password,fullname){user, error ->
                if(user!=null){
                    idUser = user._id
                    idUser?.let { it1 -> createUserInfo(it1) }
                    Toast.makeText(
                        this@RegisterActivity,
                        "Đăng ký thành công",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity,"$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun createUserInfo(idUser: String){
        userInfoController.createUserInfo(idUser){userInfo, error->
            if(userInfo!=null){
                Toast.makeText(this@RegisterActivity,"Đã tạo UserInfo", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@RegisterActivity,"$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}