package com.example.world_present_socialnetwork.ui.user.postSave

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivityPostSaveBinding

class PostSaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostSaveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }
    }
}