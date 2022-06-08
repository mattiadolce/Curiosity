package com.example.curiosity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_login, container, false)

        //Viene gestito il click sul link di testo: Non ancora registrato?Registrati.
        view.tvRegister.setOnClickListener(){

            //Il fragmentContainer viene sostituito con dal LoginFragment al RegisterFragment
            val transaction = parentFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView,RegisterFragment())
            transaction.commit()
        }

        //Viene gestito il click sul bottone di login
        view.buttonSignUp.setOnClickListener(){

            //Se la login va a buon fine allora aggiungiamo nel backstack la curiosityActivity
            val intent = Intent(activity, CuriosityActivity::class.java)
            intent.putExtra("keyIdentifier", 1)
            startActivity(intent)
        }

        // Return the fragment view/layout
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}