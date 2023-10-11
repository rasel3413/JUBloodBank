package com.example.jubloodbank.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jubloodbank.HomePage.HomePageActivity
import com.example.jubloodbank.MainActivity
import com.example.jubloodbank.R
import com.example.jubloodbank.databinding.ActivityLoginBinding
import com.example.jubloodbank.registration.RegistrationActivity
import com.google.firebase.messaging.FirebaseMessaging
import login
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().subscribeToTopic("All")

        binding.btnLogSubmit.setOnClickListener {

            if (binding.edLogEmail.text?.isEmpty() == true) {
                binding.edLogEmail.error = "Please Provide Email"

            } else if (binding.edLogPassword.text?.isEmpty() == true) {
                binding.edLogEmail.error = "Please Provide Password"
            } else {
                login(
                    binding.edLogEmail.text.toString(),
                    binding.edLogPassword.text.toString(),
                    this@LoginActivity
                ) { success ->
                    if (success) {
                        startActivity(Intent(this, HomePageActivity::class.java))
                    }

                }

//
            }

        }
        binding.btnCreat.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

    }

    override fun onBackPressed() {
        // Close the app when the back button is pressed
        moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }
}