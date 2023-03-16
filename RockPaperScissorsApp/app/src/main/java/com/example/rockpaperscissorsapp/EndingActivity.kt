package com.example.rockpaperscissorsapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EndingActivity : AppCompatActivity(){
    lateinit var textViewWinStatusMessage : TextView
    lateinit var listViewScoreboard : ListView

    lateinit var cursor : Cursor
    lateinit var database : SQLiteDatabase

    var scoreBoard : ArrayList<Int> = ArrayList()
    lateinit var scoreBoardAdapter : ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ending)

        setCloseGameDialog()

        try {
            database = this@EndingActivity.openOrCreateDatabase("rockPaperScissorsAppDb", Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS scoreBoard(id INTEGER PRIMARY KEY,survivedRounds INTEGER, overAllStatus VARCHAR)")

        }catch (exception:Exception){
            exception.printStackTrace()
        }

        textViewWinStatusMessage = findViewById(R.id.textViewWinStatusMessage)
        listViewScoreboard = findViewById(R.id.listViewScoreboard)

        val intent = intent
        val winStatus = intent.getStringExtra("winStatus")
        val survivedRounds = intent.getIntExtra("survivedRounds",0)

        if(winStatus == resources.getString(R.string.winStatus)){
            textViewWinStatusMessage.setTextColor(ContextCompat.getColor(this@EndingActivity,R.color.green))
            database.execSQL("INSERT INTO scoreBoard (survivedRounds,overAllStatus) VALUES (${survivedRounds},'win')")
        }else if(winStatus == resources.getString(R.string.loseStatus)){
            textViewWinStatusMessage.setTextColor(ContextCompat.getColor(this@EndingActivity,R.color.red))
            database.execSQL("INSERT INTO scoreBoard (survivedRounds,overAllStatus) VALUES (${survivedRounds},'lose')")
        }

        textViewWinStatusMessage.text = winStatus

        setScoreboard(winStatus!!)
    }

    private fun setScoreboard(winStatus : String){
        when(winStatus){
            resources.getString(R.string.winStatus) -> {
                cursor = database.rawQuery("SELECT * FROM scoreBoard WHERE overAllStatus == 'win' ORDER BY survivedRounds ASC LIMIT 10",null)
            }else -> {
                cursor = database.rawQuery("SELECT * FROM scoreBoard WHERE overAllStatus == 'lose' ORDER BY survivedRounds DESC LIMIT 10",null)
            }
        }

        val survivedRoundsIndex = cursor.getColumnIndex("survivedRounds")
        while (cursor.moveToNext()){
            scoreBoard.add(cursor.getInt(survivedRoundsIndex))
        }

        scoreBoardAdapter = ArrayAdapter(this@EndingActivity,android.R.layout.simple_expandable_list_item_1,scoreBoard)
        listViewScoreboard.adapter = scoreBoardAdapter
    }

    private fun setCloseGameDialog() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    var alertDialog = AlertDialog.Builder(this@EndingActivity)
                    alertDialog.setMessage("Are you sure you want to exit game ?")
                    alertDialog.setNegativeButton("No", null)
                    alertDialog.setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialog, which ->
                            finishAffinity();
                        })

                    alertDialog.show()
                }
            }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    fun handleClick(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}