package com.example.world_present_socialnetwork.ui.user.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.ViewPager2Adapter
import com.example.world_present_socialnetwork.controllers.FriendshipController
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.controllers.UserInfoController
import com.example.world_present_socialnetwork.databinding.ActivityProfileBinding
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PhotoProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.PostProfileFragment
import com.example.world_present_socialnetwork.ui.user.profile.fragment.ReelsProfileFragment
import com.example.world_present_socialnetwork.utils.Common
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var idUserAt: String? = null
    private val listFragment = mutableListOf<Fragment>()
    private var isFriend: Boolean? = false
    private var idUser: String? = null
    private var idFriendship: String? = null
    private lateinit var viewPager2Adapter: ViewPager2Adapter
    private val userController = UserController()
    private val friendshipController = FriendshipController()
    private val userInfoController = UserInfoController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUserAt = intent.getStringExtra("idUserAt")
        idUserAt?.let { getUser(it) }
        idUserAt?.let { getUserInfo(it) }
        idUser?.let { idUserAt?.let { it1 -> friend(it, it1) } }
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.btnAddFriend.setOnClickListener {
            if(isFriend == false){
                idUser?.let { it1 -> idUserAt?.let { it2 -> addFriend(it1, it2) } }
            }else{
                idFriendship?.let { it1 -> unFriend(it1) }
            }
        }
        binding.swipeToRefresh.setOnRefreshListener {
            if(binding.swipeToRefresh.isRefreshing){
                onResume()
            }
        }
        setUpTabLayout()
    }

    override fun onResume() {
        super.onResume()
        idUserAt?.let { getUser(it) }
        idUserAt?.let { getUserInfo(it) }
        idUser?.let { idUserAt?.let { it1 -> friend(it, it1) } }
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
                binding.swipeToRefresh.isRefreshing = false
            }else{
                Toast.makeText(this@ProfileActivity, "$error", Toast.LENGTH_SHORT).show()
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
//                Toast.makeText(this@ProfileActivity, "${userInfo.isActive}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@ProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun friend(idUser: String, idFriend: String){
        friendshipController.friend(idUser,idFriend){friend, error->
            if(friend!=null){
                idFriendship = friend._id
                if(friend.status == 0){
                    binding.btnAddFriend.text = "Chờ chấp nhận"
                    binding.btnAddFriend.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                    binding.btnAddFriend.setTextColor(ContextCompat.getColor(this,R.color.black))
                    binding.btnAddFriend.setBackgroundDrawable(ContextCompat.getDrawable(this,
                        R.drawable.shape_btn_profile_2))
                    binding.btnAddFriend.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                }else if(friend.status == 1){
                    isFriend = true
                    binding.btnAddFriend.text = "Bạn bè"
                    binding.btnAddFriend.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                    binding.btnAddFriend.setTextColor(ContextCompat.getColor(this,R.color.black))
                    binding.btnAddFriend.setBackgroundDrawable(ContextCompat.getDrawable(this,
                        R.drawable.shape_btn_profile_2))
                    binding.btnAddFriend.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                }else{
                    Toast.makeText(this@ProfileActivity, "Đã chặn người dùng.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else{
                Toast.makeText(this@ProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun addFriend(idUser: String,idFriend: String){
        friendshipController.addFriend(idUser,idFriend){friendships, error ->
            if(friendships!=null){
                binding.btnAddFriend.text = "Chờ chấp nhận"
                binding.btnAddFriend.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                binding.btnAddFriend.setTextColor(ContextCompat.getColor(this,R.color.black))
                binding.btnAddFriend.setBackgroundDrawable(ContextCompat.getDrawable(this,
                    R.drawable.shape_btn_profile_2))
                binding.btnAddFriend.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                Toast.makeText(this@ProfileActivity, "Đã gửi lời mời kết bạn.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@ProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun unFriend(idFriendship: String){
        friendshipController.unFriend(idFriendship){ friend, error->
            if(friend!=null){
                isFriend = false
                binding.btnAddFriend.text = "Thêm bạn bè"
                binding.btnAddFriend.setBackgroundDrawable(ContextCompat.getDrawable(this,
                    R.drawable.shape_btn_profile))
                binding.btnAddFriend.setTextColor(ContextCompat.getColor(this,R.color.white))
                binding.btnAddFriend.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_add_friend,0,0,0)
                Toast.makeText(this@ProfileActivity, "Đã hủy kết bạn.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@ProfileActivity, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setUpTabLayout(){
        listFragment.add(0, idUserAt?.let { PostProfileFragment(it) }!!)
        listFragment.add(1,PhotoProfileFragment())
        listFragment.add(2,ReelsProfileFragment())
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