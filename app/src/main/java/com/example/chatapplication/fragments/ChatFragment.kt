package com.example.chatapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.chatapplication.R
import com.example.chatapplication.databinding.FragmentChatBinding

class ChatFragment:Fragment() {

    private   var _binding : FragmentChatBinding?=null
    private val binding:FragmentChatBinding? get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater,container,false)

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
