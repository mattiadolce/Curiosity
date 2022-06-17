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
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*


class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    var howManyInserted = 0
    val data = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_register, container, false)

        auth = Firebase.auth

        CuriosityUsersHelper.readUsersItems(getToDoEventListener())

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


                    val sdf = SimpleDateFormat("dd-M-yyyy_hh:mm:ss")

                    var user : User = User(tf_email.text.toString(),
                                           tf_password.text.toString() ,
                                  0,
                                0,
                                     0,
                                           "sport,spazio")

                    val email = auth.currentUser?.email?.replace(".","")
                    CuriosityUsersHelper.setUsersItem(email.toString(),user)


                    Log.i("la md5" , CuriosityUsersHelper.md5(tf_email.text.toString()))
                    Log.i("la md5" , CuriosityUsersHelper.md5(tf_email.text.toString()))

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



    private fun getToDoEventListener(): ValueEventListener {
        val listener = object: ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.getValue(User::class.java)
                data.add(item!!)

                howManyInserted = snapshot.childrenCount.toInt()

                Log.i("Guarda qua invece ",howManyInserted.toString())
            }

            override fun onCancelled(p0: DatabaseError) {

            }



        }

        return listener
    }
}
