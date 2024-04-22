package com.example.world_present_socialnetwork.ui.group.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.databinding.FragmentPostGroupAllBinding

class PostGroupAllFragment : Fragment() {
    private var _binding: FragmentPostGroupAllBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPostGroupAllBinding.inflate(inflater,container,false)
        return binding.root
    }
}