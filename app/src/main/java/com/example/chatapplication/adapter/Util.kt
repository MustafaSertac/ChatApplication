package com.example.chatapplication.adapter

import com.google.firebase.auth.FirebaseAuth

class Util {
    companion object{
        private val auth=FirebaseAuth.getInstance()
        private var userId:String=""
        fun getUiLoggedIn():String{
            if(auth.currentUser!=null){
            userId= auth.currentUser!!.uid
        }
            return userId
    }
}
}