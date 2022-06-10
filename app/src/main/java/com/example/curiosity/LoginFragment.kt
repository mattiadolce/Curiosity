package com.example.curiosity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.tf_password
import kotlinx.android.synthetic.main.fragment_login.tv_errorLogin
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_login, container, false)

        auth = Firebase.auth

        //Viene gestito il click sul link di testo: Non ancora registrato?Registrati.
        view.tv_register.setOnClickListener(){

            //Il fragmentContainer viene sostituito con dal LoginFragment al RegisterFragment
            val transaction = parentFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView,RegisterFragment())
            transaction.commit()
        }

        //Viene gestito il click sul bottone di login
        view.buttonSignUp.setOnClickListener(){
            auth.signInWithEmailAndPassword(tf_user_name.text.toString(), tf_password.text.toString()).addOnCompleteListener{
                if(it.isSuccessful){
                    //Se la login va a buon fine allora aggiungiamo nel backstack la curiosityActivity
                    val intent = Intent(activity, CuriosityActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                    intent.putExtra("keyIdentifier", 1)
                    startActivity(intent)
                    Log.i("MainActivity", "Login ok")

                    activity?.finish()
                }else{
                    //mostra messaggio d'errore
                    tv_errorLogin.text = it.exception?.message.toString()
                    tv_errorLogin.isVisible = true

                    //sposta a view della registrazione
                    Log.i("MainActivity", "Login fallito")
                }
            }
        }

        // Return the fragment view/layout
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }
}