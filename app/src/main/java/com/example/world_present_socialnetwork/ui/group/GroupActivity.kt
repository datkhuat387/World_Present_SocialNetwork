package com.example.world_present_socialnetwork.ui.group

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.GroupViewPage2Adapter
import com.example.world_present_socialnetwork.databinding.ActivityGroupBinding
import com.example.world_present_socialnetwork.ui.group.createGroup.CreateGroupActivity
import com.example.world_present_socialnetwork.ui.group.fragment.ListMyGroupFragment
import com.example.world_present_socialnetwork.ui.group.fragment.PostGroupAllFragment
import com.google.android.material.tabs.TabLayoutMediator

class GroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupBinding
    private val listFragment = mutableListOf<Fragment>()
    private lateinit var groupViewPage2Adapter: GroupViewPage2Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.imgAddGr.setOnClickListener {
            val intent = Intent(this,CreateGroupActivity::class.java)
            startActivity(intent)
        }
        setUpTabLayout()
    }

    private fun setUpTabLayout(){
        listFragment.add(0,PostGroupAllFragment())
        listFragment.add(1,ListMyGroupFragment())
        binding.vp2Group.isUserInputEnabled = false
        binding.vp2Group.setCurrentItem(0,true)
        groupViewPage2Adapter = GroupViewPage2Adapter(listFragment,this)
        binding.vp2Group.adapter = groupViewPage2Adapter

        val tabTitles = listOf("Dành cho bạn", "Nhóm của bạn")
        TabLayoutMediator(binding.tabGroup, binding.vp2Group) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}