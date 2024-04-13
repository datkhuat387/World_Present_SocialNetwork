package com.example.world_present_socialnetwork.ui.user.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.ViewPager2Adapter
import com.example.world_present_socialnetwork.databinding.ActivityMyProfileBinding
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PhotoProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PostProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.ReelsProfileFragment

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private val listFragment = mutableListOf<Fragment>()
    private lateinit var viewPager2Adapter: ViewPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.addAvatar.setOnClickListener {
            Toast.makeText(this,"Add Avatar",Toast.LENGTH_SHORT).show()
        }
        binding.addCover.setOnClickListener {
            Toast.makeText(this,"Add Cover",Toast.LENGTH_SHORT).show()
        }
        setUpTabLayout()
    }
    private fun setUpTabLayout(){
        listFragment.add(0, PostProfileFragment())
        listFragment.add(1, PhotoProfileFragment())
        listFragment.add(2, ReelsProfileFragment())
        viewPager2Adapter = ViewPager2Adapter(listFragment, this)
        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.setCurrentItem(0,true)
        binding.viewBgItem1.visibility = View.VISIBLE
        binding.viewBgItem2.visibility = View.INVISIBLE
        binding.viewBgItem3.visibility = View.INVISIBLE
        binding.viewPager2.adapter = viewPager2Adapter
        binding.itemPost.setOnClickListener{
            binding.viewPager2.setCurrentItem(0,true)
            binding.viewBgItem1.visibility = View.VISIBLE
            binding.viewBgItem2.visibility = View.INVISIBLE
            binding.viewBgItem3.visibility = View.INVISIBLE
            binding.tvPost.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
            binding.tvImage.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvReels.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        }
        binding.itemImage.setOnClickListener {
            binding.viewPager2.setCurrentItem(1,true)
            binding.viewBgItem1.visibility = View.INVISIBLE
            binding.viewBgItem2.visibility = View.VISIBLE
            binding.viewBgItem3.visibility = View.INVISIBLE
            binding.tvPost.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvImage.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
            binding.tvReels.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        }
        binding.itemReels.setOnClickListener {
            binding.viewPager2.setCurrentItem(2,true)
            binding.viewBgItem1.visibility = View.INVISIBLE
            binding.viewBgItem2.visibility = View.INVISIBLE
            binding.viewBgItem3.visibility = View.VISIBLE
            binding.tvPost.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvImage.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvReels.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
        }
    }
}