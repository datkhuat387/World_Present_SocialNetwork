package com.example.world_present_socialnetwork.ui.friend

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.ActivityListFriendBinding

class ListFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListFriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edSearch.setOnEditorActionListener{v, actionId,event->
            if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)
                ){
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show()
                true
            }else{
                false
            }
        }
    }
}