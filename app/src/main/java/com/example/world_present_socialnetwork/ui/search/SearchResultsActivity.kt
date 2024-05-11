package com.example.world_present_socialnetwork.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    private var textSearch: String? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textSearch = intent.getStringExtra("search")
        Log.e("TAG", "onCreate: $textSearch", )

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.edSearch.setText(textSearch)
        binding.edSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Chuyển sang hoạt động khác tại đây
                val intent = Intent(this@SearchResultsActivity,SearchActivity::class.java)
                intent.putExtra("textSearchNew",textSearch)
                startActivity(intent)
            }
            false
        }
        setUpTabLayout()
    }
    private fun setUpTabLayout(){
        listFragment.add(0,SearchAllFragment(textSearch))
        listFragment.add(1,SearchPostFragment(textSearch))
        listFragment.add(2,SearchUserFragment(textSearch))
        listFragment.add(3,GroupFragment(textSearch))
        listFragment.add(4,PageFragment(textSearch))
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