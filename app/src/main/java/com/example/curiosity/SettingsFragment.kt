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
import androidx.core.view.size
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

    var auth: FirebaseAuth = Firebase.auth
    var notificationTimeSelected : String =""
    var areeInteresseSelected : String =""

    var writedByUserNotification : Boolean = false
    public  var writedByUserAreeInteresse : Boolean = false

    var areeInteresseFirebaseList : String? = null


    val listAreeInteresse = arrayListOf<String>()

    //Dal nome alla posizione
    val myMapNomiPosizioni = mutableMapOf<String?,Int?>()
    //Dal nome al fatto che e selezionata
    val myMapAreeInteresse = mutableMapOf<String,Boolean>()


    val mapConversionTempo = mutableMapOf<String,Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)


        return view
    }


    //Lettura dal database firebase - Nodo generale Users
    val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("aree_interesse").addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            if(writedByUserAreeInteresse){
                writedByUserAreeInteresse = false
                return;
            }

            if(writedByUserNotification){
                writedByUserNotification = false
                return;
            }



            areeInteresseFirebaseList = dataSnapshot.value.toString()

            Log.i("SettingsFragment","l'utente  sembrea essere interessato a" + areeInteresseFirebaseList)

            areeInteresseFirebaseList?.split(", ")?.forEach(){
                Log.i("SettingsFragment", it)

                if(it.equals("") || it == null) return

                myMapAreeInteresse.put(it, true)

                if(myMapNomiPosizioni.size == 0)
                {
                    myMapNomiPosizioni.put("Animali", 0)
                    myMapNomiPosizioni.put("Cibo", 1)
                    myMapNomiPosizioni.put("Giochi", 2)
                    myMapNomiPosizioni.put("HarryPotter", 3)
                    myMapNomiPosizioni.put("Musica", 4)
                    myMapNomiPosizioni.put("Scienza", 5)
                    myMapNomiPosizioni.put("Spazio", 6)
                    myMapNomiPosizioni.put("Sport", 7)
                    myMapNomiPosizioni.put("Storia", 8)
                }
                list_aree_interesse?.setItemChecked(myMapNomiPosizioni[it]!!,true)

            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w("SettingsFragment", "Failed to read value.", error.toException())
        }
    })


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listTempoNotifiche = arrayListOf<String>("1 minuto","2 minuti","5 minuti","10 minuti","15 minuti","30 minuti",
            "45 minuti","1 ora","2 ore","5 ore","8 ore","12 ore","24 ore")

        Log.i("settingsFragment"," vado a fare push lista tempo")
        mapConversionTempo.put(listTempoNotifiche[0],0)
        mapConversionTempo.put(listTempoNotifiche[1],1)
        mapConversionTempo.put(listTempoNotifiche[2],2)
        mapConversionTempo.put(listTempoNotifiche[3],3)
        mapConversionTempo.put(listTempoNotifiche[4],4)
        mapConversionTempo.put(listTempoNotifiche[5],5)
        mapConversionTempo.put(listTempoNotifiche[6],6)
        mapConversionTempo.put(listTempoNotifiche[7],7)
        mapConversionTempo.put(listTempoNotifiche[8],8)
        mapConversionTempo.put(listTempoNotifiche[9],9)
        mapConversionTempo.put(listTempoNotifiche[10],10)
        mapConversionTempo.put(listTempoNotifiche[11],11)
        mapConversionTempo.put(listTempoNotifiche[12],12)

        Log.i("settingsFragment"," vado a fare push lista aree interesse")
        //Purtroppo l'app deve conoscere in anticipo le aree di interesse
        if(myMapNomiPosizioni.size == 0) {
            myMapNomiPosizioni.put("Animali", 0)
            myMapNomiPosizioni.put("Cibo", 1)
            myMapNomiPosizioni.put("Giochi", 2)
            myMapNomiPosizioni.put("HarryPotter", 3)
            myMapNomiPosizioni.put("Musica", 4)
            myMapNomiPosizioni.put("Scienza", 5)
            myMapNomiPosizioni.put("Spazio", 6)
            myMapNomiPosizioni.put("Sport", 7)
            myMapNomiPosizioni.put("Storia", 8)
        }

        val adapterTempo = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_single_choice, listTempoNotifiche)
        list_tempo.adapter = adapterTempo

        //Aggiunge ogni nodo alla lista
        for (nome in myMapNomiPosizioni.keys) {
            listAreeInteresse.add(nome.toString())
            Log.i("settingsFragment","popolo mymapConversion")

            val adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_checked,
                listAreeInteresse
            )
            list_aree_interesse.adapter = adapter
        }

        areeInteresseFirebaseList?.split(", ")?.forEach(){
            Log.i("SettingsFragment", it + " sto riempiendo")

            if(it.equals("") || it == null) return

            list_aree_interesse.smoothScrollToPosition(8)

            list_aree_interesse.setItemChecked(myMapNomiPosizioni[it]!!, true)

        }



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

            writedByUserNotification = true
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
                    "Hai scelto di ricevere notifiche ogni: \n" + notificationTimeSelected
                    +"Su le seguenti aree di interesse: \n"+ areeInteresseSelected)
                .setPositiveButton("Okay"){_,_ ->}
                .show()
        }


        //Lettura dal database firebase - Nodo generale Users
        val addValueEventListener5 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("tempoMinutiNotifica").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                notificationTimeSelected = dataSnapshot.value.toString()
                Log.i("SettingsFragment","l'utente vuole ricevere notifiche ogni" + notificationTimeSelected)
                list_tempo?.setItemChecked(mapConversionTempo[notificationTimeSelected]!!,true)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("SettingsFragment", "Failed to read value.", error.toException())
            }
        })






    }
}