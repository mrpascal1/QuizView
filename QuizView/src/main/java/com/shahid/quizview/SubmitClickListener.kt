package com.shahid.quizview

interface SubmitClickListener {
    fun onSubmitClicked(isLast: Boolean, quizModel: ArrayList<QuizModel>)
}