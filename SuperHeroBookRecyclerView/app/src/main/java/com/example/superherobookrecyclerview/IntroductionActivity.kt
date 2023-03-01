package com.example.superherobookrecyclerview

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        val intent = intent

        val textViewSuperHero = findViewById<TextView>(R.id.textViewSuperHero)
        textViewSuperHero.setText(intent.getStringExtra("superHeroName"))

        val imageViewSuperHero = findViewById<ImageView>(R.id.imageViewSuperHero)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources,intent.getIntExtra("superHeroImage",0))
        imageViewSuperHero.setImageBitmap(bitmap)
    }
}