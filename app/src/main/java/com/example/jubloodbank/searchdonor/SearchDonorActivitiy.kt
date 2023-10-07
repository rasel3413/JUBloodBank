package com.example.jubloodbank.searchdonor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jubloodbank.R
import com.example.jubloodbank.bloodlist.BloodAdapter
import com.example.jubloodbank.bloodlist.data.BloodEntity
import com.example.jubloodbank.databinding.ActivitySearchDonorActivitiyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchDonorActivitiy : AppCompatActivity() {
    lateinit var binding: ActivitySearchDonorActivitiyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchDonorActivitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val blood = ArrayList<String>()
        val hall = ArrayList<String>()
        blood.addAll(resources.getStringArray(R.array.blood))
        hall.addAll(resources.getStringArray(R.array.hall))

        val database = FirebaseDatabase.getInstance().getReference("users")



       binding.recycler.layoutManager=LinearLayoutManager(this@SearchDonorActivitiy)

        binding.recycler.visibility= View.GONE
        val bloodList = mutableListOf<BloodEntity>()


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, blood)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, hall)
        binding.bloodtype.setAdapter(adapter)
        binding.hallname.setAdapter(adapter2)
        binding.submit.setOnClickListener {
            if (binding.bloodtype.text.toString().isEmpty()) {
                binding.bloodtype.error = "This Field is required"
            } else if (binding.hallname.text.toString().isEmpty()) {
                binding.hallname.error = "This Field is required"
            } else {
                binding.recycler.visibility=View.VISIBLE

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

                                if(binding.hallname.text.toString()!="All"){
                                    if(binding.hallname.text.toString()!=hallName)continue
                                }
                                if(binding.bloodtype.text.toString()!=bloodGroup)continue
                                bloodList.add(bloodEntity)


                            }
                        }

                        if(bloodList.size==0){
                            Toast.makeText(this@SearchDonorActivitiy, "List is Empty", Toast.LENGTH_SHORT).show()
                        }

                        binding.recycler.adapter=BloodAdapter(bloodList)


                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })



            }
        }

    }
}