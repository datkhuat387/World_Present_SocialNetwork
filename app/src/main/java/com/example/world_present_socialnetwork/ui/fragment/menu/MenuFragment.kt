package com.example.world_present_socialnetwork.ui.fragment.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.world_present_socialnetwork.MainActivity
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.FragmentMenuBinding
import com.example.world_present_socialnetwork.ui.group.GroupActivity
import com.example.world_present_socialnetwork.ui.group.MyGroupActivity
import com.example.world_present_socialnetwork.ui.group.createGroup.CreateGroupActivity
import com.example.world_present_socialnetwork.ui.setting.SettingActivity
import com.example.world_present_socialnetwork.ui.user.postSave.PostSaveActivity
import com.example.world_present_socialnetwork.utils.Common

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val userController = UserController()
    private var id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("profile", Context.MODE_PRIVATE)
        id = sharedPreferences?.getString("id","")
        Log.e("test1", "$id")
        id?.let { getUser(it) }
        binding.tvSetting.setOnClickListener {
            val intent = Intent(context,SettingActivity::class.java)
            startActivity(intent)
        }
        binding.tvPostSave.setOnClickListener {
            val intent = Intent(context, PostSaveActivity::class.java)
            startActivity(intent)
        }
        binding.tvGroup.setOnClickListener {
            val intent = Intent(requireContext(),GroupActivity::class.java)
            startActivity(intent)
        }
        binding.tvLogOut.setOnClickListener {
            (activity as? MainActivity)?.performLogout()
        }
    }
    private fun getUser(id: String){
        userController.getUser(id){ user, error ->
            if (user != null) {
                binding.tvFullname.text = user.fullname

                Glide.with(requireContext())
                    .load(Common.baseURL+user.avatar)
                    .placeholder(R.drawable.avatar_profile)
                    .error(R.drawable.avatar_profile)
                    .into(binding.imgAvatar)
            }else{
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        id?.let { getUser(it) }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}