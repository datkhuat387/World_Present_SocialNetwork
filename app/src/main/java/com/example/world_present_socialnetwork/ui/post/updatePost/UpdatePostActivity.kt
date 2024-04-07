package com.example.world_present_socialnetwork.ui.post.updatePost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.ActivityUpdatePostBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class UpdatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePostBinding
    private var postController = PostController()
    private var imageUri: Uri? = null
    private var idPost: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        val idUser = sharedPreferences?.getString("id", "").toString()
        Log.e("test add", "$idUser")
        idPost = intent.getStringExtra("idPost")
        idPost?.let { updatePost(it) }
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

    private fun updatePost(idPost: String){
        binding.btnSave.setOnClickListener {
            val content = binding.edContent.text.toString()

            val imageFile = if (imageUri != null) {
                File(imageUri!!.path!!)
            } else {
                null
            }
            postController.updatePost(idPost,content,imageFile) { post, error ->
                if (post != null) {
                    Toast.makeText(this, "Đã chỉnh sửa bài viết", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    Log.e("TAG", "updatePost: $post")
                } else {
                    Toast.makeText(
                        this,
                        "Đã xảy ra lỗi: $error",
                        Toast.LENGTH_SHORT
                    ).show()
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
            binding.imagePost.setImageURI(imageUri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}