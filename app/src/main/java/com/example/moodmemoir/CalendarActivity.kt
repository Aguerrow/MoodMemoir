package com.example.moodmemoir

import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_main)

        // Use findViewById to get a reference to the DatePicker
        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        // Get the current date for initialization
        val currentDate = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentDate
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // You can now use the datePicker as needed
        // For example, you can set an OnDateChangedListener to handle date changes
        datePicker.init(year, month, day) { _, year, monthOfYear, dayOfMonth ->
            // Handle date change here
        }
    }
}