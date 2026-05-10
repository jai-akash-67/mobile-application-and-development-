package com.gcollector

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gcollector.utils.PrefHelper
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private var currentDate = Calendar.getInstance()
    private lateinit var tvMonthYear: TextView
    private lateinit var glCalendar: GridLayout

    private val monthNames = arrayOf(
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvMonthYear = findViewById(R.id.tvMonthYear)
        glCalendar  = findViewById(R.id.glCalendar)

        findViewById<Button>(R.id.btnPrev).setOnClickListener {
            currentDate.add(Calendar.MONTH, -1)
            loadCalendar()
        }
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            currentDate.add(Calendar.MONTH, 1)
            loadCalendar()
        }
        findViewById<Button>(R.id.btnAnalytics).setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }

        loadCalendar()
    }

    private fun loadCalendar() {
        glCalendar.removeAllViews()

        val year  = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)

        tvMonthYear.text = "${monthNames[month]} $year"

        val firstDay = Calendar.getInstance().apply {
            set(year, month, 1)
        }.get(Calendar.DAY_OF_WEEK) - 1   // 0=Sun

        val daysInMonth = Calendar.getInstance().apply {
            set(year, month + 1, 0)
        }.get(Calendar.DAY_OF_MONTH)

        // Collect dates with history
        val history = PrefHelper.getHistory(this)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val activeDays = history.mapNotNull {
            runCatching { sdf.parse(it.date.take(10)) }.getOrNull()
        }.mapNotNull { d ->
            Calendar.getInstance().apply { time = d }.let {
                Triple(it.get(Calendar.YEAR), it.get(Calendar.MONTH), it.get(Calendar.DAY_OF_MONTH))
            }
        }.toSet()

        val dp8 = (8 * resources.displayMetrics.density).toInt()

        // Empty cells before 1st
        repeat(firstDay) {
            glCalendar.addView(TextView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0; height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
            })
        }

        for (day in 1..daysInMonth) {
            val hasData = activeDays.contains(Triple(year, month, day))
            val tv = TextView(this).apply {
                text = day.toString()
                textSize = 12f
                setPadding(dp8, dp8, dp8, dp8)
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setTextColor(Color.WHITE)
                setBackgroundColor(if (hasData) Color.parseColor("#2e7d32") else Color.parseColor("#222222"))
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0; height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(2, 2, 2, 2)
                }
            }
            glCalendar.addView(tv)
        }
    }
}
