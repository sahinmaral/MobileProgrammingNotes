package com.example.learningnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class FirstFragment : Fragment() {

    lateinit var button1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        button1 = view.findViewById(R.id.button)
        button1.setOnClickListener({
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
            action.setUsername("sahinmaral")

            Navigation.findNavController(it).navigate(action)
            //Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment)
        })
    }

}