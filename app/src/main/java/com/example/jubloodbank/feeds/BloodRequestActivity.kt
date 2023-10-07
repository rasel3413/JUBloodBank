package com.example.jubloodbank.feeds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jubloodbank.R
import com.example.jubloodbank.bloodlist.BloodAdapter
import com.example.jubloodbank.bloodlist.data.DataRequest
import com.example.jubloodbank.requestblood.RequestData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BloodRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_request)
        val database = FirebaseDatabase.getInstance().getReference("requests")
        val recycler=findViewById<RecyclerView>(R.id.recycler)


        val back=findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }


        recycler.layoutManager= LinearLayoutManager(this@BloodRequestActivity)
        val bloodRequestList = mutableListOf<DataRequest>()

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount!=bloodRequestList.size.toLong()) {
                    bloodRequestList.clear()
                    for (datasnap in snapshot.children) {

                        val ptname: String = datasnap.child("patient").value as String
                        val blood = datasnap.child("blood").getValue(String::class.java)
                        val amount = datasnap.child("amuont").getValue(String::class.java)
                        val date = datasnap.child("date").getValue(String::class.java)
                        val time = datasnap.child("time").getValue(String::class.java)
                        val hsname = datasnap.child("hname").getValue(String::class.java)
                        val location = datasnap.child("location").getValue(String::class.java)
                        val phone = datasnap.child("phone").getValue(String::class.java)

                        val notet:String = datasnap.child("note").value as String
                        val personname:String = datasnap.child("personName").value as String
                        val bloodEntity = DataRequest(ptname, blood, amount, date,time,hsname,location,personname,phone,notet)
                        bloodRequestList.add(bloodEntity)


                    }
                }

                recycler.adapter= RequestAdapter(bloodRequestList)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}