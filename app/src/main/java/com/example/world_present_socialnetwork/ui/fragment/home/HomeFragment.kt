package com.example.world_present_socialnetwork.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.world_present_socialnetwork.adapter.PostAdapter
import com.example.world_present_socialnetwork.controllers.PostController
import com.example.world_present_socialnetwork.databinding.FragmentHomeBinding
import com.example.world_present_socialnetwork.model.PostsExtend
import com.example.world_present_socialnetwork.ui.comment.CommentActivity
import com.example.world_present_socialnetwork.ui.post.addPost.AddPostActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter
    private val postController = PostController()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postAdapter = PostAdapter()
        binding.recycleviewPost.adapter = postAdapter

        binding.imgAddPost.setOnClickListener {
            val intent = Intent(requireContext(),AddPostActivity::class.java)
            startActivity(intent)
        }
        binding.imgSearch.setOnClickListener {

        }
        postAdapter.setListener(object : PostAdapter.PostListener{
            override fun onClickComment(idPost: String) {
                val intent = Intent(requireContext(), CommentActivity::class.java)
                startActivity(intent)
            }

            override fun onClickLike(idPost: String) {
                TODO("Not yet implemented")
            }

        })
        binding.swipeToRefresh.setOnRefreshListener {
            if(binding.swipeToRefresh.isRefreshing){
                getAllPost()
            }
        }
        getAllPost()
    }
    private fun getAllPost(){

        postController.getAllPost { list, error ->
            if (list != null) {
                // Cập nhật danh sách bài viết trên postAdapter
                postAdapter.updateDataPost(list as MutableList<PostsExtend>)
                binding.swipeToRefresh.isRefreshing = false
            } else {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onResume() {
        super.onResume()
        getAllPost()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}