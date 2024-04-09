package com.example.world_present_socialnetwork.ui.user.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.ViewPager2Adapter
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.ActivityProfileBinding
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PhotoProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PostProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.ReelsProfileFragment
import com.example.world_present_socialnetwork.utils.Common
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var idUserAt: String? = null
    private val listFragment = mutableListOf<Fragment>()
    private lateinit var viewPager2Adapter: ViewPager2Adapter
    private val userController = UserController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUserAt = intent.getStringExtra("idUserAt")
        Log.e("TAG", "getPost: $idUserAt" )
        Toast.makeText(this, "$idUserAt", Toast.LENGTH_SHORT).show()
        idUserAt?.let { getUser(it) }
        binding.imgBack.setOnClickListener {
            finish()
        }
        setUpTabLayout()
    }
    private fun getUser(idUser: String){
        userController.getUser(idUser){user,error->
            if(user!=null){
                binding.tvName.text = user.fullname
                binding.tvFullname.text = user.fullname
                Glide.with(applicationContext)
                    .load(Common.baseURL+user.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(binding.imgAvatar)
            }else{
                Toast.makeText(this@ProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setUpTabLayout(){
        listFragment.add(0,PostProfileFragment())
        listFragment.add(1,PhotoProfileFragment())
        listFragment.add(2,ReelsProfileFragment())
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