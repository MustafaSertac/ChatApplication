package com.example.chatapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.chatapplication.R
import com.example.chatapplication.databinding.FragmentSettingBinding

class SettingFragment:Fragment() {

    private  var _binding : FragmentSettingBinding?=null
    private val binding:FragmentSettingBinding? get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding!!.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}