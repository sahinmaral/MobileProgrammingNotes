package com.example.basicsuperheroproject

class SuperHero {
    
        private var identityName : String
        private var realName : String
        private var power : Int

        constructor(identityName:String,realName:String,power:Int) {
            this.identityName = identityName
            this.realName = realName
            this.power = power
        }

        fun getIdentityName(): String {
            return identityName
        }

        fun getRealName() : String{
            return realName
        }

        fun getPower() : Int{
            return power
        }

        fun setIdentityName(identityName:String){

            if(identityName == null){
                throw Exception("identityName null exception")
            }
            if(identityName.length < 2 || identityName.length > 15){
                throw Exception("Minimum length of identityName should be 2 and maximum length of identityName should be 15")
            }
            this.identityName = identityName
        }

        fun setRealName(realName:String){
            if(realName == null){
                throw Exception("realName null exception")
            }
            if(realName.length < 2 || realName.length > 15){
                throw Exception("Minimum length of realName should be 2 and maximum length of realName should be 15")
            }
            this.realName = realName
        }

        fun setPower(power:Int){
            if(power == null){
                throw Exception("power null exception")
            }
            if(power == 0){
                throw Exception("power cannot set zero")
            }
            this.power = power

        }
    }
