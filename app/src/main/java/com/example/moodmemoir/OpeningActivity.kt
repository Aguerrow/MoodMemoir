package com.example.moodmemoir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OpeningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opening_main)

        // Define an Intent for CalendarActivity
        val calendarIntent = Intent(this, CalendarActivity::class.java)

        // Find the "Log" button by its ID
        val logButton: Button = findViewById(R.id.btnLog)

        // Set a click listener for the button
        logButton.setOnClickListener {
            // Start CalendarActivity when the button is clicked
            startActivity(calendarIntent)
        }
    }
}
