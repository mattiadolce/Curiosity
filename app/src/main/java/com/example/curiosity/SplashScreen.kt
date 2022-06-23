package com.example.curiosity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_generator.*


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    var areeInteresseFirebaseList : String? = null
    var auth: FirebaseAuth = Firebase.auth
    var listaAree : ArrayList<String>? = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = Firebase.auth

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //Lettura dal database firebase - Nodo generale Users
       /* val addValueEventListener2 = CuriosityUsersHelper.refUsers.child(auth.currentUser?.email?.replace(".","").toString()).child("aree_interesse").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                areeInteresseFirebaseList = dataSnapshot.value.toString()

                Log.i("SplashScreen","l'utente  sembrea essere interessato a" + areeInteresseFirebaseList)

                areeInteresseFirebaseList?.split(", ")?.forEach(){
                    Log.i("SettingsFragment", it)

                    if(it.equals("") || it == null) return

                    for(i in 0..9)
                    {
                        Log.i("Ascolto",it+"$i")
                        CuriosityUsersHelper.listen(it,it+"$i")
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("GeneratorFragment", "Failed to read value.", error.toException())
            }
        })*/

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.

         // simula caricamento
        val user =auth.currentUser


        val authStateListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                //manda a view del login
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                startActivity(intent)

                finish();
            }
            else
            {
                //auth.signOut()

                CuriosityUsersHelper.initialize(auth.currentUser?.email?.replace(".","").toString())


                val intent = Intent(this, CuriosityActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                startActivity(intent)

                finish();
            }
        }
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)

    }
}