package com.example.curiosity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater!!.inflate(R.layout.fragment_login, container, false)

        view.buttonSignUp.setOnClickListener(){

            val transaction = parentFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView,RegisterFragment())
            transaction.commit()

            Log.i("say" , "something")
        }

        // Return the fragment view/layout
        return view


    }



}