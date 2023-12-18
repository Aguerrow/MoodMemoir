// OpeningActivity.kt

package com.example.moodmemoir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class OpeningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opening_main)

        // Find the UI elements by their IDs
        val logButton: Button = findViewById(R.id.btnLog)
        val edtNote: EditText = findViewById(R.id.edtNote)

        // Set a click listener for the "Log" button
        logButton.setOnClickListener {
            // Get the note from the EditText
            val note = edtNote.text.toString()

            // Create an Intent to start CalendarActivity
            val calendarIntent = Intent(this, CalendarActivity::class.java)

            // Pass the note as an extra to the Intent
            calendarIntent.putExtra("NOTE", note)

            // Start CalendarActivity
            startActivity(calendarIntent)
        }
    }
}
