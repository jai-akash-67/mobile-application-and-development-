package com.gcollector

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.utils.PrefHelper
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val llHistory = findViewById<LinearLayout>(R.id.llHistory)
        val btnBack   = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val history = PrefHelper.getHistory(this)
        val isoFmt  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dispFmt = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

        if (history.isEmpty()) {
            val tv = TextView(this).apply {
                text = "No history yet. Request a collection!"
                setTextColor(0xFFCCCCCC.toInt())
                textSize = 14f
                setPadding(16, 32, 16, 32)
            }
            llHistory.addView(tv)
            return
        }

        history.reversed().forEach { item ->
            val dateStr = runCatching {
                dispFmt.format(isoFmt.parse(item.date.take(19))!!)
            }.getOrDefault(item.date)

            val dp12 = (12 * resources.displayMetrics.density).toInt()
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundColor(0x1AFFFFFF)
                setPadding(dp12, dp12, dp12, dp12)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 0, 0, dp12) }
                layoutParams = lp
            }

            card.addView(TextView(this).apply {
                text = "📅 $dateStr"
                setTextColor(0xFFFFFFFF.toInt())
                textSize = 13f
            })
            card.addView(TextView(this).apply {
                text = "💰 Total: ₹${"%.2f".format(item.amt)}"
                setTextColor(0xFF00FFCC.toInt())
                textSize = 14f
            })
            card.addView(TextView(this).apply {
                text = "🌿 E-Coins: ${item.eco}"
                setTextColor(0xFF00FFCC.toInt())
                textSize = 13f
            })

            llHistory.addView(card)
        }
    }
}
