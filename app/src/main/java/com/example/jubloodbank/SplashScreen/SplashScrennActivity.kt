package com.example.jubloodbank.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ProgressBar
import com.example.jubloodbank.HomePage.HomePageActivity
import com.example.jubloodbank.MainActivity
import com.example.jubloodbank.R

class SplashScrennActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_DURATION = 1000L // Changed to a Long value
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screnn)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        progressBar = findViewById(R.id.progress_bar)
        progressBar.progress = 0
        // Start a timer to display the splash screen for 3 seconds.
        Handler().postDelayed({
            // Navigate to the main activity.
            startActivity(Intent(this, MainActivity::class.java))

            // Finish the splash screen activity.
            finish()
        }, 3000)
    }
}