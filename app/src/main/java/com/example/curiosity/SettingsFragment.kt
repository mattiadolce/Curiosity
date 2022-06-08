package com.example.curiosity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    lateinit var listView :  ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val listAreeInteresse = arrayListOf<String>()
        for(item in 1..10)
        {
            listAreeInteresse.add("Area interesse n: $item")
        }

       // val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listAreeInteresse)

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}