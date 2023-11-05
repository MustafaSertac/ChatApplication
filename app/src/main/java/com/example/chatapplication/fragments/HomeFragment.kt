package com.example.chatapplication.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapplication.R
import com.example.chatapplication.SignInActivity
import com.example.chatapplication.adapter.OnItemClickListener
import com.example.chatapplication.adapter.UserAdapter
import com.example.chatapplication.databinding.FragmentHomeBinding
import com.example.chatapplication.model.RecentChats
import com.example.chatapplication.model.Users
import com.example.chatapplication.viewmodel.ChatAppViewModel
import com.example.chatmessenger.adapter.RecentChatAdapter
import com.example.chatmessenger.adapter.onChatClicked
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment:Fragment(), OnItemClickListener,onChatClicked {
    private lateinit var rvUsers : RecyclerView
    private lateinit var rvRecentChats : RecyclerView
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel : ChatAppViewModel
    private lateinit var circleImageView: CircleImageView
    lateinit var firestore : FirebaseFirestore
    private var _binding: FragmentHomeBinding?=null
    private lateinit var userAdapter: UserAdapter
    private lateinit var fbauth:FirebaseAuth
    private lateinit var homePd:ProgressDialog
    private lateinit var toolbar: Toolbar
    private  var recentChatAdapter= RecentChatAdapter()
    private val binding: FragmentHomeBinding? get() =_binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fbauth=FirebaseAuth.getInstance()
        homePd= ProgressDialog(requireContext())

        binding?.let {
            it.logOut.setOnClickListener{
                fbauth.signOut()
                startActivity(Intent(requireContext(),SignInActivity::class.java))


            }
            it.toolbarMain
            circleImageView=it.tlImage
            rvUsers=it.rvUsers
        }
        viewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        userAdapter= UserAdapter()
        val layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        rvUsers.layoutManager=layoutManager
onSubscribe()

    }
    private fun onSubscribe(){
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            rvUsers.adapter=userAdapter
            userAdapter.setList(it)
            userAdapter.setOnClickListener(this)

        })
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            Glide.with(requireActivity()).load(it).into(circleImageView)
        })
        viewModel.getRecentUsers().observe(viewLifecycleOwner, Observer {
  val recleview=binding?.rvRecentChats
            recleview!!.layoutManager=LinearLayoutManager(requireContext())
            recleview!!.adapter=recentChatAdapter
            recentChatAdapter.setOnChatClickListener(this)
            recentChatAdapter.setList(it)

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onUserSelected(position: Int, users: Users) {
findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment(users))


    }

    override fun getOnChatCLickedItem(position: Int, chatList: RecentChats) {

    }
}