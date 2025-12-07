package com.example.lingoleap

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingoleap.data.DBHelper

class LoginActivity : AppCompatActivity() {

    private val dbHelper by lazy { DBHelper.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // --- REMOVED AUTO-LOGIN CHECK ---
        // The code that checked sharedPreferences and called navigateToMain()
        // has been removed so you can see the login screen every time.

        val emailInput = findViewById<EditText>(R.id.email)
        val passInput = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginButton)
        val registerBtn = findViewById<Button>(R.id.registerButton)
        val rememberCheck = findViewById<CheckBox>(R.id.rememberCheck)

        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val pass = passInput.text.toString()

            if (validateInput(email, pass)) {
                if (dbHelper.checkUser(email, pass)) {
                    saveSession(email, rememberCheck.isChecked)
                    Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    showError("Invalid Email or Password")
                }
            }
        }

        registerBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val pass = passInput.text.toString()

            if (validateInput(email, pass)) {
                if (dbHelper.registerUser(email, pass)) {
                    Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()
                    saveSession(email, rememberCheck.isChecked)
                    navigateToMain()
                } else {
                    showError("User already exists")
                }
            }
        }
    }

    private fun validateInput(email: String, pass: String): Boolean {
        if (email.isEmpty() || pass.isEmpty()) {
            showError("Please fill all fields")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email")
            return false
        }
        return true
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveSession(email: String, remember: Boolean) {
        val prefs = getSharedPreferences("lingoleap_prefs", MODE_PRIVATE)
        // We still save the email so the Profile page knows who is logged in
        prefs.edit().putString("current_user_email", email).apply()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
