package com.example.learnoopbykotlin

class Person {
    var name:String?=null
    var age:Int?=null

    constructor(name:String,age:Int){
        this.name = name
        this.age = age
        println("Constructor has been started")
    }

    init {
        println("Init has been started, init will start first instead of constructor")
    }
}