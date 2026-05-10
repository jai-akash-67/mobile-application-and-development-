package com.gcollector

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.utils.BarChartView
import com.gcollector.utils.PrefHelper
import java.text.SimpleDateFormat
import java.util.*

class AnalyticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        val chart = findViewById<BarChartView>(R.id.barChart)
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }

        // Build monthly totals from history
        val history = PrefHelper.getHistory(this)
        val sdf = SimpleDateFormat("MMM", Locale.getDefault())
        val monthTotals = LinkedHashMap<String, Double>()

        history.forEach { item ->
            runCatching {
                val isoFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val date = isoFmt.parse(item.date.take(19)) ?: return@runCatching
                val month = sdf.format(date)
                monthTotals[month] = (monthTotals[month] ?: 0.0) + item.amt
            }
        }

        if (monthTotals.isNotEmpty()) {
            chart.values = monthTotals.values.map { it.toFloat() }
            chart.labels = monthTotals.keys.toList()
        }
        // else keeps default demo values
    }
}
