package me.bamsarts.footballschedule.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.bamsarts.footballschedule.MainActivity

class SplashScreenActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}