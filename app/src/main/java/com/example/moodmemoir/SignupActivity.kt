package com.example.moodmemoir

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_main)

        auth = FirebaseAuth.getInstance()

        val signUpButton = findViewById<Button>(R.id.btnSignUp2)
        signUpButton.setOnClickListener {
            val emailEditText = findViewById<EditText>(R.id.edtSignupName)
            val passwordEditText = findViewById<EditText>(R.id.edtSignupPass)
            val confirmPasswordEditText = findViewById<EditText>(R.id.edtSignupConfPass)

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Implement your own validation logic
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Please fill in all fields")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }

            // Firebase authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign-up success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        showToast("Sign-up successful!")

                        // Call a method to write additional user data to Firebase
                        writeToFirebase(user?.uid ?: "")

                        // Start MainActivity instead of OpeningActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign-up fails, display a message to the user.
                        showToast("Sign-up failed. ${task.exception?.message}")
                    }
                }

        }
    }

    private fun writeToFirebase(userId: String) {
        // You can modify this method to write additional user data to your database
        // For example, you can store the user's name, additional settings, etc.
        // This is just a basic example, and you might want to design your database schema accordingly.
        val databaseReference = FirebaseDatabase.getInstance().reference
        val userData = mapOf("email" to auth.currentUser?.email)
        databaseReference.child("users").child(userId).setValue(userData)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
