package com.example.assignment.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.example.assignment.utils.SPLASH_TIME

/**
 * This class is used to show application logo
 */
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}