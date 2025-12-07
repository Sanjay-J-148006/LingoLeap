package com.example.lingoleap

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lingoleap.data.DBHelper

data class Question(val text: String, val options: List<String>, val correctIndex: Int)

class QuizActivity : AppCompatActivity() {

    private val questions = listOf(
        Question("What is 'Hello' in Spanish?", listOf("Adios", "Hola", "Gracias", "Si"), 1),
        Question("What is 'Thank you' in French?", listOf("Bonjour", "Pardon", "Merci", "Oui"), 2),
        Question("Which number is 'Drei' in German?", listOf("One", "Two", "Three", "Four"), 2),
        Question("Translate 'Cat' to Spanish.", listOf("Perro", "Gato", "Pez", "Pajaro"), 1),
        Question("What does 'Arigato' mean?", listOf("Hello", "Goodbye", "Sorry", "Thank you"), 3)
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitQuiz)
        val tvScore = findViewById<TextView>(R.id.tvQuizScore)

        // Get UI references for options (Make sure these IDs exist in XML)
        val rb1 = findViewById<RadioButton>(R.id.rbOption1)
        val rb2 = findViewById<RadioButton>(R.id.rbOption2)
        val rb3 = findViewById<RadioButton>(R.id.rbOption3)
        val rb4 = findViewById<RadioButton>(R.id.rbOption4)
        val optionsList = listOf(rb1, rb2, rb3, rb4)

        val dbHelper = DBHelper.getInstance(this)
        val userEmail = getSharedPreferences("lingoleap_prefs", MODE_PRIVATE)
            .getString("current_user_email", "guest") ?: "guest"

        fun loadQuestion() {
            if (currentQuestionIndex < questions.size) {
                val q = questions[currentQuestionIndex]
                tvQuestion.text = q.text
                radioGroup.clearCheck()

                // Set text for radio buttons
                for (i in 0 until 4) {
                    optionsList[i].text = q.options[i]
                }
                btnSubmit.text = "Submit Answer"
            } else {
                // End of Quiz
                val finalXp = score * 10
                dbHelper.addXp(userEmail, finalXp)

                tvQuestion.text = "Quiz Complete!"
                radioGroup.visibility = android.view.View.GONE
                tvScore.text = "Final Score: $score / ${questions.size}\nXP Earned: $finalXp"

                btnSubmit.text = "Finish"
                btnSubmit.setOnClickListener { finish() }
            }
        }

        loadQuestion()

        btnSubmit.setOnClickListener {
            if (currentQuestionIndex >= questions.size) return@setOnClickListener

            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Find index of selected answer
            val selectedRb = findViewById<RadioButton>(selectedId)
            val selectedIndex = optionsList.indexOf(selectedRb)

            if (selectedIndex == questions[currentQuestionIndex].correctIndex) {
                score++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++
            loadQuestion()
        }
    }
}
