package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.utils.PrefHelper

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvName    = findViewById<TextView>(R.id.tvPName)
        val tvEmail   = findViewById<TextView>(R.id.tvPEmail)
        val tvPhone   = findViewById<TextView>(R.id.tvPPhone)
        val tvAddress = findViewById<TextView>(R.id.tvPAddress)

        val btnEdit   = findViewById<Button>(R.id.btnEdit)
        val btnTheme  = findViewById<Button>(R.id.btnTheme)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnBack   = findViewById<Button>(R.id.btnBack)

        PrefHelper.getProfile(this)?.let { p ->
            tvName.text    = p.name
            tvEmail.text   = p.email
            tvPhone.text   = p.phone
            tvAddress.text = p.address
        }

        btnEdit.setOnClickListener {
            startActivity(Intent(this, DetailsActivity::class.java))
        }

        btnTheme.setOnClickListener {
            val isLight = PrefHelper.isLightTheme(this)
            PrefHelper.setLightTheme(this, !isLight)
            // Restart to apply theme
            recreate()
        }

        btnLogout.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }

        btnBack.setOnClickListener { finish() }
    }
}
