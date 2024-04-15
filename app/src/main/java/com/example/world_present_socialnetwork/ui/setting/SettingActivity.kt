package com.example.world_present_socialnetwork.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.world_present_socialnetwork.databinding.ActivitySettingBinding
import com.example.world_present_socialnetwork.ui.user.changePassword.ChangePasswordActivity
import com.example.world_present_socialnetwork.ui.user.update.UpdateFullnameActivity
import com.example.world_present_socialnetwork.ui.user.update.UpdateUserActivity

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.tvBtnName.setOnClickListener {
            val intent = Intent(this,UpdateFullnameActivity::class.java)
            startActivity(intent)
        }
        binding.tvBtnEmailPhone.setOnClickListener {
            val intent = Intent(this,UpdateUserActivity::class.java)
            startActivity(intent)
        }
        binding.tvBtnChangePassword.setOnClickListener {
            val intent = Intent(this,ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }
}