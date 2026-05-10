package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.utils.PrefHelper

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etUser = findViewById<EditText>(R.id.etNewUsername)
        val etPass = findViewById<EditText>(R.id.etNewPassword)
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnCreate.setOnClickListener {
            val user = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            PrefHelper.addUser(this, user, pass)
            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnBack.setOnClickListener { finish() }
    }
}
