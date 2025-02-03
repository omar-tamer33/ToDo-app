package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({changeActivity()},3000)
    }

    private fun changeActivity() {
        val intent : Intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}