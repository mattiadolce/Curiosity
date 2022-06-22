package com.example.curiosity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.curiosity.CuriosityUsersHelper.Companion.listaAreeInteresse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_generator.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class GeneratorFragment : Fragment() {
    var areeInteresseFirebaseList : String? = null
    var auth: FirebaseAuth = Firebase.auth
    var listaAree : ArrayList<String>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Auto generator"


        if(listaAreeInteresse.size != 0)
        {
            val rnds = (0..9).random()
            val rnd2 = (0..listaAreeInteresse.size - 1).random()
            Log.i("qua", CuriosityUsersHelper.listaAreeInteresse[rnd2])
            Log.i("qua",
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()
            )

            tf_area_interesse.text = CuriosityUsersHelper.listaAreeInteresse[rnd2]
            tf_curiosita.text =
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()
        }




        btn_si.setOnClickListener() {
            val rnds = (0..9).random()
            val rnd2 = (0..listaAreeInteresse.size - 1).random()
            Log.i("qua", CuriosityUsersHelper.listaAreeInteresse[rnd2])
            Log.i("qua",
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()
            )

            tf_area_interesse.text = CuriosityUsersHelper.listaAreeInteresse[rnd2]
            tf_curiosita.text =
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()

            val email = auth.currentUser?.email?.replace(".","")

            var nTotali = CuriosityUsersHelper.totalAnswer
            var nCorrette = CuriosityUsersHelper.totalCorrectAnswer
            nCorrette += 1

            nTotali += 1

            CuriosityUsersHelper.updateUserItem(email.toString(),"correctAnswer",nCorrette.toString())
            CuriosityUsersHelper.updateUserItem(email.toString(),"totalAnswer",nTotali.toString())

        }

        btn_no.setOnClickListener() {
            val rnds = (0..9).random()
            val rnd2 = (0..listaAreeInteresse.size - 1).random()
            Log.i("qua", CuriosityUsersHelper.listaAreeInteresse[rnd2])
            Log.i("qua",
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()
            )

            tf_area_interesse.text = CuriosityUsersHelper.listaAreeInteresse[rnd2]
            tf_curiosita.text =
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()

            var nTotali = CuriosityUsersHelper.totalAnswer

            nTotali += 1
            val email = auth.currentUser?.email?.replace(".","")

            CuriosityUsersHelper.updateUserItem(email.toString(),"totalAnswer",nTotali.toString())
        }

        //Lettura dal database firebase - Nodo generale Users

    }
}