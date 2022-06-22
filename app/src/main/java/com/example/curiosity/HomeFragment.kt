package com.example.curiosity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import java.math.BigDecimal

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


    var correctAnswerReaded :  Boolean = false
    var totalAnswerReaded :  Boolean = false

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

        (activity as AppCompatActivity).supportActionBar?.title = "Curiosity"

        fun calculatePercentage() {

            if(correctAnswerReaded && totalAnswerReaded)
            {

                val corrette = tv_risposte_corrette.text
                val numeroCorrette = corrette.split(" ").first().toFloat()

                val totali = tv_risposte_totali.text
                val numeroTotali = totali.split(" ").first().toFloat()

                var percentage : Float = (numeroCorrette)  * (100F)

                percentage /= numeroTotali


                Log.i("ttttttt",percentage.toString())


                //percent lavora con range da (0..0.99999)
                //percentage = 35.666
                progressBar.percent = (percentage / 100F)
            }

        }

        //Lettura dal database firebase - Nodo generale Users
        val addValueEventListener3 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString())
            .child("correctAnswer").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(!dataSnapshot?.value.toString().equals(""))
                    {
                        //la fix ad mettere anche dalle altre parti - se non e la sua pagina allora e nullo
                        if(tv_risposte_corrette!= null) {
                            correctAnswerReaded = true
                            tv_risposte_corrette.text = dataSnapshot!!.value.toString() + " risposte corrette"
                            calculatePercentage()

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("SettingsFragment", "Failed to read value.", error.toException())
                }
            })


        //Lettura dal database firebase - Nodo generale Users
        val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("totalAnswer").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot?.value.toString().equals("")) {
                    if(tv_risposte_totali!=null) {
                        totalAnswerReaded = true
                        tv_risposte_totali.text = dataSnapshot.value.toString() + " risposte totali"
                        calculatePercentage()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("SettingsFragment", "Failed to read value.", error.toException())
            }
        })


    }




}