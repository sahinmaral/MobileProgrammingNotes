package com.example.learnoopbykotlin

class SpecialActor(name: String, age: Int, profession: String) : Actor(name, age, profession) {
    fun singSong(){
        println("${this.name} can sing a song")
    }
}