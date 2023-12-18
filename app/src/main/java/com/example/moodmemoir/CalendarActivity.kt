// CalendarActivity.kt

package com.example.moodmemoir

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private val noteCalendarManager = NoteCalendarManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_main)

        calendarView = findViewById(R.id.calendarView)
        val txtCalendarNote: TextView = findViewById(R.id.txtCalendarNote)

        // Set listener for calendar date change
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            // Retrieve and display the note for the selected date
            val note = intent.getStringExtra("NOTE")
            note?.let {
                noteCalendarManager.addNote(selectedDate, it)
                txtCalendarNote.text = "Note for ${selectedDate.get(Calendar.DAY_OF_MONTH)}/" +
                        "${selectedDate.get(Calendar.MONTH) + 1}/" +
                        "${selectedDate.get(Calendar.YEAR)}: $it"
            }
        }
    }
}
