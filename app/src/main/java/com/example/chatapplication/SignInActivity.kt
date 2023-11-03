package com.example.chatapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatapplication.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity:AppCompatActivity(),View.OnClickListener {
   private  var _binding: ActivitySignInBinding?=null
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit private var fbauth: FirebaseAuth
    lateinit private var pds: ProgressDialog
    private  val binding get() =  _binding
    private lateinit var loginButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding?.let {
           it. signInTextToSignUp.setOnClickListener (this)
loginButton=it.loginButton
           loginButton.setOnClickListener(this)
        }
    }

    override fun onStart() {
        super.onStart()
        fbauth=FirebaseAuth.getInstance()
        if(fbauth.currentUser!=null){
             startActivity(Intent(this,MainActivity::class.java))
        }
    }


    override fun onClick(v: View?) {
       when(v!!.id){
           binding!!.signInTextToSignUp.id->{
               startActivity(Intent(this,SignUpActivity::class.java))

           }
           loginButton.id ->{
               println("içerdeyim")
               binding?.let {
                   email=it.loginetemail.text.toString()
                   password=it.loginetpassword.text.toString()    }

                   if(email.isEmpty() || password.isEmpty()){
                  val toast=     Toast.makeText(this,"Empty cant be empty",Toast.LENGTH_SHORT)
                  toast.setGravity(Gravity.CENTER,0,0)
                       toast.show()
                       println("içerdeyim")
                   }
                   else{
                       println("içerdeyim")
signIn(password,email)
                   }



           }
           
       }
    }

    private fun signIn(password: String, email: String) {
        pds= ProgressDialog(this)
        pds.show()
        pds.setMessage("Sign In")
        fbauth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                pds.dismiss()
                startActivity(Intent(this,MainActivity::class.java))
            }
            else {
                pds.dismiss()
val toast=Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_LONG)
          toast.show()
            }
        }.addOnFailureListener {
            pds.dismiss()
            val toast=Toast.makeText(this,"Auth Failed",Toast.LENGTH_LONG)
        toast.show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        pds.dismiss()
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
pds.dismiss()
    }

}