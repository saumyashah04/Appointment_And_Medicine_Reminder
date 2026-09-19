package com.example.appointment_and_medicine_reminder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Redirect to SplashActivity
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}