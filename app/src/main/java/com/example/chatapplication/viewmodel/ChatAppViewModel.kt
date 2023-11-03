package com.example.chatapplication.viewmodel

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.MyApplication
import com.example.chatapplication.adapter.UserAdapter
import com.example.chatapplication.adapter.Util
import com.example.chatapplication.model.Users
import com.example.chatapplication.repository.UsersRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.Utils
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatAppViewModel : ViewModel() {



    val message = MutableLiveData<String>()
    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()


    val usersRepo = UsersRepo()

    fun getUsers(): LiveData<List<Users>> {
        return usersRepo.getUsers()


    }
    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO) {

        val context = MyApplication.instance.applicationContext


        firestore.collection("Users").document(Util.getUiLoggedIn())
            .addSnapshotListener { value, error ->


                if (value!!.exists() && value != null) {

                    val users = value.toObject(Users::class.java)
                    name.value = users?.username!!
                    imageUrl.value = users.imageUrl!!


                    //val mysharedPrefs = SharedPrefs(context)
                //    mysharedPrefs.setValue("username", users.username!!)


                }


            }


    }



}