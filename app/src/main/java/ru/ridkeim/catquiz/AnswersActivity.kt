package ru.ridkeim.catquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class AnswersActivity : AppCompatActivity() {
    private var answerIsTrue : Boolean = false
    private var answerIsShown : Boolean = false
    private var index : Int = 0
    companion object{
        private const val EXTRA_ANSWER_IS_TRUE = "ru.ridkeim.catquiz.true_answer"
        private const val EXTRA_ANSWER_INDEX = "ru.ridkeim.catquiz.index_answer"
        private const val KEY_ANSWER_SHOWN = "answer_shown"
        fun prepareIntent(packageContext : Context, answerIsTrue : Boolean, index : Int): Intent {
            return Intent(packageContext,AnswersActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_ANSWER_INDEX, index)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        if(savedInstanceState != null){
            answerIsShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN,false)
        }
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)
        index = intent.getIntExtra(EXTRA_ANSWER_INDEX, 0)
        val textViewAnswer = findViewById<TextView>(R.id.textViewAnswer).apply {
            text = getString(R.string.answer,
                if(answerIsTrue){
                    getString(R.string.yes)
                }else{
                    getString(R.string.no)
                }
            )
            append("\n${resources.getStringArray(R.array.answers_string)[index]}")
        }
        val textViewConfirm = findViewById<TextView>(R.id.textViewConfirm)
        val showAnswerButton = findViewById<Button>(R.id.buttonShowAnswer).apply {
            setOnClickListener {
                textViewConfirm.visibility = View.GONE
                textViewAnswer.visibility = View.VISIBLE
                it.isEnabled = false
                answerIsShown = true
            }
        }

        if(answerIsShown){
            textViewAnswer.visibility = View.VISIBLE
            textViewConfirm.visibility = View.GONE
        }else{
            textViewAnswer.visibility = View.GONE
            textViewConfirm.visibility = View.VISIBLE
        }
        showAnswerButton.isEnabled = !answerIsShown
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN,answerIsShown)
    }
}