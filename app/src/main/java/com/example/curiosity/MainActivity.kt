package com.example.curiosity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())

        val listAreeInteresse = arrayListOf<String>()
        for(item in 1..10)
        {
            listAreeInteresse.add("Area interesse n: $item")
        }

        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listAreeInteresse)


        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)
    }


}