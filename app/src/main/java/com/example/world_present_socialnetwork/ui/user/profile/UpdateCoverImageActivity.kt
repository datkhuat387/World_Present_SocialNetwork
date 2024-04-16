package com.example.world_present_socialnetwork.ui.user.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserInfoController
import com.example.world_present_socialnetwork.databinding.ActivityUpdateCoverImageBinding
import java.io.File

class UpdateCoverImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCoverImageBinding
    private var imageUri: Uri? = null
    private var idUser: String? = null
    private val userInfoController = UserInfoController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCoverImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        imageUri = intent.getParcelableExtra("imageUri")
        Glide.with(applicationContext)
            .load(imageUri)
            .placeholder(R.drawable.cover_image_default)
            .error(R.drawable.cover_image_default)
            .into(binding.imgCover)
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.tvSave.setOnClickListener {
            idUser?.let { it1 -> updateCoverImage(it1) }
        }
    }

    private fun updateCoverImage(idUser: String){
        val imageFile = if (imageUri != null) {
            File(imageUri!!.path!!)
        } else {
            null
        }
        userInfoController.updateCoverImage(idUser,imageFile){userInfo, error->
            if(userInfo!=null){
                Toast.makeText(this@UpdateCoverImageActivity, "Đã thay đổi ảnh bìa", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this@UpdateCoverImageActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}