package com.example.world_present_socialnetwork.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.world_present_socialnetwork.MainActivity
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.ActivityLoginBinding
import com.example.world_present_socialnetwork.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userController = UserController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")
        if (id.isNullOrEmpty()) {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            // login
            login()
            // next register
            binding.tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login() {
        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Email hoặc mật khẩu không được để trống",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            userController.login(email, password) { user, error ->
                if (user != null) {
                    // login success
                    val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("id", user._id)
                    editor.apply()
                    Log.e("test3", user._id)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        this@LoginActivity,
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Error
                    Toast.makeText(this@LoginActivity, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
