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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.tv_errorLogin
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.ArrayList


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    var datamio : String = "String"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_login, container, false)

        auth = Firebase.auth

        CuriosityUsersHelper.readUsersItems(getToDoEventListener())

        //Viene gestito il click sul link di testo: Non ancora registrato?Registrati.
        view.tv_register.setOnClickListener(){

            //Il fragmentContainer viene sostituito con dal LoginFragment al RegisterFragment
            val transaction = parentFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView,RegisterFragment())
            transaction.commit()
        }

        //Viene gestito il click sul bottone di login
        view.buttonSignUp.setOnClickListener(){
            auth.signInWithEmailAndPassword(tf_email_log.text.toString(), tf_password_log.text.toString()).addOnCompleteListener{
                if(it.isSuccessful){

                    CuriosityUsersHelper.initialize(auth.currentUser?.email?.replace(".","").toString())

                    //Se la login va a buon fine allora aggiungiamo nel backstack la curiosityActivity
                    val intent = Intent(activity, CuriosityActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                    intent.putExtra("keyIdentifier", 1)
                    startActivity(intent)
                    Log.i("LoginFragment", "Login ok")

                    activity?.finish()
                }else{
                    //mostra messaggio d'errore
                    tv_errorLogin.text = it.exception?.message.toString()
                    tv_errorLogin.isVisible = true

                    //sposta a view della registrazione
                    Log.i("LoginFragment", "Login fallito")
                }
            }
        }

        // Return the fragment view/layout
        return view
    }

    private fun getToDoEventListener(): ValueEventListener {
        val listener = object: ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.value
                datamio = item.toString()
                Log.i("datamio",datamio)

            }

            override fun onCancelled(p0: DatabaseError) {

            }



        }

        return listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }
}