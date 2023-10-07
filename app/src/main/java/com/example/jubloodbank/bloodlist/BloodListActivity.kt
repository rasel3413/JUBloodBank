package com.example.jubloodbank.bloodlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jubloodbank.R
import com.example.jubloodbank.bloodlist.data.BloodEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BloodListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_list)
        val database = FirebaseDatabase.getInstance().getReference("users")

        val recycler=findViewById<RecyclerView>(R.id.recycler)

        recycler.layoutManager=LinearLayoutManager(this@BloodListActivity)
        val bloodList = mutableListOf<BloodEntity>()


        var back=findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            simulateBackButtonPress()
        }




        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                if(snapshot.childrenCount!=bloodList.size.toLong()) {
                    bloodList.clear()
                    for (datasnap in snapshot.children) {

                        val name: String = datasnap.child("name").value as String
                        val phoneNumber = datasnap.child("phone").getValue(String::class.java)
                        val hallName = datasnap.child("hallName").getValue(String::class.java)
                        val bloodGroup = datasnap.child("blood").getValue(String::class.java)
                        val bloodEntity = BloodEntity(name, phoneNumber, hallName, bloodGroup)
                        bloodList.add(bloodEntity)


                    }
                }

                recycler.adapter=BloodAdapter(bloodList)


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





    }
    fun simulateBackButtonPress() {
        onBackPressed()
    }
}