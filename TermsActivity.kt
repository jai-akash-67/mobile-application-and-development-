package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        findViewById<Button>(R.id.btnAccept).setOnClickListener {
            startActivity(Intent(this, DetailsActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            Toast.makeText(this, "You must accept to continue", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }
}
