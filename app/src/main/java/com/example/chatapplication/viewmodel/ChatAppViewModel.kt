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
import com.example.chatapplication.database.SharedPrefs
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

    init {
        getCurrentUser()
    }
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

val mySharedPref=SharedPrefs(context)
                    mySharedPref.setValue("username",users.username)
                    //val mysharedPrefs = SharedPrefs(context)
                //    mysharedPrefs.setValue("username", users.username!!)


                }


            }


    }
    fun sendMessage(sender: String, receiver: String, friendname: String, friendimage: String) =
        viewModelScope.launch(Dispatchers.IO) {

            val context = MyApplication.instance.applicationContext

            val hashMap = hashMapOf<String, Any>(
                "sender" to sender,
                "receiver" to receiver,
                "message" to message.value!!,
                "time" to Util.getTime()
            )


            val uniqueId = listOf(sender, receiver).sorted()
            uniqueId.joinToString(separator = "")


            val friendnamesplit = friendname.split("\\s".toRegex())[0]
            val mysharedPrefs = SharedPrefs(context)
            mysharedPrefs.setValue("friendid", receiver)
            mysharedPrefs.setValue("chatroomid", uniqueId.toString())
            mysharedPrefs.setValue("friendname", friendnamesplit)
            mysharedPrefs.setValue("friendimage", friendimage)




            firestore.collection("Messages").document(uniqueId.toString()).collection("chats")
                .document(Util.getTime()).set(hashMap).addOnCompleteListener { taskmessage ->


                    val setHashap = hashMapOf<String, Any>(
                        "friendid" to receiver,
                        "time" to Util.getTime(),
                        "sender" to Util.getUiLoggedIn(),
                        "message" to message.value!!,
                        "friendsimage" to friendimage,
                        "name" to friendname,
                        "person" to "you"
                    )


                    firestore.collection("Conversation${Util.getUiLoggedIn()}").document(receiver)
                        .set(setHashap)



                    firestore.collection("Conversation${receiver}").document(Util.getUiLoggedIn())
                        .update(
                            "message",
                            message.value!!,
                            "time",
                            Util.getTime(),
                            "person",
                            name.value!!
                        )
                    message.value=""


    }



}
}