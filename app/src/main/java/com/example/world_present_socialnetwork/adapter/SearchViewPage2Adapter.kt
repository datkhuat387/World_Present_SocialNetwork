package com.example.world_present_socialnetwork.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchViewPage2Adapter(
    private val listFragment: MutableList<Fragment>,
    supportFragmentManager: FragmentActivity):
    FragmentStateAdapter(supportFragmentManager) {
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}