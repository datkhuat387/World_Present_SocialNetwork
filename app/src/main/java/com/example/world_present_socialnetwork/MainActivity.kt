package com.example.world_present_socialnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.world_present_socialnetwork.databinding.ActivityMainBinding
import com.example.world_present_socialnetwork.ui.fragment.friend.FriendFragment
import com.example.world_present_socialnetwork.ui.fragment.home.HomeFragment
import com.example.world_present_socialnetwork.ui.fragment.menu.MenuFragment
import com.example.world_present_socialnetwork.ui.fragment.notification.NotificationFragment

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPage()
        binding.bottomNav.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.nav_home -> binding.viewpg2.setCurrentItem(0,true)
                R.id.nav_friend -> binding.viewpg2.setCurrentItem(1, true)
                R.id.nav_notification -> binding.viewpg2.setCurrentItem(2, true)
                R.id.nav_menu -> binding.viewpg2.setCurrentItem(3, true)
            }
            true
        }
    }
    private fun initPage() {
        binding.viewpg2.adapter = HomeUserPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewpg2.isUserInputEnabled = false
    }
    private class HomeUserPagerAdapter(
        fragmentManager: FragmentManager, lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> FriendFragment()
                2 -> NotificationFragment()
                else -> MenuFragment()
            }
        }
    }
}