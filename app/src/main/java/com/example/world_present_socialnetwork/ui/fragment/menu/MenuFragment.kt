package com.example.world_present_socialnetwork.ui.fragment.menu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.world_present_socialnetwork.MainActivity
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
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
        binding.tvId.text = id
        binding.btnLogOut.setOnClickListener {
            (activity as? MainActivity)?.performLogout()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}