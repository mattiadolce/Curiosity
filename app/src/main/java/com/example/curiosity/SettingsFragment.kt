package com.example.curiosity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.VIEW_LOG_TAG
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.curiosity.CuriosityUsersHelper.Companion.readUsersItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment() : Fragment() {

    lateinit var listView :  ListView
    private lateinit var auth: FirebaseAuth
    val data = ArrayList<User>()

    var notificationTimeSelected : String =""
    var areeInteresseSelected : String =""

    data class Avanzato(val pos : Int, val checked : Boolean)

    val myMapAreeInteresse = mutableMapOf<String,Boolean>()

    //Dal nome alla posizione
    val myMapConversion = mutableMapOf<String,Int?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        auth = Firebase.auth
        readUsersItems(this.getToDoEventListener())

        //1 aprire la connessione a CuriosityUsers
        //2 cercare il nodo contenente lo user con email auth.currentUser!!.email.toString()
        //3 aggiornare la chiave delle aree di interesse con tutti i valori fleggati nella lista
        Log.i("SettingsFragment",auth.currentUser!!.email.toString())

        return view
    }

    private fun getToDoEventListener(): ValueEventListener {
        val listener = object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.getValue(User::class.java)
                data.add(item!!)
                Log.i("SettingsFragment","update fa girare datachange")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        }

        return listener
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAreeInteresse = arrayListOf<String>()

        //Lettura dal database firebase - Nodo generale Curiosity
        val addValueEventListener = CuriosityUsersHelper.refCuriosity.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //Aggiunge ogni nodo alla lista
                for (nome in dataSnapshot.children) {
                    listAreeInteresse.add(nome.key.toString())
                    myMapConversion.put(nome.key.toString(),listAreeInteresse.size-1)
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
                Log.w("SettingsFragment", "Failed to read value.", error.toException())
            }
        })

        //Lettura dal database firebase - Nodo generale Users
        val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var areeInteresseList : String? = null
                //Aggiunge ogni nodo alla lista
                for (nome in dataSnapshot.children) {

                    if(nome.key.equals("aree_interesse"))
                    {
                        areeInteresseList = nome.value.toString()
                        Log.i("SettingsFragment","l'utente  sembrea essere interessato a" + areeInteresseList)
                    }
                }

                areeInteresseList?.split(", ")?.forEach(){
                    Log.i("SettingsFragment", it)

                    if(it.equals("") || it == null) return

                    list_aree_interesse.setItemChecked(myMapConversion[it]!!,true)

                    myMapAreeInteresse.put(it,true)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("SettingsFragment", "Failed to read value.", error.toException())
            }
        })

        val listTempoNotifiche = arrayListOf<String>("1 minuto","2 minuti","5 minuti","10 minuti","15 minuti","30 minuti",
                                                     "45 minuti","1 ora","2 ore","5 ore","8 ore","12 ore","24 ore")

        val adapterTempo = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_single_choice, listTempoNotifiche)
        list_tempo.adapter = adapterTempo

        //todo leggere da firebase i valori spazio->true/false e schiaffarli nella lista

        list_tempo.setOnItemClickListener(){
                myAdapter, myView, myItemInt, mylng ->
            val itemName = list_tempo.getItemAtPosition(myItemInt) as String

            notificationTimeSelected = list_tempo.getItemAtPosition(myItemInt) as String



            Log.i("SettingsFragment", "il nome dell item selezionato $itemName ")
        }




        list_aree_interesse.setOnItemClickListener(){
                myAdapter, myView, myItemInt, mylng ->
            val itemName = list_aree_interesse.getItemAtPosition(myItemInt) as String
            val ischecked = list_aree_interesse.isItemChecked(myItemInt) as Boolean
            Log.i("SettingsFragment", "il nome dell item selezionato $itemName con valore ${ischecked.toString()}")

            myMapAreeInteresse.put(itemName,ischecked);
        }

        view.btn_conferma.setOnClickListener{

            val email = auth.currentUser?.email?.replace(".","")
            CuriosityUsersHelper.updateUserItem(email.toString(),"tempoMinutiNotifica",notificationTimeSelected)

            areeInteresseSelected = ""
            for(key in myMapAreeInteresse.keys){
                if(myMapAreeInteresse[key] == true) {
                    areeInteresseSelected += "$key, "
                    Log.i("settingsFragment", key)
                    Log.i("settingsFragment", myMapAreeInteresse[key].toString())
                }
            }

            CuriosityUsersHelper.updateUserItem(email.toString(),"aree_interesse",areeInteresseSelected)



            AlertDialog.Builder(context)
                .setTitle("Settings confermate")
                .setMessage(
                    "Hai scelto di ricevere notifiche ogni: \n" + notificationTimeSelected )
                .setPositiveButton("Okay"){_,_ ->}
                .show()
        }
    }
}