package com.example.lingoleap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lingoleap.data.DBHelper

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvLevelBadge: TextView
    private lateinit var tvTotalXp: TextView
    private lateinit var tvLessons: TextView
    private lateinit var dbHelper: DBHelper
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 1. Initialize Views with correct IDs from the XML
        tvUsername = findViewById(R.id.tvUsername)
        tvLevelBadge = findViewById(R.id.tvUserLevelBadge)
        tvTotalXp = findViewById(R.id.tvTotalXp)
        tvLessons = findViewById(R.id.tvLessonsCompleted)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // 2. Initialize Database and Prefs
        dbHelper = DBHelper.getInstance(this)
        val prefs = getSharedPreferences("lingoleap_prefs", MODE_PRIVATE)
        userEmail = prefs.getString("current_user_email", "Guest") ?: "Guest"

        // 3. Set Initial Name
        tvUsername.text = userEmail

        // 4. Setup Logout Button
        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data every time the page appears
        loadUserData()
    }

    private fun loadUserData() {
        try {
            // Fetch raw XP from DB
            val totalXp = dbHelper.getUserXp(userEmail)

            // Calculate Stats (Example: 10 XP per lesson)
            val lessonsCompleted = totalXp / 10
            val currentLevel = (totalXp / 100) + 1

            // Update UI safely
            tvTotalXp.text = totalXp.toString()
            tvLessons.text = lessonsCompleted.toString()

            val levelName = when {
                currentLevel < 2 -> "Beginner"
                currentLevel < 5 -> "Intermediate"
                currentLevel < 10 -> "Advanced"
                else -> "Master"
            }
            tvLevelBadge.text = "Lvl $currentLevel • $levelName"

        } catch (e: Exception) {
            e.printStackTrace() // Log error but don't crash
        }
    }
}

