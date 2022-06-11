package com.company.mathrix

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.random.Random

class Division : AppCompatActivity() {

    lateinit var ivUserAvatar: ImageView
    lateinit var tvUserName: TextView
    lateinit var tvUserLife: TextView
    lateinit var tvUserScore: TextView
    lateinit var tvTime: TextView
    lateinit var tvQuestion: TextView
    lateinit var etAnswer: EditText
    lateinit var okButton: Button
    lateinit var nextButton: Button

    var random = Random(System.currentTimeMillis())
    var firstNumber: Int = 0
    var secondNumber: Int = 0
    var resultEquals: Int = 0

    var userLife = 3
    var userScore = 0
    var userAvatar = 0
    var userName: String? = null

    var timeLeftInMillis = TIME_IN_MILLIS
    var timerActive = false
    lateinit var timer: CountDownTimer

    var makeOkBtnActive = false
    var makeNextButtonActive = false

    companion object {
        const val TIME_IN_MILLIS: Long = 60000L
    }

    lateinit var backgroundMusic: MediaPlayer
    lateinit var clickButtonSound: MediaPlayer
    lateinit var correctAnswerSound: MediaPlayer
    lateinit var wrongAnswerSound: MediaPlayer
    lateinit var gameOverSound: MediaPlayer
    lateinit var timerEndingSound: MediaPlayer

    var timerEndingActivate: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_division)

        ivUserAvatar = findViewById(R.id.ivUserAvatarDivision)
        tvUserName = findViewById(R.id.tvUserNameDivision)
        tvUserLife = findViewById(R.id.tvUserLifeDivision)
        tvUserScore = findViewById(R.id.tvUserScoreDivision)
        tvTime = findViewById(R.id.tvTimeDivision)
        tvQuestion = findViewById(R.id.tvQuestionDivision)
        etAnswer = findViewById(R.id.etAnswerDivision)
        okButton = findViewById(R.id.btnOkDivision)
        nextButton = findViewById(R.id.btnNextQuestionDivision)

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music)
        clickButtonSound = MediaPlayer.create(this, R.raw.click_button)
        correctAnswerSound = MediaPlayer.create(this, R.raw.correct_sound)
        wrongAnswerSound = MediaPlayer.create(this, R.raw.incorrect_sound)
        gameOverSound = MediaPlayer.create(this, R.raw.game_over)
        timerEndingSound = MediaPlayer.create(this, R.raw.timer_ending)

        fillImageAndName()
        continueGame()

        okButton.setOnClickListener {

            if (makeOkBtnActive && etAnswer.text.toString().trim().isNotEmpty()) {

                makeNextButtonActive = true
                makeOkBtnActive = false

                var stringResult = etAnswer.text.toString()
                var userResult: Int = Integer.valueOf(stringResult)

                pauseTimer()
                updateTimeText()

                if (userResult == resultEquals) {
                    correctAnswerSound.start()

                    tvQuestion.text = "That's correct!"
                    userScore += 10
                    tvUserScore.text = userScore.toString()
                } else {
                    userLife--
                    tvUserLife.text = userLife.toString()

                    if (userLife == 0) {
                        backgroundMusic.stop()
                        gameOverSound.start()

                        tvQuestion.text = "Game Over! You have no lives left."

                        makeOkBtnActive = false
                        makeNextButtonActive = false

                        val endingTimer: CountDownTimer = object : CountDownTimer(2000L, 1000L) {
                            override fun onTick(p0: Long) {

                            }

                            override fun onFinish() {
                                val intent = Intent(this@Division, EndGameDivision::class.java)
                                intent.putExtra(Constants.USER_AVATAR_DIVISION, userAvatar)
                                intent.putExtra(Constants.USER_NAME_DIVISION, userName)
                                intent.putExtra(Constants.USER_SCORE_DIVISION, userScore)
                                startActivity(intent)
                                finish()
                            }
                        }.start()
                    } else {
                        wrongAnswerSound.start()

                        tvQuestion.text = "Sorry! That's incorrect."
                    }
                }
            }
        }

        nextButton.setOnClickListener {

            clickButtonSound.start()

            if (makeNextButtonActive) {
                etAnswer.text.clear()
                resetTimer()
                continueGame()
            }
        }

        etAnswer.setOnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        }
    }

    private fun fillImageAndName() {
        userAvatar = intent.getIntExtra(Constants.USER_AVATAR_DIVISION, R.drawable.alan_turing)
        userName = intent.getStringExtra(Constants.USER_NAME_DIVISION)
        ivUserAvatar.setImageResource(userAvatar)
        tvUserName.text = userName
    }

    private fun continueGame() {
        makeNextButtonActive = false
        makeOkBtnActive = true

        do {
            firstNumber = random.nextInt(100)
            secondNumber = random.nextInt(1,20)
        } while (firstNumber % secondNumber > 0)

        resultEquals = firstNumber / secondNumber

        tvQuestion.text = "$firstNumber / $secondNumber"

        timer = object : CountDownTimer(timeLeftInMillis, 1000L) {
            override fun onTick(p0: Long) {
                timeLeftInMillis = p0
                updateTimeText()

                if (p0 <= 10000L && timerEndingActivate) {
                    timerEndingSound.start()
                    timerEndingActivate = false
                }
            }

            override fun onFinish() {
                timerEndingActivate = true

                updateTimeText()
                pauseTimer()
                resetTimer()

                userLife--
                tvUserLife.text = userLife.toString()

                if (userLife == 0) {
                    backgroundMusic.stop()
                    gameOverSound.start()

                    tvQuestion.text = "Game Over! You have no lives left."
                    makeOkBtnActive = false

                    val endingTimer: CountDownTimer = object : CountDownTimer(2000L, 1000L) {
                        override fun onTick(p0: Long) {

                        }

                        override fun onFinish() {
                            val intent = Intent(this@Division, EndGameDivision::class.java)
                            intent.putExtra(Constants.USER_AVATAR_DIVISION, userAvatar)
                            intent.putExtra(Constants.USER_NAME_DIVISION, userName)
                            intent.putExtra(Constants.USER_SCORE_DIVISION, userScore)
                            startActivity(intent)
                            finish()
                        }
                    }.start()
                } else {
                    wrongAnswerSound.start()

                    tvQuestion.text = "Sorry! You run out of time."
                    makeOkBtnActive = false
                    makeNextButtonActive = true
                }
            }
        }.start()

        timerActive = true
    }

    private fun updateTimeText() {
        val seconds = timeLeftInMillis / 1000
        val gameTime = String.format(Locale.getDefault(), "%02d", seconds)
        tvTime.text = gameTime
    }

    private fun pauseTimer() {
        timer.cancel()
        timerActive = false
    }

    private fun resetTimer() {
        timeLeftInMillis = TIME_IN_MILLIS
        updateTimeText()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        backgroundMusic.start()
        backgroundMusic.isLooping = true
        super.onResume()
    }

    override fun onPause() {
        backgroundMusic.pause()
        super.onPause()
    }

    override fun onDestroy() {
        backgroundMusic.stop()
        backgroundMusic.release()

        clickButtonSound.stop()
        clickButtonSound.release()

        correctAnswerSound.stop()
        correctAnswerSound.release()

        wrongAnswerSound.stop()
        wrongAnswerSound.release()

        gameOverSound.stop()
        gameOverSound.release()

        timerEndingSound.stop()
        timerEndingSound.release()
        super.onDestroy()
    }
}