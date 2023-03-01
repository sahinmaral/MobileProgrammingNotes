package com.example.simplecalculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var firstNumber : Int = 0
    var secondNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    

    fun addNumbers(view: View){
        firstNumber = editTextFirstNumber.text.toString().toInt()
        secondNumber = editTextSecondNumber.text.toString().toInt()

        var result = firstNumber + secondNumber
        editTextResult.setText("Result : ${result}")
    }

    fun subtractNumbers(view: View){
        firstNumber = editTextFirstNumber.text.toString().toInt()
        secondNumber = editTextSecondNumber.text.toString().toInt()

        var result = firstNumber - secondNumber
        editTextResult.setText("Result : ${result}")
    }

    fun multiplyNumbers(view: View){
        firstNumber = editTextFirstNumber.text.toString().toInt()
        secondNumber = editTextSecondNumber.text.toString().toInt()

        var result = firstNumber * secondNumber
        editTextResult.setText("Result : ${result}")
    }

    fun divideNumbers(view: View){
        firstNumber = editTextFirstNumber.text.toString().toInt()
        secondNumber = editTextSecondNumber.text.toString().toInt()

        var result = (firstNumber.toDouble() / secondNumber.toDouble())
        editTextResult.setText("Result : ${result}")
    }
}