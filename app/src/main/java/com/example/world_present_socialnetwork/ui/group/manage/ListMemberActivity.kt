package com.example.world_present_socialnetwork.ui.group.manage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivityListMemberBinding

class ListMemberActivity : AppCompatActivity() {
    private lateinit var binding:ActivityListMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}