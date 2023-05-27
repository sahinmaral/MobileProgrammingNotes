package com.example.cookingrecipeapp

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Dictionary


class ListFragment : Fragment() {

    private val foodNameList = ArrayList<String>()
    private val foodIdList = ArrayList<Int>()
    lateinit var recyclerAdapter : RecyclerAdapter
    lateinit var recyclerViewRecipes : RecyclerView

    lateinit var cursor : Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            recyclerViewRecipes = view.findViewById(R.id.recyclerViewRecipes)
            recyclerAdapter = RecyclerAdapter(foodNameList,foodIdList)
            recyclerViewRecipes.layoutManager = LinearLayoutManager(context)
            recyclerViewRecipes.adapter = recyclerAdapter

            getFoodsFromDatabase()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun getFoodsFromDatabase(){
        try {
            context?.let {
                val database = it.openOrCreateDatabase("cookingRecipeAppDb", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS recipes (ID INTEGER PRIMARY KEY,name VARCHAR,ingredients VARCHAR,image BLOB)")
                cursor = database.rawQuery("SELECT * FROM recipes",null)
                val foodNameIndex = cursor.getColumnIndex("name")
                val foodIdIndex = cursor.getColumnIndex("ID")

                foodNameList.clear()
                foodIdList.clear()

                while (cursor.moveToNext()){
                    foodNameList.add(cursor.getString(foodNameIndex))
                    foodIdList.add(cursor.getInt(foodIdIndex))
                }

                recyclerAdapter.notifyDataSetChanged()
            }
        }catch (e:Exception){
            throw e
        }finally {
            cursor.close()
        }
    }

}