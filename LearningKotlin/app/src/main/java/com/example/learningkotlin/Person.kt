package com.example.learningkotlin


class Person  {
    private var firstName : String
    private var lastName : String
    private var age : Int

    constructor(firstName:String,lastName:String,age:Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }

    fun getFirstName(): String {
        return firstName
    }

    fun getLastName() : String{
        return lastName
    }

    fun getAge() : Int{
        return age
    }

    fun setFirstName(firstName:String){

        if(firstName == null){
            throw Exception("firstName null exception")
        }
        if(firstName.length < 2 || firstName.length > 15){
            throw Exception("Minimum length of firstname should be 2 and maximum length of firstName should be 15")
        }
        this.firstName = firstName
    }

    fun setLastName(lastName:String){
        if(lastName == null){
            throw Exception("lastName null exception")
        }
        if(lastName.length < 2 || lastName.length > 15){
            throw Exception("Minimum length of lastName should be 2 and maximum length of lastName should be 15")
        }
        this.lastName = lastName
    }

    fun setAge(age:Int){
        if(age == null){
            throw Exception("age null exception")
        }
        if(age == 0){
            throw Exception("age cannot set zero")
        }
        this.age = age

    }
}



/*
class Person{
    var firstName = ""
    var lastName = ""
    var age = 0

    // Constructor

    constructor(firstName:String,lastName:String,age:Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }
}

*/