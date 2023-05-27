package com.example.basicsuperheroproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createSuperHero(view: View){
        val identityName = editTextIdentityName.text.toString()
        val realName = editTextRealName.text.toString()
        val power = editTextPower.text.toString().toInt()

        val superHero = SuperHero(identityName,realName,power)
        textViewSuperHeroIdentityName.text = "Identity Name : ${superHero.getIdentityName()}"
        textViewSuperHeroRealName.text = "Real Name : ${superHero.getRealName()}"
        textViewSuperHeroPower.text = "Power : ${superHero.getPower().toString()}"
    }
}