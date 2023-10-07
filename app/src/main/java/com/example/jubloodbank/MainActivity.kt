package com.example.jubloodbank

import android.content.ClipDescription
import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.jubloodbank.HomePage.HomePageActivity
import com.example.jubloodbank.bloodlist.BloodListActivity
import com.example.jubloodbank.databinding.ActivityMainBinding
import com.example.jubloodbank.login.LoginActivity
import com.example.jubloodbank.registration.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    val auth = FirebaseAuth.getInstance()
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    private var titlelist= mutableListOf<String>()
    private var deslist= mutableListOf<String>()
    private var imageslist= mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            if (user != null) {
                startActivity(Intent(this, HomePageActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
        auth.addAuthStateListener(authListener)
        postToList()

      binding.viewPager2.adapter=ViewPagerAdapter(titlelist,deslist,imageslist)

        binding.viewPager2.orientation=ViewPager2.ORIENTATION_HORIZONTAL

//        binding.circleIndicator3.setViewPager(binding.viewPager2)


    }

    private fun addToList(title: String,description: String,image:Int){
        titlelist.add(title)
        deslist.add(description)
        imageslist.add(image)
    }
    private fun postToList(){
        for(i in 1..5){
            addToList("Title $i","Description $i",R.mipmap.ic_launcher_round)
    }
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }
}