package com.example.learnoopbykotlin


// If class that you chose is available for inheritance , you have to write "open" keyword before class keyword
open class Actor(name:String, age:Int , profession:String){
    var name : String? = name
        private set
        get

    var age : Int? = age
        private set
        get

    var profession : String? = profession


}