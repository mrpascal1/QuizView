package com.shahid.quizview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shahid.quizview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initQuiz()
    }

    private fun initQuiz() {
        binding.quizView.initView(dummy())
        binding.quizView.setOnOptionClickListener(object : SubmitClickListener {
            override fun onSubmitClicked(isLast: Boolean, quizModel: ArrayList<QuizModel>) {
                if (isLast) {
                    // Process the data and find the correct answer count
                    // Put custom logic here to open new activity or do something else
                    Toast.makeText(this@MainActivity, "Quiz completed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun dummy(): ArrayList<QuizModel> {
        val quizModel = ArrayList<QuizModel>()
        val answerSet1 = ArrayList<QuizOption>()
        val answerSet2 = ArrayList<QuizOption>()
        val answerSet3 = ArrayList<QuizOption>()

        answerSet1.add(QuizOption(1, "1850s"))
        answerSet1.add(QuizOption(2, "1880s", isRightAnswer = true))
        answerSet1.add(QuizOption(3, "1930s"))
        answerSet1.add(QuizOption(4, "1950s"))

        answerSet2.add(QuizOption(1, "Windows"))
        answerSet2.add(QuizOption(2, "Nokia"))
        answerSet2.add(QuizOption(3, "iOS"))
        answerSet2.add(QuizOption(4, "Android", isRightAnswer = true))

        answerSet3.add(QuizOption(1, "Order of Significance"))
        answerSet3.add(QuizOption(2, "Open Software"))
        answerSet3.add(QuizOption(3, "Operating System", isRightAnswer = true))
        answerSet3.add(QuizOption(4, "Optical Sensor"))

        quizModel.add(
            QuizModel(
                id = 1,
                questionTitle = "In which decade was the American Institute of Electrical Engineers (AIEE) founded?",
                questionImageLink = "",
                answers = answerSet1,
                quizType = QuizType.SINGLE_TEXT
            )
        )
        quizModel.add(
            QuizModel(
                id = 2,
                questionTitle = "Which operating system has the above mascot?",
                questionImageLink ="https://wallpaperaccess.com/full/1650659.jpg",
                answers = answerSet2,
                quizType = QuizType.SINGLE_IMAGE
            )
        )
        quizModel.add(
            QuizModel(
                id = 3,
                questionTitle = "'OS' computer abbreviation usually means ?",
                answers = answerSet3,
                quizType = QuizType.SINGLE_TEXT
            )
        )

        return quizModel
    }
}