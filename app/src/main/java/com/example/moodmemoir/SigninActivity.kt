package com.example.moodmemoir

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_main)

        val signInButton: Button = findViewById(R.id.btnSignIn2)
        signInButton.setOnClickListener {
            // Start OpeningActivity
            val intent = Intent(this, OpeningActivity::class.java)
            startActivity(intent)
        }
    }
}
