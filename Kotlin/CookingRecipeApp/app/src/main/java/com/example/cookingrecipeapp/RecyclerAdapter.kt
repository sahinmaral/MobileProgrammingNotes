package com.example.cookingrecipeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerAdapter(val foodNameList:ArrayList<String>,val foodIdList:ArrayList<Int>) : RecyclerView.Adapter<RecyclerAdapter.FoodHolder>() {
    lateinit var textViewFoodName : TextView
    lateinit var buttonDeleteRecipe : Button
    lateinit var context: Context

    class FoodHolder(itemView: View) : ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        context = parent.context
        return FoodHolder(view)
    }

    override fun getItemCount(): Int {
        return foodNameList.size
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        textViewFoodName = holder.itemView.findViewById(R.id.textViewFoodName)
        buttonDeleteRecipe = holder.itemView.findViewById(R.id.buttonDeleteRecipe)
        buttonDeleteRecipe.setOnClickListener {
            deleteRecipe(holder.itemView,position)
        }
        holder.itemView.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToRecipeFragment()
            action.locationBeforeRecipeFragment = LocationBeforeRecipeFragment.FROM_LIST
            action.foodId = foodIdList[position]
            Navigation.findNavController(holder.itemView).navigate(action)
        }

        textViewFoodName.text = foodNameList[position]
    }

    private fun deleteRecipe(itemView:View, position:Int){
        val database = context.openOrCreateDatabase("cookingRecipeAppDb", Context.MODE_PRIVATE,null)
        database.execSQL("CREATE TABLE IF NOT EXISTS recipes (ID INTEGER PRIMARY KEY,name VARCHAR,ingredients VARCHAR,image BLOB)")
        database.execSQL("DELETE FROM recipes WHERE ID = '${foodIdList[position]}'")
        this.foodNameList.remove(foodNameList[position])
        this.foodIdList.remove(foodIdList[position])
        this.notifyItemRemoved(position)
    }
}