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
import com.example.world_present_socialnetwork.MainActivity
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.controllers.UserController
import com.example.world_present_socialnetwork.databinding.FragmentMenuBinding
import com.example.world_present_socialnetwork.ui.setting.SettingActivity

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val userController = UserController()
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
        var id: String? = sharedPreferences?.getString("id","")
        Log.e("test1", "$id")

        id?.let {
            userController.getUser(it){ user, error ->
                if (user != null) {
                    binding.tvFullname.text = user.fullname
                    binding.tvEmail.text = user.email
                }else{
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.tvSetting.setOnClickListener {
            val intent = Intent(context,SettingActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogOut.setOnClickListener {
            (activity as? MainActivity)?.performLogout()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}