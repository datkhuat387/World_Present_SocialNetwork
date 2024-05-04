package com.example.world_present_socialnetwork.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivitySplashBinding
import com.example.world_present_socialnetwork.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}