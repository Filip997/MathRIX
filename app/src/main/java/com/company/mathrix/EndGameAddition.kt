package com.company.mathrix

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class EndGameAddition : AppCompatActivity() {

    lateinit var tvMessageUser: TextView
    lateinit var tvFinalScore: TextView
    lateinit var recycleView: RecyclerView
    lateinit var btnPlayAgain: Button
    lateinit var btnExit: Button

    lateinit var user: User
    var highScoreUsers: ArrayList<User> = arrayListOf()
    var checkEquality: Boolean = false
    var checkScore: Boolean = false

    var name: String? = null
    var avatar: Int = 0
    var score: Int = 0

    lateinit var recyclerAdapter: RecyclerAdapter

    lateinit var backgroundMusic: MediaPlayer
    lateinit var clickButtonSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game_addition)

        tvMessageUser = findViewById(R.id.tvMessageUserAddition)
        tvFinalScore = findViewById(R.id.tvFinalScoreAddition)
        recycleView = findViewById(R.id.recycleViewAddition)
        btnPlayAgain = findViewById(R.id.btnPlayAgainAddition)
        btnExit = findViewById(R.id.btnExitAddition)

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music)
        clickButtonSound = MediaPlayer.create(this, R.raw.click_button)

        user = getUserDataAndCreateUserObject()


        highScoreUsers = FileHelperAddition.readData(this)

        if (user.userScore > 0) {
            if (highScoreUsers.size > 0) {
                for (u in highScoreUsers) {
                    if (user == u) {
                        checkEquality = true
                        if (user.userScore > u.userScore) {
                            checkScore = true
                            highScoreUsers.remove(u)
                        }
                    }
                }

                if (!checkEquality || checkScore) {
                    highScoreUsers.add(user)
                }
            } else {
                highScoreUsers.add(user)
            }

            tvMessageUser.text = "CONGRATULATIONS!"
        } else {
            tvMessageUser.text = "TOO BAD!"
        }

        tvFinalScore.text = "Your score is: $score"

        highScoreUsers.sortByDescending { it.userScore }

        recycleView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = RecyclerAdapter(highScoreUsers, this)
        recycleView.adapter = recyclerAdapter

        FileHelperAddition.saveData(highScoreUsers, this)

        btnPlayAgain.setOnClickListener {
            clickButtonSound.start()
            showDialog()
        }

        btnExit.setOnClickListener {
            clickButtonSound.start()
            System.exit(0)
        }
    }

    private fun getUserDataAndCreateUserObject() : User {
        name = intent.getStringExtra(Constants.USER_NAME_ADDITION)
        avatar = intent.getIntExtra(Constants.USER_AVATAR_ADDITION, R.drawable.alan_turing)
        score = intent.getIntExtra(Constants.USER_SCORE_ADDITION, 0)

        return User(name!!, avatar, score)
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this@EndGameAddition)
        alertDialog.setTitle("Play Again")
        alertDialog.setMessage("Do you want to change your name and/or avatar?")
        alertDialog.setCancelable(false)

        alertDialog.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
            clickButtonSound.start()

            val intent = Intent(this@EndGameAddition, OperationChooser::class.java)
            intent.putExtra(Constants.USER_NAME, name)
            intent.putExtra(Constants.USER_AVATAR, avatar)
            startActivity(intent)
            finish()
        })

        alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            clickButtonSound.start()

            val intent = Intent(this@EndGameAddition, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        alertDialog.setNeutralButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            clickButtonSound.start()

            dialogInterface.cancel()
        })

        alertDialog.create()
        alertDialog.show()
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