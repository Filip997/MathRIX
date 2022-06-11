package com.company.mathrix

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var etUserName: EditText
    lateinit var btnStart: Button
    lateinit var imageSpinner: Spinner
    lateinit var spinnerAdapter: ArrayAdapter<Int>
    val avatars = ArrayList<Int>()
    var avatarSelected: Int = 0

    lateinit var startButtonSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUserName = findViewById(R.id.etUserName)
        btnStart = findViewById(R.id.btnStart)
        imageSpinner = findViewById(R.id.imageSpinner)

        startButtonSound = MediaPlayer.create(this, R.raw.start_button)

        avatars.add(R.drawable.alan_turing)
        avatars.add(R.drawable.albert_einstein)
        avatars.add(R.drawable.ada_lovelace)
        avatars.add(R.drawable.blaise_pascal)
        avatars.add(R.drawable.fibonacci)
        avatars.add(R.drawable.sophie_germain)
        avatars.add(R.drawable.isaac_newton)

        avatarSelected = avatars[0]

        spinnerAdapter = SpinnerAdapter(this, avatars.toTypedArray())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        imageSpinner.adapter = spinnerAdapter

        imageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                avatarSelected = avatars[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        btnStart.setOnClickListener {

            startButtonSound.start()

            if (etUserName.text.trim().isEmpty()) {

                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()

            } else {

                val intent = Intent(this@MainActivity, OperationChooser::class.java)

                val userName: String = etUserName.text.toString()

                intent.putExtra(Constants.USER_NAME, userName)
                intent.putExtra(Constants.USER_AVATAR, avatarSelected)

                startActivity(intent)
                finish()

            }
        }

        etUserName.setOnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        startButtonSound.stop()
        startButtonSound.release()
        super.onDestroy()
    }
}