package com.example.world_present_socialnetwork.ui.group.createGroup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.GroupController
import com.example.world_present_socialnetwork.databinding.ActivityCreateGroupBinding

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding
    private var idUser: String? = null
    private var groupController = GroupController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        binding.imgExit.setOnClickListener {
            finish()
        }
        binding.btnCreateGr.setOnClickListener {
            val name = binding.tilNameGr.editText?.text.toString()
            val description = binding.tilDescription.editText?.text.toString()
            if(name.isEmpty()){
                Toast.makeText(this,"Tên nhóm không được để trống",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            idUser?.let { it1 -> createGroup(it1,name,description) }
        }
    }

    private fun createGroup(idUser: String, name: String, description: String){
        groupController.createGroup(idUser,name,description){group,error->
            if(group!=null){
                Toast.makeText(this, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show()
                finish()
                Log.e("TAG", "createGroup: $group" )
            }else{
                Toast.makeText(this, "Đã xảy ra lỗi khi tạo nhóm: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}