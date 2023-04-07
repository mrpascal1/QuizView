package com.shahid.quizview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.shahid.quizview.databinding.QuizViewBinding

class QuizView : LinearLayout {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    private var binding: QuizViewBinding = QuizViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var pointer = 0

    @DrawableRes
    private var enabledSubmitDrawable = R.drawable.bg_button_submit_active
    @DrawableRes
    private var disabledSubmitDrawable = R.drawable.bg_button_disabled

    @DrawableRes
    private var enabledNextDrawable = R.drawable.bg_button_submit_active
    @DrawableRes
    private var disabledNextDrawable = R.drawable.bg_button_disabled

    @DrawableRes
    private var enabledPreviousDrawable = R.drawable.bg_button_submit_active
    @DrawableRes
    private var disabledPreviousDrawable = R.drawable.bg_button_disabled

    @ColorRes
    private var enabledSubmitTextColor = R.color.md_grey_700
    @ColorRes
    private var disabledSubmitTextColor = R.color.md_grey_700

    @ColorRes
    private var enabledNextTextColor = R.color.md_grey_700
    @ColorRes
    private var disabledNextTextColor = R.color.md_grey_700

    @ColorRes
    private var enabledPreviousTextColor = R.color.md_grey_700
    @ColorRes
    private var disabledPreviousTextColor = R.color.md_grey_700

    private var submitTitle = "Submit"
    private var nextTitle = "Next"
    private var previousTitle = "Previous"

    private var quizModel: ArrayList<QuizModel> = ArrayList()

    private var submitClickListener: SubmitClickListener? = null

    private fun initView() {
        setSubmitTitle()
        setNextTitle()
        setPreviousTitle()
    }
    fun setSubmitButtonTitle(title: String) {
        submitTitle = title
        setSubmitTitle()
    }

    fun setNextTitle(title: String) {
        nextTitle = title
        setNextTitle()
    }

    fun setPreviousTitle(title: String) {
        previousTitle = title
        setPreviousTitle()
    }

    private fun setSubmitTitle() {
        binding.submitTv.text = submitTitle
    }
    private fun setNextTitle() {
        binding.nextButtonTv.text = nextTitle
    }
    private fun setPreviousTitle() {
        binding.prevButtonTv.text = previousTitle
    }

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
            setDisabledPreviousButton()
        }
    }

    private fun handleClicks() {
        binding.submitBtn.setOnClickListener {
            binding.submitBtn.isEnabled = false
            setDisabledSubmitButton()
            if (this.quizModel.size - 1 > pointer) {
                binding.nextBtn.isEnabled = true
                setEnabledNextButton()
                submitClickListener?.onSubmitClicked(false, quizModel)
            } else {
                binding.nextBtn.isEnabled = false
                setDisabledNextButton()
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
                setEnabledSubmitButton()
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
            setDisabledNextButton()
            setDisabledSubmitButton()
            if (currentPointer > 0) {
                binding.prevBtn.isEnabled = true
                setEnabledPreviousButton()
            } else {
                binding.prevBtn.isEnabled = true
                setDisabledPreviousButton()
            }
        } else {
            binding.nextBtn.isEnabled = false
            setDisabledNextButton()
        }
    }

    private fun setDisabledSubmitButton() {
        binding.submitBtn.isEnabled = false
        binding.submitBtn.setBackgroundResource(disabledSubmitDrawable)
        binding.submitTv.setTextColor(ContextCompat.getColor(context, disabledSubmitTextColor))
    }

    private fun setEnabledSubmitButton() {
        binding.submitBtn.isEnabled = true
        binding.submitBtn.setBackgroundResource(enabledSubmitDrawable)
        binding.submitTv.setTextColor(ContextCompat.getColor(context, enabledSubmitTextColor))
    }

    private fun setEnabledNextButton() {
        binding.nextBtn.setBackgroundResource(enabledNextDrawable)
        binding.nextButtonTv.setTextColor(ContextCompat.getColor(context, enabledNextTextColor))
    }
    private fun setDisabledNextButton() {
        binding.nextBtn.setBackgroundResource(disabledNextDrawable)
        binding.nextButtonTv.setTextColor(ContextCompat.getColor(context, disabledNextTextColor))
    }

    private fun setEnabledPreviousButton() {
        binding.prevBtn.setBackgroundResource(enabledNextDrawable)
        binding.prevButtonTv.setTextColor(ContextCompat.getColor(context, enabledPreviousTextColor))
    }
    private fun setDisabledPreviousButton() {
        binding.prevBtn.setBackgroundResource(disabledPreviousDrawable)
        binding.prevButtonTv.setTextColor(ContextCompat.getColor(context, disabledPreviousTextColor))
    }

    fun setSubmitButtonDrawables(@DrawableRes enabledDrawable: Int, @DrawableRes disabledDrawable: Int) {
        this.enabledSubmitDrawable = enabledDrawable
        this.disabledSubmitDrawable = disabledDrawable
    }
    fun setNextButtonDrawable(@DrawableRes enabledDrawable: Int, @DrawableRes disabledDrawable: Int) {
        this.enabledNextDrawable = enabledDrawable
        this.disabledNextDrawable = disabledDrawable
    }
    fun setPreviousButtonDrawable(@DrawableRes enabledDrawable: Int, @DrawableRes disabledDrawable: Int) {
        this.enabledPreviousDrawable = enabledDrawable
        this.disabledPreviousDrawable = disabledDrawable
    }

    fun setSubmitButtonTextColor(@ColorRes enabledTextColor: Int, @ColorRes disabledTextColor: Int) {
        this.enabledSubmitTextColor = enabledTextColor
        this.disabledSubmitTextColor = disabledTextColor
    }

    fun setNextButtonTextColor(@ColorRes enabledTextColor: Int, @ColorRes disabledTextColor: Int) {
        this.enabledNextTextColor = enabledTextColor
        this.disabledNextTextColor = disabledTextColor
    }

    fun setPreviousButtonTextColor(@ColorRes enabledTextColor: Int, @ColorRes disabledTextColor: Int) {
        this.enabledPreviousTextColor = enabledTextColor
        this.disabledPreviousTextColor = disabledTextColor
    }



    fun disablePreviousButton(isDisable: Boolean) {
        binding.prevBtn.isEnabled = !isDisable
        if (!isDisable) {
            setEnabledPreviousButton()
        } else {
            setDisabledPreviousButton()
        }
    }

    fun disableNextButton(isDisable: Boolean) {
        binding.nextBtn.isEnabled = !isDisable
        if (!isDisable) {
            setEnabledNextButton()
        } else {
            setDisabledNextButton()
        }
    }

    fun disableSubmitButton(isDisable: Boolean) {
        binding.submitBtn.isEnabled = !isDisable
        if (!isDisable) {
            setEnabledSubmitButton()
        } else {
            setDisabledSubmitButton()
        }
    }

    fun setOnOptionClickListener(submitClickListener: SubmitClickListener?) {
        this.submitClickListener = submitClickListener
    }
}