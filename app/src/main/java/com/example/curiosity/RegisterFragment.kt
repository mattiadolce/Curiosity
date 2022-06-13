package com.example.curiosity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.text.SimpleDateFormat
import java.util.*


class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

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
                    var user : User = User(tf_email.text.toString(), tf_password.text.toString() ,sdf.format(Date()))

                    CuriosityUsersHelper.setUsersItem(user.key,user)

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

    private fun getToDoEventListener(): ChildEventListener {
        val listener = object: ChildEventListener {

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val item = p0.getValue(User::class.java)
                data.add(item!!)

            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Log.d("MainActivity","ciao")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }


            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }


        }

        return listener
    }
}
