package com.company.mathrix

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class OperationChooser : AppCompatActivity() {

    lateinit var tvWelcomeUser: TextView
    lateinit var btnAddition: Button
    lateinit var btnSubtraction: Button
    lateinit var btnMultiplication: Button
    lateinit var btnDivision: Button

    lateinit var backgroundMusic: MediaPlayer
    lateinit var clickButtonSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_chooser)

        tvWelcomeUser = findViewById(R.id.tvWelcomeUser)
        btnAddition = findViewById(R.id.btnAddition)
        btnSubtraction = findViewById(R.id.btnSubtraction)
        btnMultiplication = findViewById(R.id.btnMultiplication)
        btnDivision = findViewById(R.id.btnDivision)

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music)
        clickButtonSound = MediaPlayer.create(this, R.raw.click_button)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val userAvatar = intent.getIntExtra(Constants.USER_AVATAR, R.drawable.alan_turing)
        tvWelcomeUser.text = "$userName!"

        btnAddition.setOnClickListener {

            clickButtonSound.start()

            val intent = Intent(this@OperationChooser, Addition::class.java)
            intent.putExtra(Constants.USER_NAME_ADDITION, userName)
            intent.putExtra(Constants.USER_AVATAR_ADDITION, userAvatar)
            startActivity(intent)
            finish()
        }

        btnSubtraction.setOnClickListener {

            clickButtonSound.start()

            val intent = Intent(this@OperationChooser, Subtraction::class.java)
            intent.putExtra(Constants.USER_NAME_SUBTRACTION, userName)
            intent.putExtra(Constants.USER_AVATAR_SUBTRACTION, userAvatar)
            startActivity(intent)
            finish()
        }

        btnMultiplication.setOnClickListener {

            clickButtonSound.start()

            val intent = Intent(this@OperationChooser, Multiplication::class.java)
            intent.putExtra(Constants.USER_NAME_MULTIPLICATION, userName)
            intent.putExtra(Constants.USER_AVATAR_MULTIPLICATION, userAvatar)
            startActivity(intent)
            finish()
        }

        btnDivision.setOnClickListener {

            clickButtonSound.start()

            val intent = Intent(this@OperationChooser, Division::class.java)
            intent.putExtra(Constants.USER_NAME_DIVISION, userName)
            intent.putExtra(Constants.USER_AVATAR_DIVISION, userAvatar)
            startActivity(intent)
            finish()
        }
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
        super.onDestroy()
    }
}