package com.example.learningcontext

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(applicationContext.packageName,
            MODE_PRIVATE)

        textViewUsername.setText(sharedPreferences.getString("username",""))

        //this, this@MainActivity -> context of activity
        Toast.makeText(this@MainActivity,"Welcome",Toast.LENGTH_LONG).show()
    }

    fun saveUsername(view:View){
        val username = editTextUsername.text.toString()
        if(username == ""){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show()
        }else{
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.apply()

            textViewUsername.setText(sharedPreferences.getString("username",""))
        }


    }

    fun deleteUsername(view:View){
        val editor = sharedPreferences.edit()
        editor.remove("username")
        editor.apply()

        textViewUsername.setText("")
    }

    fun showMessage(view: View){
        var alertMessageBuilder = AlertDialog.Builder(this@MainActivity)
        alertMessageBuilder.setTitle("Alert")
        alertMessageBuilder.setMessage("Are you sure you want to rate the application ?")
        alertMessageBuilder.setNegativeButton("No",null)
        alertMessageBuilder.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this@MainActivity,"Thank you for rating our application",Toast.LENGTH_SHORT)
        })

        alertMessageBuilder.show()
    }
}