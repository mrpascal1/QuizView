package com.shahid.quizview

data class QuizModel(
    var id: Int? = 0,
    var questionTitle: String? = "",
    var questionImageLink: String? = "",
    var answers: ArrayList<QuizOption>? = null,
    var quizType: QuizType? = null
)

data class QuizOption(
    var id: Int? = 0,
    var option: String? = "",
    var isRightAnswer: Boolean = false,
    var isSelected: Boolean = false
)