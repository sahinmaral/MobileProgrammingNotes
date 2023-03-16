package com.example.rockpaperscissorsapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random


class PlayingActivity : AppCompatActivity() {
    lateinit var imageViewComputerSelect : ImageView
    lateinit var imageViewYourSelect : ImageView
    lateinit var imageViewRockSelect : ImageView
    lateinit var imageViewPaperSelect : ImageView
    lateinit var imageViewScissorsSelect : ImageView

    lateinit var textViewWinStatus : TextView
    lateinit var textViewScore : TextView

    var yourSelection : Selection? = null

    var survivedRounds = 0
    var topScoreComputer = 0
    var topScorePlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)


        setCloseGameDialog()
        setElements()

        imageViewPaperSelect.setOnClickListener(View.OnClickListener {
            setImageBySelection(Selection.PAPER,imageViewYourSelect)
            yourSelection = Selection.PAPER
        })

        imageViewRockSelect.setOnClickListener(View.OnClickListener {
            setImageBySelection(Selection.ROCK,imageViewYourSelect)
            yourSelection = Selection.ROCK
        })

        imageViewScissorsSelect.setOnClickListener(View.OnClickListener {
            setImageBySelection(Selection.SCISSORS,imageViewYourSelect)
            yourSelection = Selection.SCISSORS
        })

        imageViewComputerSelect.setOnClickListener(View.OnClickListener {
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage("Are you ready ?")
            alertDialog.setNegativeButton("No",null)
            alertDialog.setPositiveButton("Yes",DialogInterface.OnClickListener{ dialog, which ->
                startGame()
            })

            alertDialog.show()
        })
    }

    private fun setElements() {
        topScoreComputer = resources.getInteger(R.integer.topScoreComputer)
        topScorePlayer = resources.getInteger(R.integer.topScorePlayer)

        imageViewComputerSelect = findViewById(R.id.imageViewComputerSelect)
        imageViewYourSelect = findViewById(R.id.imageViewYourSelect)
        imageViewRockSelect = findViewById(R.id.imageViewRockSelect)
        imageViewPaperSelect = findViewById(R.id.imageViewPaperSelect)
        imageViewScissorsSelect = findViewById(R.id.imageViewScissorsSelect)

        textViewScore = findViewById(R.id.textViewScore)
        textViewWinStatus = findViewById(R.id.textViewWinStatus)
    }

    private fun setCloseGameDialog() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    var alertDialog = AlertDialog.Builder(this@PlayingActivity)
                    alertDialog.setMessage("Are you sure you want to exit game ?")
                    alertDialog.setNegativeButton("No", null)
                    alertDialog.setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialog, which ->
                            finishAffinity()
                        })

                    alertDialog.show()
                }
            }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun startGame(){

        if(yourSelection == null){
            Toast.makeText(this,"You have to choose one of them",Toast.LENGTH_SHORT).show()
        }else{
            val randomNumber = Random.nextInt(0,3)
            val computerSelection = enumValues<Selection>()[randomNumber]
            setImageBySelection(computerSelection,imageViewComputerSelect)

            calculateWinStatus(yourSelection!!,computerSelection)

            survivedRounds++

            val score = Integer.parseInt(textViewScore.text.toString())
            if(score == topScoreComputer || score == topScorePlayer){
                calculateOverallStatus(score)
            }
        }

    }

    private fun setImageBySelection(selection:Selection,imageView:ImageView){
        when(selection){
            Selection.ROCK -> imageView.setImageResource(R.drawable.rock)
            Selection.PAPER -> imageView.setImageResource(R.drawable.paper)
            else -> imageView.setImageResource(R.drawable.scissors)
        }
    }

    private fun calculateWinStatus(yourSelection:Selection,computerSelection:Selection){
        when (yourSelection) {
            Selection.ROCK -> {
                when (computerSelection) {
                    Selection.PAPER -> {
                        textViewWinStatus.text = resources.getString(R.string.loseStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.red))
                        textViewScore.text = (Integer.parseInt(textViewScore.text.toString()) - 1).toString()
                    }
                    Selection.SCISSORS -> {
                        textViewWinStatus.text = resources.getString(R.string.winStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.green))
                        textViewScore.text = (Integer.parseInt(textViewScore.text.toString()) + 1).toString()
                    }
                    else -> {
                        textViewWinStatus.text = resources.getString(R.string.drawStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.yellow))
                    }
                }
            }
            Selection.PAPER -> {
                when (computerSelection) {
                    Selection.SCISSORS -> {
                        textViewWinStatus.text = resources.getString(R.string.loseStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.red))
                        textViewScore.text = (Integer.parseInt(textViewScore.text.toString()) - 1).toString()
                    }
                    Selection.ROCK -> {
                        textViewWinStatus.text = resources.getString(R.string.winStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.green))
                        textViewScore.text = (Integer.parseInt(textViewScore.text.toString()) + 1).toString()
                    }
                    else -> {
                        textViewWinStatus.text = resources.getString(R.string.drawStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.yellow))
                    }
                }
            }
            else -> {
                when (computerSelection) {
                    Selection.ROCK -> {
                        textViewWinStatus.text = resources.getString(R.string.loseStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.red))
                        textViewScore.text = (Integer.parseInt(textViewScore.text.toString()) - 1).toString()
                    }
                    Selection.PAPER -> {
                        textViewWinStatus.text = resources.getString(R.string.winStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.green))
                        textViewScore.text = (Integer.parseInt(textViewScore.text.toString()) + 1).toString()
                    }
                    else -> {
                        textViewWinStatus.text = resources.getString(R.string.drawStatus)
                        textViewWinStatus.setTextColor(ContextCompat.getColor(this@PlayingActivity,R.color.yellow))
                    }
                }
            }
        }
    }

    private fun calculateOverallStatus(score:Int){
        val intent = Intent(this,EndingActivity::class.java)
        if(score == resources.getInteger(R.integer.topScorePlayer)){
            intent.putExtra("survivedRounds",survivedRounds)
            intent.putExtra("winStatus",resources.getString(R.string.winStatus))
        }else if(score == resources.getInteger(R.integer.topScoreComputer)){
            intent.putExtra("survivedRounds",survivedRounds)
            intent.putExtra("winStatus",resources.getString(R.string.loseStatus))
        }

        startActivity(intent)
    }


}