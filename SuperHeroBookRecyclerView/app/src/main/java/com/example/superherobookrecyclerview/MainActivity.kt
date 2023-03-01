package com.example.superherobookrecyclerview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var superHeroNames = ArrayList<String>()
        superHeroNames.add("Batman")
        superHeroNames.add("Flash")
        superHeroNames.add("Iron Man")
        superHeroNames.add("Spider Man")
        superHeroNames.add("Superman")

        val batmanImage = R.drawable.batman
        val flashImage = R.drawable.flash
        val ironmanImage = R.drawable.ironman
        val spidermanImage = R.drawable.spiderman
        val supermanImage = R.drawable.superman

        var superHeroImages = ArrayList<Int>()
        superHeroImages.add(batmanImage)
        superHeroImages.add(flashImage)
        superHeroImages.add(ironmanImage)
        superHeroImages.add(spidermanImage)
        superHeroImages.add(supermanImage)

        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        val adapter = RecyclerAdapter(superHeroNames,superHeroImages)
        recyclerView.adapter = adapter

    }
}