package com.example.jubloodbank.registration

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.CoroutineContext


fun Registration(user: User, pass: String, context: Context,callback:(Boolean)->Unit) {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()
    firebaseAuth.createUserWithEmailAndPassword(user.email, pass)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show()

                val userr = firebaseAuth.currentUser
                val userData = user
                val dbref = userr?.let { firebaseDatabase.reference.child("users").child(it.uid) }
                if (userr != null) {
                    userData.id=userr.uid

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(user.name) // Replace with the user's desired display name
                        // You can also set other profile attributes like photo URL here if needed
                        .build()

                    userr?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Profile updated successfully
                                val updatedUser = firebaseAuth.currentUser
                                val displayName = updatedUser?.displayName
                                // Now, the displayName is set for the user
                            } else {
                                // Profile update failed
                                // Handle the error
                            }
                        }

                }
                dbref?.setValue(userData)?.addOnSuccessListener {

                }
                    ?.addOnFailureListener { e ->
                        // Handle the error if data saving fails
                        Toast.makeText(
                            context,
                            "Error saving user data: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                callback(true)

            } else {
                Toast.makeText(context, "not successfull", Toast.LENGTH_SHORT).show()

                callback(false)
            }


        }
        .addOnFailureListener {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
            callback(false)
        }

}