package com.example.jubloodbank.HomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.jubloodbank.ProfileActivity
import com.example.jubloodbank.R
import com.example.jubloodbank.bloodlist.BloodListActivity
import com.example.jubloodbank.databinding.ActivityHomePageBinding
import com.example.jubloodbank.feeds.BloodRequestActivity
import com.example.jubloodbank.registration.User
import com.example.jubloodbank.requestblood.RequestBloodActivity
import com.example.jubloodbank.searchdonor.SearchDonorActivitiy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomePageActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomePageBinding
    private var imageslist= mutableListOf<Int>()

    val auth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user: FirebaseUser? = auth.currentUser
        val userId=user!!.uid
        val database = FirebaseDatabase.getInstance()
        val userReference: DatabaseReference = database.getReference("users").child(userId)


        binding.username.text=user.displayName

        binding.profileImageView.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        binding.username.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }

        binding.alldonor.setOnClickListener{
            startActivity(Intent(this,BloodListActivity::class.java))
        }
        binding.requestblood.setOnClickListener {
            startActivity(Intent(this,RequestBloodActivity::class.java))

        }
        binding.cvSearchDonor.setOnClickListener {
            startActivity(Intent(this,SearchDonorActivitiy::class.java))
        }
        binding.feed.setOnClickListener {
            startActivity(Intent(this,BloodRequestActivity::class.java))
        }

        imageslist.add(R.drawable.ddonateblood)
        imageslist.add(R.drawable.img_3)
        imageslist.add(R.drawable.donate_blood)
        imageslist.add(R.drawable.blood_donor_day)

        binding.viewPager2.adapter= HomeViewPageAdapter(imageslist)

        binding.viewPager2.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        binding.circleIndicator3.setViewPager(binding.viewPager2)
    }
    override fun onBackPressed() {
        // Close the app when the back button is pressed
        moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }
}