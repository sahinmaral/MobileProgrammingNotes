package com.example.learningtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textViewTimer.setText("Remaining time : ${millisUntilFinished/1000}")
            }

            override fun onFinish() {
                Toast.makeText(applicationContext,"Time is up",Toast.LENGTH_LONG).show()
            }
        }
        timer.start()
    }
}