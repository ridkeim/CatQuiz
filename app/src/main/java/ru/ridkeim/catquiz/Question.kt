package ru.ridkeim.catquiz

data class Question(val textResId : Int, val isRightAnswer : Boolean){
    fun checkAnswer(answer: Boolean) : Boolean{
        return answer == isRightAnswer
    }
}