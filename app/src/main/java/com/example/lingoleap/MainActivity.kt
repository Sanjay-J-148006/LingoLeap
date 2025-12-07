package com.example.lingoleap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLessons = findViewById<Button>(R.id.btnLessons)
        val btnQuiz = findViewById<Button>(R.id.btnQuiz)
        val btnProfile = findViewById<Button>(R.id.btnProfile)

        btnLessons.setOnClickListener {
            startActivity(Intent(this, LessonActivity::class.java))
        }

        btnQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
