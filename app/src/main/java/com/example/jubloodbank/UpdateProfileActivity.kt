package com.example.jubloodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.jubloodbank.HomePage.HomePageActivity
import com.example.jubloodbank.databinding.ActivityProfileBinding
import com.example.jubloodbank.databinding.ActivityUpdateProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateProfileBinding
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = Firebase.auth.currentUser
        val userId = user?.uid
        val database = FirebaseDatabase.getInstance()
        val userRef = userId?.let { database.getReference("users").child(it) }



        val blood = ArrayList<String>()
        blood.addAll(resources.getStringArray(R.array.blood))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, blood)
        binding.bloodtype.setAdapter(adapter)


        GlobalScope.launch {


            if (userRef != null) {
                userRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val fieldValue = dataSnapshot.child("name").getValue(String::class.java)
                            val blood = dataSnapshot.child("blood").getValue(String::class.java)
                            val phone = dataSnapshot.child("phone").getValue(String::class.java)
                            if (fieldValue != null) {
                                // Set the retrieved field value (name) to your TextView
                                binding.Name.setText(fieldValue)
                                binding.bloodtype.setText(blood)
                                binding.PhoneNumber.setText(phone)
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle any errors or exceptions here
                    }
                })
            }
        }




        binding.update.setOnClickListener {

            if(binding.Name.text.toString().isEmpty())
            {
                binding.Name.error="Field Required"
            }
            else  if(binding.PhoneNumber.text.toString().isEmpty())
            {
                binding.PhoneNumber.error="Field Required"
            }
            else  if(binding.bloodtype.text.toString().isEmpty())
            {
                binding.bloodtype.error="Field Required"
            }
            else {
                val updates = hashMapOf<String, Any>(
                    "name" to binding.Name.text.toString(),
                    "phone" to binding.PhoneNumber.text.toString(),
                    "blood" to binding.bloodtype.text.toString()


                    // Add more fields to update as needed
                )
                userRef?.updateChildren(updates)?.addOnSuccessListener {
                    // The data was successfully updated
                }?.addOnFailureListener { e ->
                    // Handle the error here
                    Toast.makeText(this, "update failed", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, HomePageActivity::class.java))

            }
        }
    }
}