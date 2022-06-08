package com.example.curiosity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_curiosity.view.*
import kotlinx.android.synthetic.main.activity_main.*

class CuriosityActivity : AppCompatActivity() {

    lateinit var actionBar : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curiosity)


        val drawerLayout : DrawerLayout = findViewById( R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START);
        val navView  : NavigationView = findViewById( R.id.nav_view)

        actionBar = androidx.appcompat.app.ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close)
        drawerLayout.addDrawerListener(actionBar)
        actionBar.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener(){it->

            when(it.itemId)
            {
                R.id.nav_home->{
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,HomeFragment())
                    transaction.commit()
                }

                R.id.nav_auto_generator -> {
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,GeneratorFragment())
                    transaction.commit()
                }
                R.id.nav_logout -> {


                    finish()

                    Toast.makeText(applicationContext,"Clicked lOGOUT",  Toast.LENGTH_SHORT).show()

                }
                R.id.nav_settings -> {
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,SettingsFragment())
                    transaction.commit()

                }
            }

            drawerLayout.closeDrawers();
            Log.i("ciao","ciao")



            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(actionBar.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}