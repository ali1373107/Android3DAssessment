package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import android.view.Menu
import android.view.MenuInflater
import android.content.Intent
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.OverlayItem

class MainActivity : AppCompatActivity(), LocationListener {
    lateinit var map1: MapView
    var lon = 0.0
    var lat = 0.0
    lateinit var overlay: ItemizedIconOverlay<OverlayItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main)
        map1 = findViewById<MapView>(R.id.map1)
        map1.controller.setZoom(16.0)
        map1.controller.setCenter(GeoPoint(51.05, -0.72))
        requestPermissions()
        overlay = ItemizedIconOverlay(this, arrayListOf<OverlayItem>(), null)
        map1.overlays.add(overlay)
    }

    val addPoiLauncher = {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.apply {
                    val name = this.getStringExtra("com.example.myapplication.nameValue").toString()
                    val type = this.getStringExtra("com.example.myapplication.typeValue").toString()
                    val description =
                        this.getStringExtra("com.example.myapplication.decValue").toString()
                    val newPointOfInterest =
                        OverlayItem(name, "${type}:${description}", GeoPoint(lat, lon))
                    overlay.addItem(newPointOfInterest)


                }
            }
        }

    }


    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {
            startGps()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGps()
                } else {
                    AlertDialog.Builder(this).setPositiveButton("OK", null)
                        .setMessage("GPS will not work as you have denied access").show()
                }

            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startGps() {
        val mgr = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
    }

    override fun onLocationChanged(loc: Location) {
        map1.controller.setCenter(GeoPoint(loc.latitude, loc.longitude))
        lat = loc.latitude
        lon = loc.longitude

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onCreateOptionMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_POI -> {
                val intent = Intent(this, Addpoi::class.java)
                addPoiLauncher.launch(intent)
                return true
            }
        }
        return false
    }


}