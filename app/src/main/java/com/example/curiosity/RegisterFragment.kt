package com.example.curiosity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class RegisterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_register, container, false)

        view.tvSignUp.setOnClickListener(){

            val transaction = parentFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView,LoginFragment())
            transaction.commit()

            Log.i("say" , "something")
        }

        // Return the fragment view/layout
        return view
    }

}