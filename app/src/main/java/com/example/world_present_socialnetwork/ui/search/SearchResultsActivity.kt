package com.example.world_present_socialnetwork.ui.search

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.SearchViewPage2Adapter
import com.example.world_present_socialnetwork.databinding.ActivitySearchResultsBinding
import com.example.world_present_socialnetwork.ui.search.fragment.GroupFragment
import com.example.world_present_socialnetwork.ui.search.fragment.PageFragment
import com.example.world_present_socialnetwork.ui.search.fragment.SearchAllFragment
import com.example.world_present_socialnetwork.ui.search.fragment.SearchPostFragment
import com.example.world_present_socialnetwork.ui.search.fragment.SearchUserFragment
import com.google.android.material.tabs.TabLayoutMediator

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultsBinding
    private val listFragment = mutableListOf<Fragment>()
    private lateinit var searchViewPage2Adapter: SearchViewPage2Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val textSearch = intent.getStringExtra("search")
        Log.e("TAG", "onCreate: $textSearch", )
        binding.edSearch.setText(textSearch)
        setUpTabLayout()
    }
    private fun setUpTabLayout(){
        listFragment.add(0,SearchAllFragment())
        listFragment.add(1,SearchPostFragment())
        listFragment.add(2,SearchUserFragment())
        listFragment.add(3,GroupFragment())
        listFragment.add(4,PageFragment())
        binding.vp2Search.isUserInputEnabled = false
        binding.vp2Search.setCurrentItem(0,true)
        searchViewPage2Adapter = SearchViewPage2Adapter(listFragment,this)
        binding.vp2Search.adapter = searchViewPage2Adapter

        val tabTitles = listOf("Tất cả", "Bài viết", "Mọi người","Nhóm","Trang")
        TabLayoutMediator(binding.tabSearch, binding.vp2Search) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}