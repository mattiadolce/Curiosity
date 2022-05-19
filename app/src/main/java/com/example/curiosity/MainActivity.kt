package com.example.curiosity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var actionBar : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())

        val drawerLayout : DrawerLayout = findViewById( R.id.drawer_layout)
        val navView  : NavigationView = findViewById( R.id.nav_view)

        actionBar = androidx.appcompat.app.ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close)
        drawerLayout.addDrawerListener(actionBar)
        actionBar.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener(){it->
            Log.i("ciao","ciao")
            true
        }



        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(actionBar.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}