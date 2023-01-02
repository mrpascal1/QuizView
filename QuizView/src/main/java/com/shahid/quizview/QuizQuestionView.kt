package com.shahid.quizview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.shahid.quizview.databinding.QuizQuestionViewBinding

class QuizQuestionView : LinearLayout {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    private var binding: QuizQuestionViewBinding = QuizQuestionViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var quizModel: QuizModel? = null

    fun initView(quizModel: QuizModel) {
        this.quizModel = quizModel
        when(quizModel.quizType) {
            QuizType.SINGLE_TEXT -> {
                setTextQuestion()
            }
            QuizType.SINGLE_IMAGE -> {
                setImageQuestion()
            }
            QuizType.MULTIPLE_TEXT -> {
            }
            QuizType.MULTIPLE_IMAGE -> {
            }
            else -> {
            }
        }
    }

    private fun setTextQuestion() {
        binding.textQuestionCard.visibility = VISIBLE
        binding.imageHolder.visibility = GONE
        binding.questionTv.text = quizModel?.questionTitle
    }

    private fun setImageQuestion() {
        binding.textQuestionCard.visibility = GONE
        binding.imageHolder.visibility = VISIBLE
        Glide.with(context).load(quizModel?.questionImageLink).into(binding.questionIv)
        binding.imageQuestionTv.text = quizModel?.questionTitle
    }
}