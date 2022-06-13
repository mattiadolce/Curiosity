package com.example.curiosity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment() : Fragment() {

    lateinit var listView :  ListView
    private lateinit var auth: com.google.firebase.auth.FirebaseAuth

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        auth = Firebase.auth

        //1 aprire la connessione a CuriosityUsers
        //2 cercare il nodo contenente lo user con email auth.currentUser!!.email.toString()
        //3 aggiornare la chiave delle aree di interesse con tutti i valori fleggati nella lista
        Log.i("auth",auth.currentUser!!.email.toString())

        view.btn_conferma.setOnClickListener{



            AlertDialog.Builder(context)
                .setTitle("Settings confermate")
                .setMessage(
                    "Hai scelto di ricevere notifiche ogni: \n"+list_tempo.selectedItem.toString()
                )
                .setPositiveButton("Okay"){_,_ ->}
                .show()
        }

        return view
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance("https://curiosity-ca522-default-rtdb.firebaseio.com/")
        val myRef = database.getReference("Curiosity")
        val listAreeInteresse = arrayListOf<String>()

        //Lettura dal database firebase - Nodo generale Curiosity
        val addValueEventListener = myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //Aggiunge ogni nodo alla lista
                for (nome in dataSnapshot.children) {
                    listAreeInteresse.add(nome.key.toString())
                    val adapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_list_item_checked,
                        listAreeInteresse
                    )

                    list_aree_interesse.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })


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