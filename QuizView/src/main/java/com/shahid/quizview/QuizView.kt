package com.shahid.quizview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.shahid.quizview.databinding.QuizViewBinding

class QuizView : LinearLayout {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    private var binding: QuizViewBinding = QuizViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var pointer = 0

    private var quizModel: ArrayList<QuizModel> = ArrayList()

    private var submitClickListener: SubmitClickListener? = null

    fun initView(quizModel: ArrayList<QuizModel>?) {
        initializeGame(quizModel)
    }

    private fun initializeGame(quizModel: ArrayList<QuizModel>?) {
        if (quizModel != null) {
            this.quizModel = quizModel
            startOrNextGame(pointer)
        }
        isFirstQuestion()
        handleClicks()
    }

    private fun isFirstQuestion() {
        if (pointer == 0) {
            binding.prevBtn.isEnabled = false
        }
    }

    private fun handleClicks() {
        binding.submitBtn.setOnClickListener {
            binding.submitBtn.isEnabled = false
            if (this.quizModel.size - 1 > pointer) {
                binding.nextBtn.isEnabled = true
                submitClickListener?.onSubmitClicked(false, quizModel)
            } else {
                submitClickListener?.onSubmitClicked(true, quizModel)
            }
        }

        binding.nextBtn.setOnClickListener {
            if (this.quizModel.size > pointer) {
                pointer += 1
                startOrNextGame(pointer)
            }
        }

        binding.prevBtn.setOnClickListener {
            if (pointer != 0) {
                pointer -= 1
                startOrNextGame(pointer)
            }
        }

        binding.quizAnswer.setOnOptionClickListener(object : IOptionClickListener {
            override fun onOptionClicked(questionId: Int?, selectedOptionId: Int?) {
                binding.submitBtn.isEnabled = true
            }
        })
    }

    private fun startOrNextGame(currentPointer: Int) {
        if (quizModel.isNotEmpty() && quizModel.size > currentPointer) {
            binding.questionView.initView(quizModel[currentPointer])
            binding.quizAnswer.clearAllData()
            binding.quizAnswer.initView(quizModel[currentPointer])
            binding.submitBtn.isEnabled = true
            binding.nextBtn.isEnabled = false
            binding.submitBtn.isEnabled = false
            binding.prevBtn.isEnabled = currentPointer > 0
        } else {
            binding.nextBtn.isEnabled = false
        }
    }

    fun disablePreviousButton(isDisable: Boolean) {
        binding.prevBtn.isEnabled = !isDisable
    }

    fun disableNextButton(isDisable: Boolean) {
        binding.nextBtn.isEnabled = !isDisable
    }

    fun disableSubmitButton(isDisable: Boolean) {
        binding.submitBtn.isEnabled = !isDisable
    }

    fun setOnOptionClickListener(submitClickListener: SubmitClickListener?) {
        this.submitClickListener = submitClickListener
    }
}