package com.example.jubloodbank.login

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth



    lateinit var firebaseAuth: FirebaseAuth

    fun login(email: String, password: String,context:Context,callback: (Boolean) -> Unit) {

        firebaseAuth = FirebaseAuth.getInstance()


        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                    Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show()
                     callback(true)


                } else {
                    // Login failed, handle the error
                    val exception = task.exception
                    Toast.makeText(context, "$exception", Toast.LENGTH_SHORT).show()
                    // You can access the error message with exception?.message
                    callback(false)

                }

            }

    }
