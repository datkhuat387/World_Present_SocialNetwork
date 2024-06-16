package com.example.world_present_socialnetwork.ui.friend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.world_present_socialnetwork.databinding.ActivityConfirmFriendBinding

class ConfirmFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmFriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}