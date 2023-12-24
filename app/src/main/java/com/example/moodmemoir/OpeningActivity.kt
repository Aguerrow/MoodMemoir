package com.example.moodmemoir

import com.example.moodmemoir.OpeningActivity
import android.content.Intent
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import java.util.Calendar

class OpeningActivity : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val emotionsRef = database.getReference("emotions")
    private val notesRef = database.getReference("notes")
    private val auth = FirebaseAuth.getInstance()

    private var selectedEmotion: String = ""

    // Function to save emotions to Firebase
    private fun saveEmotionToFirebase(emotionKey: String) {
        selectedEmotion = emotionKey
        if (selectedEmotion.isNotEmpty()) {
            val currentUser: FirebaseUser? = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid

                emotionsRef.child(userId).child(emotionKey).setValue(true)
                    .addOnSuccessListener {
                        // Emotion saved successfully
                    }
                    .addOnFailureListener {
                        // Emotion save failed
                    }
            }
        } else {
            // Log or print a message indicating that no emotion is selected
            Log.d("EmotionSelection", "No emotion selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opening_main)

        val calendarIntent = Intent(this, CalendarActivity::class.java)
        val logButton: Button = findViewById(R.id.btnLog)
        val imgAnxx: ImageButton = findViewById(R.id.imgAnxx)
        val imgSadd: ImageButton = findViewById(R.id.imgSadd)
        val imgHapp: ImageButton = findViewById(R.id.imgHapp)
        val imgAngg: ImageButton = findViewById(R.id.imgAngg)
        val noteEditText: EditText = findViewById(R.id.edtNote)

        logButton.setOnClickListener {
            val noteText = noteEditText.text.toString()
            if (isLoggingAllowed()) {
                saveDataToFirebase(noteText)
                saveEmotionsToFirebase()
                startActivity(calendarIntent)
            } else {
                Toast.makeText(this, "Logging is only allowed for the current day", Toast.LENGTH_SHORT).show()
            }
        }

        imgAnxx.setOnClickListener { saveEmotionToFirebase("anxiety") }
        imgSadd.setOnClickListener { saveEmotionToFirebase("sadness") }
        imgHapp.setOnClickListener { saveEmotionToFirebase("happiness") }
        imgAngg.setOnClickListener { saveEmotionToFirebase("anger") }

        setButtonStateList(imgAnxx, R.drawable.anxious, R.drawable.anxious_drip)
        setButtonStateList(imgSadd, R.drawable.sad, R.drawable.sad_drip)
        setButtonStateList(imgHapp, R.drawable.happy, R.drawable.happy_drip)
        setButtonStateList(imgAngg, R.drawable.angry, R.drawable.angry_drip)
    }

    private fun saveDataToFirebase(noteText: String) {
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val timestamp = ServerValue.TIMESTAMP

            val noteData = HashMap<String, Any>()
            noteData["timestamp"] = timestamp
            noteData["note"] = noteText

            notesRef.child(userId).push().setValue(noteData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Logged successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "There's something wrong", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEmotionsToFirebase() {
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            // Set emotions to true if selected
            emotionsRef.child(userId).child("happiness").setValue(true)
            emotionsRef.child(userId).child("sadness").setValue(true)
            emotionsRef.child(userId).child("anxiety").setValue(true)
            emotionsRef.child(userId).child("anger").setValue(true)
                .addOnSuccessListener {
                    // Emotions saved successfully
                }
                .addOnFailureListener {
                    // Emotions save failed
                }
        }
    }

    private fun isLoggingAllowed(): Boolean {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Assuming that the DatePicker is used for logging
        // You can replace this with the actual DatePicker used in your layout
        // and extract the selected day accordingly
        // For simplicity, let's assume selectedDay is the selected day from DatePicker
        // Replace this with your actual implementation
        val selectedDay = currentDay

        return currentDay == selectedDay
    }

    private fun setButtonStateList(imageButton: ImageButton, normal: Int, pressed: Int) {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), resources.getDrawable(pressed))
        stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), resources.getDrawable(pressed))
        stateListDrawable.addState(intArrayOf(), resources.getDrawable(normal))

        imageButton.setImageDrawable(stateListDrawable)
    }
}
