package com.example.world_present_socialnetwork.ui.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.CommentController
import com.example.world_present_socialnetwork.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private var idPost: String? = null
    private val commentController = CommentController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        idPost = intent.getStringExtra("idPost")
    }

    private fun comment(){

    }
}