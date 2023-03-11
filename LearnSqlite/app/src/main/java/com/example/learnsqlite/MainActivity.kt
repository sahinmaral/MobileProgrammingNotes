package com.example.learnsqlite

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.core.widget.doOnTextChanged

class DatabaseModel{
    private val id:Int;
    private val name:String;
    private val price:Int;

    fun getPrice() : Int{
        return this.price
    }

    fun getName() : String{
        return this.name
    }

    fun getId() : Int{
        return this.id
    }

    constructor(id:Int,name:String,price:Int){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class MainActivity : AppCompatActivity() {
    lateinit var listViewProducts : ListView
    lateinit var editTextFilter : EditText
    lateinit var database : SQLiteDatabase
    lateinit var cursor : Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewProducts = findViewById(R.id.listViewProducts)
        editTextFilter = findViewById(R.id.editTextFilter)

        editTextFilter.doOnTextChanged { text, start, before, count ->
            handleEditTextChange(text)
        }

        try {
            database = this.openOrCreateDatabase("productsDb", Context.MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS products (id INTEGER PRIMARY KEY, name VARCHAR, price INTEGER)")
            //database.execSQL("INSERT INTO products (name,price) VALUES ('apple',25)")
            //database.execSQL("INSERT INTO products (name,price) VALUES ('banana',45)")
            //database.execSQL("INSERT INTO products (name,price) VALUES ('pea',20)")
            //database.execSQL("INSERT INTO products (name,price) VALUES ('orange',30)")
            //database.execSQL("INSERT INTO products (name,price) VALUES ('pineapple',100)")

            val databaseModels = mutableListOf<DatabaseModel>();

            cursor = database.rawQuery("SELECT * FROM products",null)
            val idColumnIndex = cursor.getColumnIndex("id")
            val nameColumnIndex = cursor.getColumnIndex("name")
            val priceColumnIndex = cursor.getColumnIndex("price")
            while (cursor.moveToNext()){
                val databaseModel = DatabaseModel(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(nameColumnIndex),
                    cursor.getInt(priceColumnIndex)
                )
                databaseModels.add(databaseModel);
            }

            val mappedDatabaseModels : Array<String> =  databaseModels.map { it.getName() }.toTypedArray()

            val adapter = ArrayAdapter(applicationContext,android.R.layout.simple_expandable_list_item_1,mappedDatabaseModels)
            listViewProducts.adapter = adapter


        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun handleEditTextChange(text:CharSequence?){
        val databaseModels = mutableListOf<DatabaseModel>();

        if(text.isNullOrBlank()){
            cursor = database.rawQuery("SELECT * FROM products",null)
        }else{
            cursor = database.rawQuery("SELECT * FROM products WHERE name LIKE '%${text}%'",null)
        }

        val idColumnIndex = cursor.getColumnIndex("id")
        val nameColumnIndex = cursor.getColumnIndex("name")
        val priceColumnIndex = cursor.getColumnIndex("price")
        while (cursor.moveToNext()){
            val databaseModel = DatabaseModel(
                cursor.getInt(idColumnIndex),
                cursor.getString(nameColumnIndex),
                cursor.getInt(priceColumnIndex)
            )
            databaseModels.add(databaseModel);
        }

        val mappedDatabaseModels : Array<String> =  databaseModels.map { it.getName() }.toTypedArray()

        val adapter = ArrayAdapter(applicationContext,android.R.layout.simple_expandable_list_item_1,mappedDatabaseModels)
        listViewProducts.adapter = adapter
    }
}