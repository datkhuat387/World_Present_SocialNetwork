package com.example.world_present_socialnetwork.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.edSearch.setOnEditorActionListener{ _, actionId, event->
            if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ){
                Toast.makeText(this,"Search", Toast.LENGTH_SHORT).show()
                val textSearch = binding.edSearch.text.toString()
                val intent = Intent(this,SearchResultsActivity::class.java)
                intent.putExtra("search",textSearch)
                Log.e("TAG", "onCreate: $textSearch", )
                startActivity(intent)
                true
            }else{
                false
            }
        }
    }
}