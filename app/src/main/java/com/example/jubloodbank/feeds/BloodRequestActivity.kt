package com.example.jubloodbank.feeds

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jubloodbank.Fragment1
import com.example.jubloodbank.Fragment2
import com.example.jubloodbank.R
import com.example.jubloodbank.bloodlist.BloodAdapter
import com.example.jubloodbank.bloodlist.data.DataRequest
import com.example.jubloodbank.databinding.ActivityBloodRequestBinding
import com.example.jubloodbank.databinding.ActivityMainBinding
import com.example.jubloodbank.requestblood.RequestData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BloodRequestActivity : AppCompatActivity() {


    lateinit var binding: ActivityBloodRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance().getReference("requests")



        val back=findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        binding.fragment1.setOnClickListener {

            binding.fragment1.isChecked=true;
            binding.fragment2.isChecked=false;

            replaceFragment(Fragment1())
        }

        binding.fragment2.setOnClickListener {
            binding.fragment1.isChecked=false;
            binding.fragment2.isChecked=true;
            replaceFragment(Fragment2())
        }




    }
    private fun replaceFragment(fragment: Fragment) {

        val fragmentmanager=supportFragmentManager
        val fragmentTransaction=fragmentmanager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }
}