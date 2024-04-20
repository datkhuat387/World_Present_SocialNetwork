package com.example.world_present_socialnetwork.ui.user.update

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private var idUser: String? = null
    private val userController = UserController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUser?.let { getUser(it) }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnConfirm.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val phone = binding.tilPhone.editText?.text.toString()
            if(email.isEmpty()||phone.isEmpty()){
                Toast.makeText(
                    this@UpdateUserActivity,
                    "Email hoặc số điện thoại không được để trống",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.e("TAG", email+phone)
            idUser?.let { it1 -> updateUser(it1, email, phone) }
        }
    }
    private fun updateUser(idUser: String, email: String, phone: String){
        userController.updateUser(idUser,email,phone){user, error->
            if(user!=null){
                Toast.makeText(this@UpdateUserActivity, "Cập nhật thành công",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@UpdateUserActivity, "$error",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getUser(idUser: String){
        userController.getUser(idUser){user, error->
            if(user!=null){
                binding.tidEmail.setText(user.email)
                binding.tidPhone.setText(user.phone)
            }else{
                Toast.makeText(this@UpdateUserActivity, "$error",Toast.LENGTH_SHORT).show()
            }
        }
    }
}