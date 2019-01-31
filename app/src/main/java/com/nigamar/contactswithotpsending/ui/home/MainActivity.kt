package com.nigamar.contactswithotpsending.ui.home

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nigamar.contactswithotpsending.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        setUpBottomNavigation(navController)
        setUpSideNavigation(navController)
        setUpActionBar(navController)
    }

    private fun setUpBottomNavigation(navController: NavController){
        //can be null when we rotate the device so a safe access operator is required
        bottom_nav?.setupWithNavController(navController)
    }

    private fun setUpSideNavigation(navController: NavController){
        //can be null when we rotate the device so a safe access operator is required
        nav_view?.setupWithNavController(navController)
    }

    private fun setUpActionBar(navController: NavController){
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout,findNavController(R.id.nav_host_fragment))
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment);
        val canBePopped = navController.popBackStack()
        if(!canBePopped) {
            val alertDialog = AlertDialog.Builder(this)
                .setMessage(" Are you sure you want to exit ")
                .setPositiveButton("Ok") { dialog, which ->
                    finish()
                }
                .setNegativeButton("Cancel"){ dialog, which ->
                    // nothing to do
                }.create()
            alertDialog.show()
        }
    }
}