package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.models.UserProfile
import com.gcollector.utils.PrefHelper

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val etName    = findViewById<EditText>(R.id.etName)
        val etEmail   = findViewById<EditText>(R.id.etEmail)
        val etPhone   = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnNext   = findViewById<Button>(R.id.btnNext)
        val btnBack   = findViewById<Button>(R.id.btnBack)

        // Pre-fill if editing
        PrefHelper.getProfile(this)?.let { p ->
            etName.setText(p.name)
            etEmail.setText(p.email)
            etPhone.setText(p.phone)
            etAddress.setText(p.address)
        }

        btnNext.setOnClickListener {
            val name    = etName.text.toString().trim()
            val email   = etEmail.text.toString().trim()
            val phone   = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Name and email are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            PrefHelper.saveProfile(this, UserProfile(name, email, phone, address))
            startActivity(Intent(this, LocationActivity::class.java))
            finish()
        }

        btnBack.setOnClickListener { finish() }
    }
}
