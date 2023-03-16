package com.example.rockpaperscissorsapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun handleClick(view: View){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Are you ready to start game?")
        alertDialog.setNegativeButton("No",null)
        alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, which ->
            val intent = Intent(this@MainActivity,PlayingActivity::class.java)
            startActivity(intent)
        })

        alertDialog.show()


    }
}