package com.example.rentalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Welcome : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{
    private lateinit var fragmentManager: Fragment

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }

    }

    private fun onBackPressedMethod() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            finish()
        }

    }

    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.navigation_drawer)
        val header = navigationView.getHeaderView(0)

        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        /// Default Navigation bar Tab Selected
        replaceFragment(ProfileFragment())
        navigationView.setCheckedItem(R.id.tvProfile)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tvHome -> replaceFragment(HomeFragment())
                R.id.tvTenant -> replaceFragment(TenantFragment())
                R.id.tvPayment-> replaceFragment(PaymentFragment())
                R.id.tvChats -> replaceFragment(ChatFragment())

            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tvProfile -> {
                replaceFragment(ProfileFragment())
            }
            R.id.tvTransactions -> {
                replaceFragment(TransactionFragment())
            }
            R.id.tvRefer -> {
                replaceFragment(ReferFragment())
            }
            R.id.tvSettings-> {
                replaceFragment(SettingFragment())
            }
            R.id.tvHelp-> {
                replaceFragment(HelpFragment())
            }
            R.id.tvLog-> {
                replaceFragment(LogOutFragment())
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}