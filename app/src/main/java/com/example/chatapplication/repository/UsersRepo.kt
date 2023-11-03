package com.example.chatapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapplication.adapter.Util
import com.example.chatapplication.model.Users
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User


class UsersRepo {
    private var firestore=FirebaseFirestore.getInstance()

    fun getUsers():LiveData<List<Users>>{
        val users=MutableLiveData<List<Users>>()
        firestore.collection(("Users")).addSnapshotListener{snapshot,exception->
            if(exception!=null){
                return@addSnapshotListener
            }
            else {
                val userList= mutableListOf<Users>()
                if (snapshot != null) {
                    snapshot.documents.forEach {document->
                        val user=document.toObject(Users::class.java)
                        if(user!!.userid!=Util.getUiLoggedIn()){
                            userList.add(user)
                        }

                    }
                }
                users.value = userList

            }

        }
  return users }
}