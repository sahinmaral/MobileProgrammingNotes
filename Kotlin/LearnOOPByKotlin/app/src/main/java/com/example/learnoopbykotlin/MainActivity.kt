package com.example.learnoopbykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("---- Classes ----")

        val person = Person("Sahin MARAL",23)

        println("---- Encapsulation ----")

        val actor = Actor("Ahmet",23,"Actor")
        //actor.profession = "Baker"
        println(actor.name)

        println("---- Inheritance ----")

        val specialActor = SpecialActor("Hasan",30,"Special Actor")

        println("---- Polymorphism ----")

        // Static Polymorphism

        val process = Processes()
        println(process.multiply(2,3))
        println(process.multiply(2,3,4))

        // Dynamic Polymorphism

        val dog = Dog()
        dog.makeSound()

        println("---- Abstraction & Interface ----")

        val employee = Employee("Sahin","Engineer")
        employee.getProfession()

        println("---- Lambda Expressions ----")

        val printSomethingLambda = {input:String -> println(input)}
        printSomethingLambda("something")

        val multiplyLambda = {a:Int,b:Int -> a*b}

        println(multiplyLambda(2,10))
    }
}