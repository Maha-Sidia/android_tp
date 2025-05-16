package com.example.epigl4tp5x

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.epigl4tp5x.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var activeSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access txtCapteurs through binding
        val txtCapteurs = binding.txtCapteurs

        setSupportActionBar(binding.appBarMain.toolbar)

        // FAB click listener
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Drawer and navigation setup
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        // Initialize sensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Get reference to txtCapteurs
        val txtCapteurs = binding.txtCapteurs

        when (item.itemId) {
            R.id.all_sensors -> {
                txtCapteurs.text = "Affichage de tous les capteurs disponibles..."
                showAllSensors()
            }
            R.id.accelerometer -> {
                txtCapteurs.text = "Capteur sélectionné : Accéléromètre"
            }
            R.id.gyroscope -> {
                txtCapteurs.text = "Capteur sélectionné : Gyroscope"
            }
            R.id.nav_magnetometer -> {
                txtCapteurs.text = "Capteur sélectionné : Magnétomètre"
            }
            R.id.nav_proximity -> {
                txtCapteurs.text = "Capteur sélectionné : Proximité"
            }
            R.id.nav_photometer -> {
                txtCapteurs.text = "Capteur sélectionné : Capteur de luminosité"
            }
        }

        // Close navigation drawer
        val drawerLayout: DrawerLayout = binding.drawerLayout
        drawerLayout.closeDrawers()
        return true
    }

    private fun showAllSensors() {
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sensorInfo = StringBuilder("Liste des capteurs disponibles :\n\n")
        for (sensor in sensorList) {
            sensorInfo.append("- ${sensor.name} (${sensor.type})\n")
        }
        binding.txtCapteurs.text = sensorInfo.toString()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val sensor = event.sensor
        val values = event.values
        val result = when (sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> "Accéléromètre\nX: ${values[0]} m/s²\nY: ${values[1]} m/s²\nZ: ${values[2]} m/s²"
            Sensor.TYPE_GYROSCOPE -> "Gyroscope\nX: ${values[0]} rad/s\nY: ${values[1]} rad/s\nZ: ${values[2]} rad/s"
            Sensor.TYPE_MAGNETIC_FIELD -> "Magnétomètre\nX: ${values[0]} µT\nY: ${values[1]} µT\nZ: ${values[2]} µT"
            Sensor.TYPE_PROXIMITY -> "Proximité\nDistance: ${values[0]} cm"
            Sensor.TYPE_LIGHT -> "Photomètre\nLuminosité: ${values[0]} lux"
            else -> "Capteur inconnu"
        }
        binding.txtCapteurs.text = result
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used for now
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        activeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}
