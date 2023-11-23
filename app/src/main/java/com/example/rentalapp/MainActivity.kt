package com.example.rentalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val intent = Intent(this,CreateAccount::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)

    }
}