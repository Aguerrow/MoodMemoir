package com.example.moodmemoir

import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarView: calendarView
    private val noteCalendarManager = NoteCalendarManager() // Reuse your existing note manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView) // Get reference to CalendarView

        // Set listener for calendar date change
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            val notes = noteCalendarManager.getNotesForDate(selectedDate) // Get notes for selected date

            // Update the UI with retrieved notes (e.g., show them in a list, toast message, etc.)

            // Check for empty notes and display a message if needed
            if (notes.isEmpty()) {
                Toast.makeText(this, "No notes found for this date.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
