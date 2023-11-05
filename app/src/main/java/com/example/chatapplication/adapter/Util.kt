package com.example.chatapplication.adapter

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

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
        fun getTime(): String {
            val mFormat = SimpleDateFormat("ss/mm/HH/dd/MM", Locale.getDefault())

            val formatter = SimpleDateFormat("HH:mm:ss")
            val date: Date = Date(System.currentTimeMillis())
            val stringdate = formatter.format(date)


            return stringdate

        }
    }
}