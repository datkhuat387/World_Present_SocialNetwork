package com.example.world_present_socialnetwork.ui.group.manage

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivityManageBinding

class ManageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.tvRequestMembership.setOnClickListener {
            val intent = Intent(this,ListWaitJoinActivity::class.java)
            startActivity(intent)
        }
        binding.tvEverybody.setOnClickListener {
            val intent = Intent(this,ListMemberActivity::class.java)
            startActivity(intent)
        }
        binding.tvSettingGroup.setOnClickListener {
            val intent = Intent(this,SettingGroupActivity::class.java)
            startActivity(intent)
        }
    }
}