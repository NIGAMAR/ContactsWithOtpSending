package com.nigamar.contactswithotpsending.ui.home

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.internals.SMS_DELIVERED
import com.nigamar.contactswithotpsending.internals.SMS_SENT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var smsSentReceiver:BroadcastReceiver
    private lateinit var smsDeliveredReceiver:BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        setUpBottomNavigation(navController)
        setUpSideNavigation(navController)
        setUpActionBar(navController)
    }

    override fun onStart() {
        super.onStart()
        registerReceivers()
    }

    private fun registerReceivers() {
        smsSentReceiver= object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                when(resultCode){
                    Activity.RESULT_OK -> Toast.makeText(this@MainActivity," Otp Sent Successfully ",Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE-> println("Generic failure")
                    SmsManager.RESULT_ERROR_NO_SERVICE->println(" No service ")
                    SmsManager.RESULT_ERROR_RADIO_OFF->println("Radio off")
                }
            }
        }
        smsDeliveredReceiver= object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                when(resultCode){
                    Activity.RESULT_OK-> Toast.makeText(this@MainActivity," Otp Delivered ",Toast.LENGTH_SHORT).show()
                    Activity.RESULT_CANCELED-> println(" sms not delivered ")
                }
            }
        }
        registerReceiver(smsSentReceiver, IntentFilter(SMS_SENT))
        registerReceiver(smsDeliveredReceiver, IntentFilter(SMS_DELIVERED))
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

    override fun onStop() {
        super.onStop()
        unregisterReceivers()
    }

    private fun unregisterReceivers() {
        unregisterReceiver(smsSentReceiver)
        unregisterReceiver(smsDeliveredReceiver)
    }
}