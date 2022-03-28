package com.github.ddenaga.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Switch to the MainActivity if there's a user logged in
        if (ParseUser.getCurrentUser() != null) {
            // goToMainActivity()
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            loginUser(username, password)
        }

        val btnSignup = findViewById<Button>(R.id.btnSignup)
        btnSignup.setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            signupUser(username, password)
        }
    }

    private fun signupUser(username: String, password: String) {
        // Create a new ParseUser
        val user = ParseUser()

        // Set the fields for the user
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Sign up succeeded.
                Log.i(TAG, "Signup was successful.")
                Toast.makeText(this, "Signup was a success!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            }
            else {
                // Sign up failed.
                Log.e(TAG, "Signup failed: $e")
                Toast.makeText(this, "Signup failed.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({user, e ->
            if (user != null) {
                // Successful login.
                Log.i(TAG, "Login was successful.")
                Toast.makeText(this, "Login was a success!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            }
            else {
                // Failed login.
                Log.e(TAG, "Login failed: $e")
                Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }))
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}