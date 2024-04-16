package com.example.world_present_socialnetwork.ui.user.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.ViewPager2Adapter
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.controllers.UserInfoController
import com.example.world_present_socialnetwork.databinding.ActivityMyProfileBinding
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PhotoProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PostProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.ReelsProfileFragment
import com.example.world_present_socialnetwork.utils.Common
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding
    private val listFragment = mutableListOf<Fragment>()
    private lateinit var viewPager2Adapter: ViewPager2Adapter
    private var idUser: String? = null
    private var imageUri: Uri? = null
    private var isPicker: Boolean = false
    private val userController = UserController()
    private val userInfoController = UserInfoController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")

        idUser?.let { getUser(it) }
        idUser?.let { getUserInfo(it) }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.addAvatar.setOnClickListener {
            isPicker = false
            imagePicker()
        }
        binding.addCover.setOnClickListener {
            isPicker = true
            imagePicker()
        }
        binding.btnUpdateProfile.setOnClickListener {
            Toast.makeText(this,"Update Profile",Toast.LENGTH_SHORT).show()
        }
        binding.btnExtend.setOnClickListener {
            Toast.makeText(this,"Extend",Toast.LENGTH_SHORT).show()
        }
        setUpTabLayout()
    }

    override fun onResume() {
        super.onResume()
        idUser?.let { getUser(it) }
        idUser?.let { getUserInfo(it) }
    }
    private fun imagePicker(){
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }
    private fun getUser(idUser: String){
        userController.getUser(idUser){user,error->
            if(user!=null){
                binding.tvName.text = user.fullname
                binding.tvFullname.text = user.fullname
                Glide.with(applicationContext)
                    .load(Common.baseURL+user.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(binding.imgAvatar)
            }else{
                Toast.makeText(this@MyProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getUserInfo(idUser: String){
        userInfoController.getUserInfo(idUser){userInfo, error->
            if(userInfo!=null){
                Glide.with(applicationContext)
                    .load(Common.baseURL+userInfo.coverImage)
                    .placeholder(R.drawable.cover_image_default)
                    .error(R.drawable.cover_image_default)
                    .into(binding.imgCover)
                Toast.makeText(this@MyProfileActivity, "${userInfo.isActive}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MyProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            imageUri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
            if(isPicker){
                val intent = Intent(this, UpdateCoverImageActivity::class.java)
                intent.putExtra("imageUri", imageUri)
                startActivity(intent)
            }else{
                val intent = Intent(this, UpdateAvatarActivity::class.java)
                intent.putExtra("imageUri", imageUri)
                startActivity(intent)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUpTabLayout(){
        listFragment.add(0, PostProfileFragment())
        listFragment.add(1, PhotoProfileFragment())
        listFragment.add(2, ReelsProfileFragment())
        viewPager2Adapter = ViewPager2Adapter(listFragment, this)
        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.setCurrentItem(0,true)
        binding.viewBgItem1.visibility = View.VISIBLE
        binding.viewBgItem2.visibility = View.INVISIBLE
        binding.viewBgItem3.visibility = View.INVISIBLE
        binding.viewPager2.adapter = viewPager2Adapter
        binding.itemPost.setOnClickListener{
            binding.viewPager2.setCurrentItem(0,true)
            binding.viewBgItem1.visibility = View.VISIBLE
            binding.viewBgItem2.visibility = View.INVISIBLE
            binding.viewBgItem3.visibility = View.INVISIBLE
            binding.tvPost.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
            binding.tvImage.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvReels.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        }
        binding.itemImage.setOnClickListener {
            binding.viewPager2.setCurrentItem(1,true)
            binding.viewBgItem1.visibility = View.INVISIBLE
            binding.viewBgItem2.visibility = View.VISIBLE
            binding.viewBgItem3.visibility = View.INVISIBLE
            binding.tvPost.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvImage.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
            binding.tvReels.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        }
        binding.itemReels.setOnClickListener {
            binding.viewPager2.setCurrentItem(2,true)
            binding.viewBgItem1.visibility = View.INVISIBLE
            binding.viewBgItem2.visibility = View.INVISIBLE
            binding.viewBgItem3.visibility = View.VISIBLE
            binding.tvPost.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvImage.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            binding.tvReels.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
        }
    }
}