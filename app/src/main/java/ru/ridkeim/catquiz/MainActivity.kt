package ru.ridkeim.catquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    companion object{
        const val KEY_INDEX = "INDEX"
    }
    private lateinit var questionTextView: TextView
    private val questions = arrayOf(
        Question(R.string.question_1, false),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, false),
        Question(R.string.question_5, true)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        }
        questionTextView = findViewById(R.id.question)
        displayCurrentQuestion()
        findViewById<Button>(R.id.buttonNext).apply {
            setOnClickListener {
                currentIndex = (currentIndex + 1) % questions.size
                displayCurrentQuestion()
            }
        }
        findViewById<Button>(R.id.buttonTrue).setOnClickListener {
            checkAnswerAndShowToast(true)
        }
        findViewById<Button>(R.id.buttonFalse).setOnClickListener {
            checkAnswerAndShowToast(false)
        }
        findViewById<Button>(R.id.buttonPeep).setOnClickListener {
            startActivity(
                AnswersActivity.prepareIntent(
                    this,
                    getCurrentQuestion().isRightAnswer,
                    currentIndex
                )
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX,currentIndex)
    }

    private fun checkAnswerAndShowToast(answer: Boolean) {
        val toastMessage = if(getCurrentQuestion().checkAnswer(answer)){
            R.string.correct
        }else{
            R.string.not_correct
        }
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentQuestion() : Question{
        return questions[currentIndex]
    }

    private fun displayCurrentQuestion() {
        questionTextView.setText(getCurrentQuestion().textResId)
    }



}