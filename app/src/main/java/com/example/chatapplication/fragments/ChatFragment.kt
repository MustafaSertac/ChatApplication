package com.example.chatapplication.fragments

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract.Directory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.chatapplication.R
import com.example.chatapplication.adapter.Util
import com.example.chatapplication.databinding.FragmentChatBinding
import com.example.chatapplication.viewmodel.ChatAppViewModel
import com.google.firebase.installations.Utils
import de.hdodenhof.circleimageview.CircleImageView

class ChatFragment:Fragment(),View.OnClickListener {

    private   var _binding : FragmentChatBinding?=null
    private lateinit var args: ChatFragmentArgs
private lateinit var circleImageView: CircleImageView
    lateinit var viewModel : ChatAppViewModel
    lateinit var toolbar: Toolbar
    private lateinit var tvName:TextView
    private lateinit var tvStatus:TextView
    private lateinit var backButton: ImageView
    private lateinit var chatButton: Button

    private val binding:FragmentChatBinding? get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater,container,false)

        return binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolBarChat)
        val circleImageView = toolbar.findViewById<CircleImageView>(R.id.chatImageViewUser)
        val textViewName = toolbar.findViewById<TextView>(R.id.chatUserName)
        val textViewStatus = view.findViewById<TextView>(R.id.chatUserStatus)
        val chatBackBtn = toolbar.findViewById<ImageView>(R.id.chatBackBtn)

        viewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)


        args = ChatFragmentArgs.fromBundle(requireArguments())

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner



        Glide.with(view.getContext()).load(args.users.imageUrl!!).placeholder(R.drawable.person).dontAnimate().into(circleImageView);
        textViewName.setText(args.users.username)
        textViewStatus.setText(args.users.status)


        chatBackBtn.setOnClickListener {


            view.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)

        }

        binding?.sendBtn?.setOnClickListener {


            viewModel.sendMessage(Util.getUiLoggedIn(), args.users.userid!!, args.users.username!!, args.users.imageUrl!!)





        }

    }

    override fun onClick(v: View?) {

    }

}
