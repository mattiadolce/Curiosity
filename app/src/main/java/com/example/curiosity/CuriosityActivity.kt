package com.example.curiosity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CuriosityActivity : AppCompatActivity() {

    //La actionbar o hamburger icon gestisce il navigation drawer
    lateinit var actionBar : ActionBarDrawerToggle

    private lateinit var auth: FirebaseAuth

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

        auth = Firebase.auth

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
                    auth.signOut()

                    //manda a view del login
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                    startActivity(intent)

                    finish()
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