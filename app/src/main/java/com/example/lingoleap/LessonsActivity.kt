package com.example.lingoleap

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingoleap.data.DBHelper

data class Lesson(val title: String, val content: String, val colorHex: String = "#1976D2")

class LessonActivity : AppCompatActivity() {

    private val lessons = listOf(
        // Spanish
        Lesson("Spanish: Basics", "Hola = Hello\nAdios = Goodbye\nPor favor = Please", "#E53935"),
        Lesson("Spanish: Food", "Comida = Food\nAgua = Water\nPan = Bread\nLeche = Milk", "#E53935"),

        // French
        Lesson("French: Greetings", "Bonjour = Hello\nBonsoir = Good evening\nMerci = Thank you", "#1E88E5"),
        Lesson("French: Travel", "Où est...? = Where is...?\nBillet = Ticket\nTrain = Train", "#1E88E5"),

        // German
        Lesson("German: Numbers", "Eins = One\nZwei = Two\nDrei = Three\nVier = Four", "#FDD835"),
        Lesson("German: Family", "Mutter = Mother\nVater = Father\nBruder = Brother", "#FDD835"),

        // Japanese
        Lesson("Japanese: Basics", "Konnichiwa = Hello\nArigato = Thank you\nSumimasen = Excuse me", "#43A047"),
        Lesson("Japanese: Days", "Getsuyoubi = Monday\nNichiyoubi = Sunday\nKyou = Today", "#43A047"),

        // Italian
        Lesson("Italian: Common", "Ciao = Hello\nGrazie = Thank you\nPrego = You're welcome", "#00897B")
    )

    private var currentLessonIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        val tvTitle = findViewById<TextView>(R.id.tvLessonTitle)
        val tvContent = findViewById<TextView>(R.id.tvLessonContent)
        val btnAction = findViewById<Button>(R.id.btnCompleteLesson)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarLesson)
        val tvProgressText = findViewById<TextView>(R.id.tvProgressText)

        val dbHelper = DBHelper.getInstance(this)
        val userEmail = getSharedPreferences("lingoleap_prefs", MODE_PRIVATE)
            .getString("current_user_email", "guest") ?: "guest"

        progressBar.max = lessons.size

        fun updateUI() {
            if (currentLessonIndex < lessons.size) {
                val lesson = lessons[currentLessonIndex]
                tvTitle.text = lesson.title
                tvContent.text = lesson.content

                // Update Progress
                val progress = currentLessonIndex + 1
                progressBar.progress = progress
                tvProgressText.text = "Lesson $progress of ${lessons.size}"
                btnAction.text = "Complete & Continue (+10 XP)"
            } else {
                tvTitle.text = "Congratulations!"
                tvContent.text = "You have completed all available lessons.\nCheck back later for more!"
                btnAction.text = "Finish Course"
                progressBar.progress = lessons.size
            }
        }

        updateUI()

        btnAction.setOnClickListener {
            if (currentLessonIndex < lessons.size) {
                dbHelper.addXp(userEmail, 10)
                Toast.makeText(this, "+10 XP Earned!", Toast.LENGTH_SHORT).show()
                currentLessonIndex++
                updateUI()
            } else {
                finish()
            }
        }
    }
}

