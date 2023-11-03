package com.example.chatapplication.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.adapter.OnItemClickListener
import com.example.chatapplication.adapter.UserAdapter
import com.example.chatapplication.databinding.FragmentHomeBinding
import com.example.chatapplication.model.Users
import com.example.chatapplication.viewmodel.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment:Fragment(), OnItemClickListener {
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
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onUserSelected(position: Int, users: Users) {

    }
}