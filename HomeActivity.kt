package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.utils.PrefHelper

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvCoins    = findViewById<TextView>(R.id.tvCoins)
        val tvLocation = findViewById<TextView>(R.id.tvLocation)

        val btnRequest   = findViewById<Button>(R.id.btnRequest)
        val btnDashboard = findViewById<Button>(R.id.btnDashboard)
        val btnCommunity = findViewById<Button>(R.id.btnCommunity)
        val btnProfile   = findViewById<Button>(R.id.btnProfile)
        val btnHistory   = findViewById<Button>(R.id.btnHistory)

        btnRequest.setOnClickListener {
            startActivity(Intent(this, TrackingActivity::class.java))
        }
        btnDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        btnCommunity.setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
        }
        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        // Update coins & location each time screen is shown
        tvCoins.text = PrefHelper.getCoins(this).toString()
        val (lat, lng) = PrefHelper.getLocation(this)
        tvLocation.text = if (lat == "N/A") "" else "📍 $lat, $lng"
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.tvCoins).text = PrefHelper.getCoins(this).toString()
    }
}
