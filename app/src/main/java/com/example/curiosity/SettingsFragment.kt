package com.example.curiosity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment() : Fragment() {

    lateinit var listView :  ListView

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)


        view.btn_conferma.setOnClickListener{
            AlertDialog.Builder(context)
                .setTitle("Settings confermate")
                .setMessage(
                    "Hai scelto di ricevere notifiche ogni: \n"+list_tempo.selectedItem
                )
                .setPositiveButton("Okay"){_,_ ->}
                .show()
        }

        return view
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAreeInteresse = arrayListOf<String>()
        for(item in 1..10)
        {
            listAreeInteresse.add("Area interesse n: $item")
        }

        val adapter =  ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_checked,listAreeInteresse)

        list_aree_interesse.adapter = adapter


        val listTempoNotifiche = arrayListOf<String>()

        listTempoNotifiche.add("1 minuto")
        listTempoNotifiche.add("2 minuti")
        listTempoNotifiche.add("5 minuti")
        listTempoNotifiche.add("10 minuti")
        listTempoNotifiche.add("15 minuti")
        listTempoNotifiche.add("30 minuti")
        listTempoNotifiche.add("45 minuti")
        listTempoNotifiche.add("1 ora")
        listTempoNotifiche.add("2 ore")
        listTempoNotifiche.add("5 ore")
        listTempoNotifiche.add("8 ore")
        listTempoNotifiche.add("12 ore")
        listTempoNotifiche.add("24 ore")


        /*
        for(item in 2..59){
            listTempoNotifiche.add("$item minuti")
        }
        listTempoNotifiche.add("1 ora")
        for(item in 2..12){
            listTempoNotifiche.add("$item ore")
        }
         */

        val adapterTempo = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_single_choice, listTempoNotifiche)
        list_tempo.adapter = adapterTempo

    }
}