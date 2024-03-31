package com.example.world_present_socialnetwork.ui.post.addPost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.ActivityAddPostBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private var postController = PostController()
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        val idUser = sharedPreferences?.getString("id", "").toString()
        Log.e("test add", "$idUser")
        addPost(idUser)
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgAddImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

    }
    private fun addPost(id: String) {
        binding.btnAdd.setOnClickListener {
            val content = binding.edContent.text.toString()

            val imageFile = if (imageUri != null) {
                File(imageUri!!.path!!)
            } else {
                null
            }

            postController.createPost(id, content, imageFile) { post, error ->
                if (post != null) {
                    Toast.makeText(this, "Bài viết đã được tạo thành công", Toast.LENGTH_SHORT).show()
                    finish()
                    Log.e("TAG", "addPost: $post" )
                } else {
                    Toast.makeText(this, "Đã xảy ra lỗi khi tạo bài viết: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            imageUri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
//            imgProfile.setImageURI(fileUri)
            binding.imagePost.setImageURI(imageUri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}