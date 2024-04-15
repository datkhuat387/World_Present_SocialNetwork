package com.example.world_present_socialnetwork.ui.friend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivityWaitFriendBinding

class WaitFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaitFriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}