package com.example.curiosity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    var totalCorrectAnswer : Int  = 0
    var totalAnswer : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater!!.inflate(R.layout.fragment_home, container, false)



        auth = Firebase.auth



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Lettura dal database firebase - Nodo generale Users
        val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("totalAnswer").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                totalAnswer = Integer.parseInt(dataSnapshot.value.toString())
                Log.i("SettingsFragment","l'utente  totalAnswer" + totalAnswer)
                textView_risposte_totali.text = totalAnswer.toString() + " risposte totali"
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("SettingsFragment", "Failed to read value.", error.toException())
            }
        })



        //Lettura dal database firebase - Nodo generale Users
        val addValueEventListener3 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("correctAnswer").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                totalCorrectAnswer = Integer.parseInt(dataSnapshot.value.toString())
                Log.i("SettingsFragment","l'utente  corrette" + totalCorrectAnswer)
                textView_risposte_corrette.text = totalCorrectAnswer.toString() + " risposte corrette"
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("SettingsFragment", "Failed to read value.", error.toException())
            }
        })
    }






}