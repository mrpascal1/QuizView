# QuizView
QuizView reduces developer time to think and cover all the possible cases for creating a quiz module in their android apps

<div align="left">
	<img src="https://i.imgur.com/c3RTZqs.png" width="128">
</div>


## Prerequisites

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

## Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	implementation 'com.github.mrpascal1:QuizView:1.0.0'
}
```

## Configuration

To use `QuizView`, there are 2 important methods you need to know:

```kotlin
quizView.initView() //initView takes ArrayList<QuizModel>? as a parameter

quizView.setOnOptionClickListener(object : SubmitClickListener {
        override fun onSubmitClicked(isLast: Boolean, quizModel: ArrayList<QuizModel>) {
            if (isLast) {
                /* isLast will be true only when the last quiz submit is clicked
                 If you want you can process the data and find the correct answer count
                 Also, you can put custom logic here to open new activity or do something else */
            }
        }
    }
)
```

See full usage [here](https://github.com/mrpascal1/QuizView/blob/master/app/src/main/java/com/shahid/quizviewtest/MainActivity.kt)

## Data model and processing.

WARNING - You should always consider mapping data from your API models to this library models instead of directly using this models, 
because this might get change in future builds.

There are two in `QuizView` - `QuizModel` and `QuizOption`, see [here](https://github.com/mrpascal1/QuizView/blob/master/QuizView/src/main/java/com/shahid/quizview/QuizModel.kt)

`QuizModel` -

```kotlin
data class QuizModel(
    var id: Int? = 0, // You should always pass a unique id to your quiz.
    var questionTitle: String? = "", // Title of the question
    var questionImageLink: String? = "", // If the Image link is Empty it won't be shown
    var answers: ArrayList<QuizOption>? = null, // Here you will supply a list of data answers to this particular quiz question.
    var quizType: QuizType? = null // QuizType is an enum which you will bind according to your quiz type per question.
)
```

Note - Right know only `SINGLE_TEXT` and `SINGLE_IMAGE` QuizType is working. Check all the types [here](https://github.com/mrpascal1/QuizView/blob/master/QuizView/src/main/java/com/shahid/quizview/QuizType.kt)

`QuizOption` -

```kotlin
data class QuizOption(
    var id: Int? = 0, // A unique id for every option
    var option: String? = "", // Option title text
    var isRightAnswer: Boolean = false, // As of now, only one option can be right
    var isSelected: Boolean = false // This will be modified by the library when user selects particular option, you could manually set it to true but only for one option.
)
```

There are other public methods you can use such as -

`disablePreviousButton(isDisable: Boolean)` - Can be used to disable previous button of the current quiz.

`disableNextButton(isDisable: Boolean)` - Can be used to disable next button of the current quiz.

## Demo

<img src="https://i.imgur.com/kxNmW3p.gif">

## License
GNU GPL v3
