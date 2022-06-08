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

    //La actionbar o hamburger icon gestisce il navigation drawer
    lateinit var actionBar : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curiosity)

        //Aggiunta del navigation drawer
        val drawerLayout : DrawerLayout = findViewById( R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START);
        val navView  : NavigationView = findViewById( R.id.nav_view)
        actionBar = androidx.appcompat.app.ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close)
        drawerLayout.addDrawerListener(actionBar)
        actionBar.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Gestione casi in seguito a click su menu del navigation drawer
        navView.setNavigationItemSelectedListener(){it->

            when(it.itemId)
            {
                //Click sulla pagina home
                R.id.nav_home->{
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,HomeFragment())
                    transaction.commit()
                }

                //Click sulla pagina auto generator
                R.id.nav_auto_generator -> {
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,GeneratorFragment())
                    transaction.commit()
                }

                //Click sulla pagina logout ( disconnette )
                R.id.nav_logout -> {
                    finish()
                    Toast.makeText(applicationContext,"Clicked lOGOUT",  Toast.LENGTH_SHORT).show()
                }

                //Click sulla pagina settings
                R.id.nav_settings -> {
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,SettingsFragment())
                    transaction.commit()

                }
            }
            drawerLayout.closeDrawers();
            true
        }

        //Di default il fragment iniziale è quello denonominato Home
        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.container_view,HomeFragment())
        transaction.commit()
    }

    //Click hamburger icon per far uscire il menu o cosiddetto navigation drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Necessari alla classe per aprire / chiudere il nav drawer
        if(actionBar.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}