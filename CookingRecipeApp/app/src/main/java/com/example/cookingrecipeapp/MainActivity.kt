package com.example.cookingrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_recipe,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addRecipeItem){
            val action = ListFragmentDirections.actionListFragmentToRecipeFragment()
            action.locationBeforeRecipeFragment = LocationBeforeRecipeFragment.FROM_NEW
            Navigation.findNavController(this@MainActivity,R.id.fragmentContainerView).navigate(R.id.action_listFragment_to_recipeFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}