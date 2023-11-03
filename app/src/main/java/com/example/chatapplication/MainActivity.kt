package com.example.chatapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.chatapplication.databinding.ActivityMainBinding
import com.example.chatapplication.databinding.ActivitySignInBinding

class MainActivity:AppCompatActivity() {
    private   var _binding: ActivityMainBinding?=null
    private lateinit var navController: NavController
    private val binding: ActivityMainBinding? get() =_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            // If we are on the Home fragment, exit the app
            if (navController.currentDestination?.id == R.id.homeFragment) {
                moveTaskToBack(true)
            } else {
                super.onBackPressed()
            }
        }
    }
}