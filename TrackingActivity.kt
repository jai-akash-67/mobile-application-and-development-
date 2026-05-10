package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrackingActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private var distance = 10
    private lateinit var tvDistance: TextView
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)

        tvDistance = findViewById(R.id.tvDistance)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            runnable?.let { handler.removeCallbacks(it) }
            finish()
        }

        startCountdown()
    }

    private fun startCountdown() {
        runnable = object : Runnable {
            override fun run() {
                if (distance > 0) {
                    tvDistance.text = "Distance: $distance km"
                    distance--
                    handler.postDelayed(this, 1000)
                } else {
                    tvDistance.text = "Collector Arrived! 🎉"
                    handler.postDelayed({
                        startActivity(Intent(this@TrackingActivity, CartActivity::class.java))
                        finish()
                    }, 1000)
                }
            }
        }
        handler.post(runnable!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        runnable?.let { handler.removeCallbacks(it) }
    }
}
