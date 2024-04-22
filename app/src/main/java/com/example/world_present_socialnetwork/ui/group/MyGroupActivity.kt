package com.example.world_present_socialnetwork.ui.group

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.GroupController
import com.example.world_present_socialnetwork.databinding.ActivityMyGroupBinding
import com.example.world_present_socialnetwork.utils.Common

class MyGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyGroupBinding
    private val groupController = GroupController()
    private var idGroup: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idGroup = intent.getStringExtra("idGroup")
        idGroup?.let { getGroupDetail(it) }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
    private fun getGroupDetail(idGroup: String){
        groupController.getGroupDetail(idGroup){group,error->
            if(group!=null){
                Glide.with(applicationContext)
                    .load(Common.baseURL+ group.coverImage)
                    .placeholder(R.drawable.cover_image_default)
                    .error(R.drawable.cover_image_default)
                    .into(binding.imgCoverGr)

                Glide.with(applicationContext)
                    .load(Common.baseURL+ group.coverImage)
                    .placeholder(R.drawable.cover_image_default)
                    .error(R.drawable.cover_image_default)
                    .into(binding.imgCoverGrEx)

                binding.tvNameGr.text = group.name
                binding.tvNameGr2.text = group.name
            }else{
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}