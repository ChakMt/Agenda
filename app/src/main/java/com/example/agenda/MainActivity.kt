package com.example.agenda

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.agenda.databinding.ActivityMainBinding
import com.example.agenda.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity(),OnNavigationItemSelectedListener {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navDrawer:DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navDrawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,navDrawer,toolbar,
            R.string.app_name, R.string.app_name)

        navDrawer.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null){

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                FirstFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)

        }



    }



    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId){
            R.id.nav_home-> {
                /*supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                MainFragment()).commit()
                }
                 */
                intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("variable","valor")
                startActivity(intent)
                finish()
            }
            R.id.nav_tarea->{supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                FirstFragment()).commit()}
            R.id.nav_contacto->{supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                ContactoFragment()).commit()}

        }
        navDrawer.closeDrawer(navDrawer)

        return true

        }

    }