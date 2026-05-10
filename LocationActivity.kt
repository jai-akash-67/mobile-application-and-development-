package com.gcollector

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gcollector.utils.PrefHelper

class LocationActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST = 1001
    private lateinit var tvMap: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        tvMap = findViewById(R.id.tvMap)
        val btnDetect   = findViewById<Button>(R.id.btnDetect)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        val btnBack     = findViewById<Button>(R.id.btnBack)

        btnDetect.setOnClickListener { detectLocation() }

        btnContinue.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun detectLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
            return
        }
        mockLocation()
    }

    private fun mockLocation() {
        // Simulate Chennai coordinates with small random offset
        val lat = 13.0 + (Math.random() * 0.1)
        val lng = 80.0 + (Math.random() * 0.1)
        val latStr = String.format("%.5f", lat)
        val lngStr = String.format("%.5f", lng)

        tvMap.text = "📍 Lat: $latStr\nLng: $lngStr"
        PrefHelper.saveLocation(this, lat, lng)
        Toast.makeText(this, "Location detected!", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mockLocation()
        } else {
            Toast.makeText(this, "Permission denied — using default location", Toast.LENGTH_SHORT).show()
        }
    }
}
