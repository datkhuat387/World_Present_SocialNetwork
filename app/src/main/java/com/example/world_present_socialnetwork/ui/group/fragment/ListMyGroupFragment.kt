package com.example.world_present_socialnetwork.ui.group.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.world_present_socialnetwork.R
import com.example.world_present_socialnetwork.adapter.GroupAdapter
import com.example.world_present_socialnetwork.controllers.GroupController
import com.example.world_present_socialnetwork.databinding.FragmentListMyGroupBinding
import com.example.world_present_socialnetwork.model.comment.CommentsExtend
import com.example.world_present_socialnetwork.model.group.Group
import com.example.world_present_socialnetwork.ui.group.MyGroupActivity
import com.example.world_present_socialnetwork.ui.group.createGroup.CreateGroupActivity

class ListMyGroupFragment : Fragment() {
    private var _binding: FragmentListMyGroupBinding? = null
    private val binding get() = _binding!!
    private val groupController = GroupController()
    private lateinit var groupAdapter: GroupAdapter
    private var listGroupManage: List<Group> = listOf()
    private var idUser: String?  =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListMyGroupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupAdapter = GroupAdapter()
        binding.rcvManageGr.adapter = groupAdapter
        val sharedPreferences = activity?.getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUser?.let { getMyGroupManage(it) }
        binding.tvCreateGr.setOnClickListener {
            val intent = Intent(requireContext(),CreateGroupActivity::class.java)
            startActivity(intent)
        }
        groupAdapter.setListener(object :GroupAdapter.GroupListener{
            override fun onClickGroup(idGroup: String) {
                val intent = Intent(requireContext(),MyGroupActivity::class.java)
                intent.putExtra("idGroup",idGroup)
                startActivity(intent)
            }

        })
    }
    private fun getMyGroupManage(idUser: String){
        groupController.getMyGroupManage(idUser){listGroup,error->
            if(listGroup!=null){
                listGroupManage = listGroup
                groupAdapter.updateDataPost(listGroupManage)
            }else{
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        idUser?.let { getMyGroupManage(it) }
    }
}