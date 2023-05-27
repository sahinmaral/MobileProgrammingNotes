package com.example.handlerrunnableproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var number = 0
    var runnable = Runnable{}
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startTimer(view: View){

        number = 0

        runnable = object : Runnable {
            override fun run() {
                number = number + 1
                textView.setText("Timer : ${number}")
                handler.postDelayed(runnable,1000)
            }
        }

        handler.post(runnable)

    }

    fun stopTimer(view:View){
        handler.removeCallbacks(runnable)
        number = 0
        textView.setText(number)
    }

}