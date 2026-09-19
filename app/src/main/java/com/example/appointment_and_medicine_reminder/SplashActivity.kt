package com.example.appointment_and_medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity(), Animation.AnimationListener {

    var imglogo: ImageView? = null
    var splashAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

        imglogo = findViewById(R.id.imglogo)

        splashAnimation = AnimationUtils.loadAnimation(this, R.anim.twinanimation)
        splashAnimation?.setAnimationListener(this)

        imglogo?.startAnimation(splashAnimation)
    }

    override fun onAnimationStart(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onAnimationRepeat(animation: Animation?) {}
}
