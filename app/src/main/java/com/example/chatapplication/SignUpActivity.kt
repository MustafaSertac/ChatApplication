package com.example.chatapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
 import com.example.chatapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpActivity:AppCompatActivity (),View.OnClickListener{
   private var _binding: ActivitySignUpBinding?=null
    private val binding : ActivitySignUpBinding? get() =_binding
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var signUpAuth:FirebaseAuth
    private lateinit var name:String
    private lateinit var password:String
    private lateinit var signUpPds:ProgressDialog
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
fireStore=FirebaseFirestore.getInstance()
        signUpAuth=FirebaseAuth.getInstance()
        signUpPds=ProgressDialog(this)
        binding?.let {
            it.signUpBtn.setOnClickListener(this)
        it.signUpTextToSignIn.setOnClickListener(this)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding!!.signUpBtn.id->{
                binding?.let {
                    name=it.signUpEtName.text.toString()
                    email=it.signUpEmail.text.toString()
                    password=it.signUpPassword.text.toString()
                }
                if(email.isNullOrEmpty() && password.isNullOrEmpty()&&name.isNullOrEmpty()){
                    val toast=Toast.makeText(this,"Empty cant be Empty",Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }
                else {
                    signUpUser(email,password,name)
                }

            }
            binding!!.signUpTextToSignIn.id->{
                startActivity(Intent(this,SignInActivity::class.java))
       finish()

            }
        }
    }

    private fun signUpUser(email: String, password: String, name: String) {
        signUpPds.show()
        signUpPds.setMessage("Sign Up")
        signUpAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
val user=signUpAuth.currentUser

                val hashMap= hashMapOf("userid" to user!!.uid!!,"username" to name,"useremail" to email,"status" to "default","imageUrl" to "https://www.pngarts.com/files/11/Elon-Musk-PNG-Picture.png")
fireStore.collection("Users").document(user.uid).set(hashMap)
                signUpPds.dismiss()
                startActivity(Intent(this,SignInActivity::class.java))
                val toast=Toast.makeText(this,"Singup is succesfull",Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
            }
            else{
                val toast=Toast.makeText(this,"Create Acount Failed",Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                signUpPds.dismiss()
            }
        }.addOnCompleteListener {

        }

    }
}