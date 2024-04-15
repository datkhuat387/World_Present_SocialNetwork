package com.example.world_present_socialnetwork.ui.user.update

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.ActivityUpdateFullnameBinding

class UpdateFullnameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateFullnameBinding
    private var idUser: String? = null
    private val userController = UserController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateFullnameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUser?.let { getUser(it) }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnConfirm.setOnClickListener {
            val fullname = binding.tilFullname.editText?.text.toString()
            if(fullname.isEmpty()){
                Toast.makeText(
                    this@UpdateFullnameActivity,
                    "Họ và tên không được để trống",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            idUser?.let { it1 -> updateFUllname(it1, fullname) }
        }
    }
    private fun getUser(idUser: String){
        userController.getUser(idUser){user, error->
            if(user!=null){
                binding.tidFullname.setText(user.fullname)
            }else{
                Toast.makeText(this@UpdateFullnameActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateFUllname(idUser: String, fullname: String){
        userController.updateFullname(idUser,fullname){user, error ->
            if(user!=null){
                Toast.makeText(this@UpdateFullnameActivity, "Cập nhật thành công",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@UpdateFullnameActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}