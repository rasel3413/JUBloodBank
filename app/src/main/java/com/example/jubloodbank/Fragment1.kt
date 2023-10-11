package com.example.jubloodbank

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jubloodbank.bloodlist.data.DataRequest
import com.example.jubloodbank.feeds.RequestAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Fragment1:Fragment(R.layout.fragment1) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val database = FirebaseDatabase.getInstance().getReference("requests")
        val recycler=view.findViewById<RecyclerView>(R.id.recycler1)
        recycler.layoutManager= LinearLayoutManager(requireContext())
        val bloodRequestList = mutableListOf<DataRequest>()
        val currentUserUid = firebaseAuth.currentUser?.uid
        var id:String=""
        var uniqueid:String=""
        database.orderByPriority().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount!=bloodRequestList.size.toLong()) {
                    bloodRequestList.clear()
                    for (datasnap in snapshot.children) {

                        uniqueid= datasnap.key.toString()

                           id= datasnap.child("id").value as String
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

                  if (id == currentUserUid) bloodRequestList.add(bloodEntity)


                    }
                }

                recycler.adapter= RequestAdapter(requireContext(),bloodRequestList,uniqueid,true)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


}