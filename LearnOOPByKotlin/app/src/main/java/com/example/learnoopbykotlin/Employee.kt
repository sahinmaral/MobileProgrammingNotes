package com.example.learnoopbykotlin

class Employee(name: String,val profession:String) : Human(name) {
    override fun getProfession() {
        println(this.profession)
    }
}