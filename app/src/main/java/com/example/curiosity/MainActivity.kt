package com.example.curiosity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())


        auth = Firebase.auth
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onStart() {
        val user =auth.currentUser
        if(user == null){
            //manda a view del login
            loginUser("email", "password") //fare in modo che questi campi vengano letti da view
            //da fare textfield.getText, passwordfield.getText
        }
        super.onStart()
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                Log.i("MainActivity", "Login ok")
            }else{
                //sposta a view della registrazione
                Log.i("MainActivity", "Login fallito")
                //messaggio: se non hai un account registrati
                createUser(email, password)
            }
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                Log.i("MainActivity", "Registrazione ok")
                //fa entrare in spp
            }else{
                Log.i("MainActivity", "Registrazione fallita")
                finish()//non c'è nulla da fare, esci oppure riprova, da decidere
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }




}