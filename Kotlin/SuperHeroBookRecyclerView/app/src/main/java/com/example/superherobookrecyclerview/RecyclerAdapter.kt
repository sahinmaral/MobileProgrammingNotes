package com.example.superherobookrecyclerview


import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerAdapter(val superHeroList:ArrayList<String>,val superHeroImages:ArrayList<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Inflater , LayoutInflater, MenuInflater

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return superHeroList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val textView = holder.itemView.findViewById<TextView>(R.id.recyclerViewTextView)
        textView.setText(superHeroList[position])
        textView.setOnClickListener{
            val intent = Intent(holder.itemView.context,IntroductionActivity::class.java)
            intent.putExtra("superHeroName",superHeroList[position])
            intent.putExtra("superHeroImage",superHeroImages[position])
            holder.itemView.context.startActivity(intent)
        }
    }

}