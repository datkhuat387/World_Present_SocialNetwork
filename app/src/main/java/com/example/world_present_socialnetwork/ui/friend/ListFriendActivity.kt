package com.example.world_present_socialnetwork.ui.friend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.FriendAllAdapter
import com.example.world_present_socialnetwork.controllers.FriendshipController
import com.example.world_present_socialnetwork.databinding.ActivityListFriendBinding
import com.example.world_present_socialnetwork.model.friend.FriendshipsExtend
import com.example.world_present_socialnetwork.ui.user.profile.MyProfileActivity
import com.example.world_present_socialnetwork.ui.user.profile.ProfileActivity

class ListFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListFriendBinding
    private lateinit var friendAllAdapter: FriendAllAdapter
    private val friendshipController = FriendshipController()
    private var listFriend: MutableList<FriendshipsExtend> = mutableListOf()
    private var idUser:String? = null
    private var idUserAt: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        friendAllAdapter = FriendAllAdapter()
        binding.rcvFriendAll.adapter = friendAllAdapter

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUserAt = intent.getStringExtra("idUserAt")

        idUserAt?.let { idUser?.let { it1 -> getListFriend(it1, it) } }
        friendAllAdapter.setListener(object : FriendAllAdapter.FriendAllListener{
            override fun onClickFriend(idUserAt: String) {
                if(idUser==idUserAt){
                    val intent = Intent(this@ListFriendActivity, MyProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@ListFriendActivity, ProfileActivity::class.java)
                    intent.putExtra("idUserAt",idUserAt)
                    startActivity(intent)
                }
            }

        })
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
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
    private fun getListFriend(idUser:String, idUserAt: String){
        friendshipController.getListFriendById(idUser,idUserAt){friend, error->
            if(friend!=null){
                friendAllAdapter.setUserId(idUserAt)
                listFriend = friend
                friendAllAdapter.updateDataPost(listFriend)
            }else{
                Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}