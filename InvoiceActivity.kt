package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.models.CartEntry
import com.gcollector.models.HistoryItem
import com.gcollector.utils.PrefHelper
import java.text.SimpleDateFormat
import java.util.*

class InvoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        val total   = intent.getDoubleExtra("total", 0.0)
        val names   = intent.getStringArrayListExtra("names") ?: arrayListOf()
        val amounts = intent.getDoubleArrayExtra("amounts") ?: doubleArrayOf()
        val eCoins  = (total / 2).toInt()

        val llItems  = findViewById<LinearLayout>(R.id.llItems)
        val tvTotal  = findViewById<TextView>(R.id.tvInvoiceTotal)
        val tvCoins  = findViewById<TextView>(R.id.tvInvoiceCoins)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val btnHome  = findViewById<Button>(R.id.btnHome)

        // Build item rows
        names.forEachIndexed { i, name ->
            val tv = TextView(this).apply {
                text = "$name  ₹${"%.2f".format(amounts[i])}"
                setTextColor(0xFFFFFFFF.toInt())
                textSize = 14f
                setPadding(0, 8, 0, 8)
            }
            llItems.addView(tv)
        }

        tvTotal.text  = "Total: ₹${"%.2f".format(total)}"
        tvCoins.text  = "E-Coins Earned: $eCoins 🌿"
        tvStatus.text = "Garbage collected successfully ✔"

        // Persist
        PrefHelper.addCoins(this, eCoins)
        val cartEntries = names.mapIndexed { i, n -> CartEntry(n, amounts[i]) }
        val dateStr = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            .format(Date())
        PrefHelper.addHistory(this, HistoryItem(dateStr, cartEntries, total, eCoins))

        btnHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }
}
