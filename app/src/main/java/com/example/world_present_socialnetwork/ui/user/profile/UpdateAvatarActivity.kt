package com.example.world_present_socialnetwork.ui.user.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.ActivityUpdateAvatarBinding
import com.example.world_present_socialnetwork.databinding.ActivityUpdateUserBinding
import com.example.world_present_socialnetwork.utils.Common
import java.io.File

class UpdateAvatarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAvatarBinding
    private var imageUri: Uri? = null
    private var idUser: String? = null
    private val userController = UserController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        imageUri = intent.getParcelableExtra("imageUri")

        Glide.with(applicationContext)
            .load(imageUri)
            .placeholder(R.drawable.avatar_profile)
            .error(R.drawable.avatar_profile)
            .into(binding.imgAvatar)

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.tvSave.setOnClickListener {
            idUser?.let { it1 -> updateAvatar(it1) }
        }
    }
    private fun updateAvatar(idUser: String) {
        val imageFile = if (imageUri != null) {
            File(imageUri!!.path!!)
        } else {
            null
        }
        userController.updateAvatar(idUser,imageFile){user, error->
            if(user!=null){
                Toast.makeText(this@UpdateAvatarActivity, "Đã thay đổi ảnh đại diện", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Log.e("TAG", "updateAvatar: $error", )
                Toast.makeText(this@UpdateAvatarActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}