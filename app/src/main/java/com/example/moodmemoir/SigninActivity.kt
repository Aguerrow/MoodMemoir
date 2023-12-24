package com.example.moodmemoir


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_main)

        auth = FirebaseAuth.getInstance()

        val signInButton: Button = findViewById(R.id.btnSignIn2)
        signInButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.edtSigninName).text.toString().trim()
            val password = findViewById<EditText>(R.id.edtSigninPass).text.toString().trim()

            // Implement your own validation logic
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill in all fields")
                return@setOnClickListener
            }

            // Firebase authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign-in success, update UI with the signed-in user's information
                        showToast("Sign-in successful!")

                        // Start OpeningActivity instead of MainActivity
                        val intent = Intent(this, OpeningActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign-in fails, display a message to the user.
                        showToast("Sign-in failed. ${task.exception?.message}")
                    }
                }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
