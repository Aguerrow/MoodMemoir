package com.example.moodmemoir

import android.content.Intent
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class OpeningActivity : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val emotionsRef = database.getReference("emotions")
    private val notesRef = database.getReference("notes")
    private val auth = FirebaseAuth.getInstance()

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
            saveDataToFirebase(noteEditText.text.toString())
            startActivity(calendarIntent)
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

    private fun saveEmotionToFirebase(emotionKey: String) {
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
    }

    private fun setButtonStateList(imageButton: ImageButton, normal: Int, pressed: Int) {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), resources.getDrawable(pressed))
        stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), resources.getDrawable(pressed))
        stateListDrawable.addState(intArrayOf(), resources.getDrawable(normal))

        imageButton.setImageDrawable(stateListDrawable)
    }
}
