package com.example.world_present_socialnetwork.ui.fragment.friend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.world_present_socialnetwork.adapter.CFriendAdapter
import com.example.world_present_socialnetwork.adapter.WFriendAdapter
import com.example.world_present_socialnetwork.controllers.FriendshipController
import com.example.world_present_socialnetwork.databinding.FragmentFriendBinding
import com.example.world_present_socialnetwork.model.FriendshipsExtend
import com.example.world_present_socialnetwork.ui.friend.ConfirmFriendActivity
import com.example.world_present_socialnetwork.ui.friend.WaitFriendActivity
import com.example.world_present_socialnetwork.ui.user.profile.ProfileActivity

class FriendFragment : Fragment() {
    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!
    private lateinit var cFriendAdapter: CFriendAdapter
    private lateinit var wFriendAdapter: WFriendAdapter
    private var listCFriend: MutableList<FriendshipsExtend>? = mutableListOf()
    private var listWFriend: MutableList<FriendshipsExtend>? = mutableListOf()
    private var friendshipController = FriendshipController()
    private var idUser:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cFriendAdapter = CFriendAdapter()
        binding.rcvConfirmFriend.adapter = cFriendAdapter
        wFriendAdapter = WFriendAdapter()
        binding.rcvWaitConfirm.adapter = wFriendAdapter

        val sharedPreferences = activity?.getSharedPreferences("profile", Context.MODE_PRIVATE)
        idUser = sharedPreferences?.getString("id","")
        idUser?.let { getListWaitConfirm(it) }
        idUser?.let { getListIsWaitConfirm(it) }

        binding.seeAll1.setOnClickListener {
            val intent = Intent(requireContext(),ConfirmFriendActivity::class.java)
            startActivity(intent)
        }
        binding.seeAll2.setOnClickListener {
            val intent = Intent(requireContext(),WaitFriendActivity::class.java)
            startActivity(intent)
        }
        cFriendAdapter.setListener(object : CFriendAdapter.CFriendListener{
            override fun onClickConfirm(friendshipsExtend: FriendshipsExtend) {
                Toast.makeText(context, "Xác nhận", Toast.LENGTH_SHORT).show()
            }

            override fun onClickDelete(friendshipsExtend: FriendshipsExtend) {
                Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show()
            }

            override fun onClickProfile(idUserAt: String) {
                val intent = Intent(requireContext(), ProfileActivity::class.java)
                intent.putExtra("idUserAt",idUserAt)
                startActivity(intent)
            }

        })
        wFriendAdapter.setListener(object : WFriendAdapter.WFriendListener{
            override fun onClickCancel(friendshipsExtend: FriendshipsExtend) {
                Toast.makeText(context, "Hủy lời mời", Toast.LENGTH_SHORT).show()
            }

            override fun onClickProfile(idUserAt: String) {
                val intent = Intent(requireContext(), ProfileActivity::class.java)
                intent.putExtra("idUserAt",idUserAt)
                startActivity(intent)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        idUser?.let { getListWaitConfirm(it) }
        idUser?.let { getListIsWaitConfirm(it) }
    }
    private fun getListWaitConfirm(idUser: String){
        friendshipController.getListWaitConfirm(idUser){list, error->
            if(list!=null){
                listCFriend = list
                cFriendAdapter.updateCFriend(listCFriend!!)
            }else{
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "getListWait: $error" )
            }
        }
    }
    private fun getListIsWaitConfirm(idUser: String){
        friendshipController.getListIsWaitConfirm(idUser){list, error->
            if(list!=null){
                listWFriend = list
                wFriendAdapter.updateWFriend(listWFriend!!)
            }else{
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "getListIsWait: $error" )
            }
        }
    }
}