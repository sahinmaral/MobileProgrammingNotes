package com.example.learningkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables

        println("---- Variables ----")

        var text = "hello kotlin"
        text = "hello learning kotlin"
        println(text)

        // Constants

        println("---- Constants ----")

        val text2 = "hello kotlin"
        //text2 = "hello learning kotlin" --> ERROR
        println(text2)

        // Integer

        println("---- Integer ----")

        var number1 = 16
        number1 = number1 / 4
        println(number1)

        // Long

        println("---- Long ----")

        var number2 = 1600000000000
        number2 = number2 / 4
        println(number2)

        // Double

        println("---- Double ----")

        val pi = 3.14
        println("Area of 3 unit radius circle : " + pi * 3 * 3)

        // Float

        println("---- Float ----")

        val piFloat = 3.14f
        println("Area of 3 unit radius circle : " + piFloat * 3 * 3)

        // String

        println("---- String ----")

        val stringExample = "Hello world"
        println("Length of stringExample : "+stringExample.length)

        val name = "Sahin"
        val surname = "MARAL"
        println(name + " " + surname)

        // Boolean

        println("---- Boolean ----")

        val isUsernameCorrect = true
        val isPasswordCorrect = false
        val status = isUsernameCorrect && isPasswordCorrect
        // OR => ||
        // AND => &&
        println(status)

        // Convert Data Types

        println("---- Convert Data Types ----")

        val stringifiedNumber = "500"
        println(stringifiedNumber.toInt() / 2)

        // Arrays

        println("---- Arrays ----")

        val arrayNumbers = intArrayOf(1,2,3,4)
        println(arrayNumbers.size)
        println(arrayNumbers[2])

        val arrayTexts = arrayOf("Hello","Bonjour","Merhaba")
        println(arrayTexts.size)
        println(arrayTexts[0])
        println(arrayTexts.get(1))

        arrayNumbers.set(3,5)
        println(arrayNumbers.get(3))

        val mixedArray = arrayOf(1,2.0,true,"Hello")
        println(mixedArray[2])

        // Array Lists

        println("---- Array Lists ----")

        val exampleArrayList = arrayListOf("Hello","Bonjour","Merhaba")
        exampleArrayList.add("hola")
        exampleArrayList.add("konnichiwa")

        // Sets

        println("---- Sets ----")

        val setExample = setOf(1,2,3,3,4,5,5)
        // setExample => 1,2,3,4,5
        println(setExample.size)

        for ((index, number) in setExample.withIndex()){
            println("number : ${number} , index : ${index}")
        }

        // Maps

        println("---- Maps ----")

        val hashMapExample = hashMapOf(Pair("Apple",100),Pair("Meat",200),Pair("Chicken",300))

        hashMapExample["Chocolate"] = 700

        println("Calorie of apple : ${hashMapExample["Apple"]} ")
        println("Calorie of chocolate : ${hashMapExample["Chocolate"]} ")

        // Conditions : When

        println("---- Conditions : When ----")

        var gradePoint = 45

        when(gradePoint){
            in 91..100 -> println("AA")
            in 81..90 -> println("BA")
            in 71..80 -> println("BB")
            in 61..70 -> println("CB")
            in 51..60 -> println("CC")
            in 41..50 -> println("CD")
            in 36..40 -> println("DD")
            in 31..35 -> println("DF")
            else -> println("F")
        }

        // Loops : For

        println("---- Loops : For ----")

        val numbersExample = 1..100
        var evenNumberCount = 0
        for(number in numbersExample){
            if(number % 2 == 0){
                evenNumberCount++
            }
        }

        println("Count of even number in 1-100 : ${evenNumberCount}")

        // Functions

        println("---- Functions ----")

        // Classes

        println("---- Classes ----")

        val person1 = Person("Sahin","MARAL",23)

        textView2.text = person1.getFirstName() + " " + person1.getLastName() + " " + person1.getAge()

    }

    fun showOddNumbersInOnetoOneHundred(view: View){
        var numbersExample2 = 1..100
        textView1.text = "Count of odd numbers in 1-100 : ${returnCountOfOddNumbersOfList(numbersExample2.toList().toTypedArray())}"
    }

    fun showEvenNumbersInOnetoOneHundred(view: View){
        var numbersExample2 = 1..100
        textView1.text = "Count of even numbers in 1-100 : ${returnCountOfEvenNumbersOfList(numbersExample2.toList().toTypedArray())}"
    }

    fun returnCountOfOddNumbersOfList(numbers:Array<Int>): Int {
        var oddNumberCount = 0
        for (number in numbers){
            if(number % 2 == 1){
                oddNumberCount++
            }
        }
        return oddNumberCount
    }

    fun returnCountOfEvenNumbersOfList(numbers:Array<Int>): Int {
        var evenNumberCount = 0
        for (number in numbers){
            if(number % 2 == 0){
                evenNumberCount++
            }
        }
        return evenNumberCount
    }
}