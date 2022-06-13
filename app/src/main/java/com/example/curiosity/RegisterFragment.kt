package com.example.curiosity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.android.synthetic.main.fragment_settings.*


class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_register, container, false)

        auth = Firebase.auth

        //Viene gestito il click sul link di testo: Gia registrato?Accedi.
        view.tvRegister.setOnClickListener(){
            //Il fragmentContainer viene sostituito con dal RegisterFragment al LoginFragment
            val transaction = parentFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView,LoginFragment())
            transaction.commit()
        }

        view.buttonRegister.setOnClickListener{
            auth.createUserWithEmailAndPassword(tf_email.text.toString(), tf_password.text.toString()).addOnCompleteListener{
                if(it.isSuccessful){
                    Log.i("MainActivity", "Registrazione ok")

                    CuriosityUsersHelper.setUsersItem(tf_email.text.toString())

                    val intent = Intent(activity, CuriosityActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.putExtra("keyIdentifier", 1)
                    startActivity(intent)

                    activity?.finish()

                }else{
                    tv_errorLogin.text = it.exception?.message.toString()
                    tv_errorLogin.isVisible = true

                    //rimane li e mostra messaggio errore
                }
            }
        }

        // Return the fragment view/layout
        return view
    }
}