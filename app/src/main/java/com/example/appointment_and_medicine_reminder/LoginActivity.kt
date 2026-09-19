package com.example.appointment_and_medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    var usernameEditText: EditText? = null
    var passwordEditText: EditText? = null
    var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)

        loginButton?.setOnClickListener {
            val username = usernameEditText?.text.toString().trim()
            val password = passwordEditText?.text.toString()

            var isValid = false
            if (username == "Prince" && password == "Prince@123") {
                isValid = true
            } else if (username == "Prince1" && password == "Prince1@123") {
                isValid = true
            } else if (username == "Prince2" && password == "Prince2@123") {
                isValid = true
            }

            if (isValid) {
                // Save current user to SharedPreferences for session and data isolation
                val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("currentUser", username)
                editor.apply()

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}