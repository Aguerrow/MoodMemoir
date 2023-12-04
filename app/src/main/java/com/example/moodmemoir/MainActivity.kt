package com.example.moodmemoir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define Intents
        val signinIntent = Intent(this, SigninActivity::class.java)
        val signUpIntent = Intent(this, SignupActivity::class.java)

        // Button Clicks
        val signinButton: Button = findViewById(R.id.btnSignIn)
        signinButton.setOnClickListener {
            startActivity(signinIntent)
        }

        val signUpButton: Button = findViewById(R.id.btnSignUp)
        signUpButton.setOnClickListener {
            startActivity(signUpIntent)
        }
    }
}
