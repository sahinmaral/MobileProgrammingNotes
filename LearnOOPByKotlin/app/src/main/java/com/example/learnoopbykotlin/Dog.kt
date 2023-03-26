package com.example.learnoopbykotlin

class Dog : Animal() {
    override fun makeSound(){
        super.makeSound()
        println("Bark")
    }
}