package com.example.tp33

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.tp33.databinding.ActivityMainBinding
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
        }
    }
override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_call_urgence -> {
                val phoneNumber =resources.getString(R.string.emergency_number)
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:1111"))
                startActivity(intent)
                true
            }
            R.id.item_call_normal -> {
                val intent = Intent(this, CallActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item_sms_urgence -> {
                val smsManager:SmsManager=SmsManager.getDefault()
                smsManager.sendTextMessage("1111", null, "au secours", null, null)
                true
            }
            R.id.item_quit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}