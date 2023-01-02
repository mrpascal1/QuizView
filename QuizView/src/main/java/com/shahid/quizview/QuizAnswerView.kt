package com.shahid.quizview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import com.shahid.quizview.databinding.QuizAnswerViewBinding

class QuizAnswerView : LinearLayout {
    constructor(context: Context?) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    private var optionClickListener: IOptionClickListener? = null

    private var binding: QuizAnswerViewBinding = QuizAnswerViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun initView(quizModel: QuizModel?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val questionId = quizModel?.id
        if (!quizModel?.answers.isNullOrEmpty()) {
            for (i in 0 until quizModel?.answers?.size!!) {
                if (quizModel.answers?.get(i)?.option != null) {
                    val id = quizModel.answers?.get(i)?.id
                    val option = quizModel.answers?.get(i)?.option
                    val isSelected = quizModel.answers?.get(i)?.isSelected
                    val view: View = inflater.inflate(R.layout.layout_radio_text, null)
                    val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    layoutParams.setMargins(0, 10, 0, 0)
                    view.layoutParams = layoutParams
                    val singleSelectableTv = view.findViewById<TextView>(R.id.singleSelectableTv)
                    singleSelectableTv.text = option
                    if (id != null) {
                        singleSelectableTv.id = id
                    }
                    if (isSelected == true) {
                        singleSelectableTv.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_selected)
                    }
                    singleSelectableTv.setOnClickListener {
                        optionClickListener?.onOptionClicked(questionId, id)
                        quizModel.answers?.forEach { options ->
                            if (options.id == id) {
                                options.isSelected = true
                                singleSelectableTv.background = ContextCompat.getDrawable(context, R.drawable.bg_answer_selected)
                            } else {
                                binding.mainLayout.allViews.forEach {
                                    if (it.tag != "mainLayout" && it.id != id) {
                                        options.isSelected = false
                                        it.background = ContextCompat.getDrawable(context, R.drawable.bg_quiz_answer)
                                    }
                                }
                            }
                        }
                    }
                    binding.mainLayout.addView(singleSelectableTv)
                }
            }
        }
    }

    fun setOnOptionClickListener(optionClickListener: IOptionClickListener?) {
        this.optionClickListener = optionClickListener
    }

    fun clearAllData() {
        binding.mainLayout.removeAllViews()
    }
}