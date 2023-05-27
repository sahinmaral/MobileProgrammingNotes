package com.example.learningnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation


class SecondFragment : Fragment() {

    lateinit var button1 : Button
    lateinit var textViewMessage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button1 = view.findViewById(R.id.button)
        textViewMessage = view.findViewById(R.id.textViewMessage)

        arguments?.let{
            textViewMessage.setText(SecondFragmentArgs.fromBundle(it).username)
        }

        button1.setOnClickListener({
            Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_firstFragment)
        })
    }

}