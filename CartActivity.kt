package com.gcollector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gcollector.adapters.ScrapAdapter
import com.gcollector.models.ScrapItem

class CartActivity : AppCompatActivity() {

    private lateinit var tvTotal: TextView
    private val scrapList = mutableListOf(
        ScrapItem("Copper",          969.0),
        ScrapItem("Brass",           598.5),
        ScrapItem("Aluminum",        214.25),
        ScrapItem("Stainless Steel",  53.5),
        ScrapItem("Iron",             27.3),
        ScrapItem("Tin",              18.3),
        ScrapItem("Battery",          75.0),
        ScrapItem("TV",              170.0),
        ScrapItem("Laptop",          300.0),
        ScrapItem("Newspaper",        11.0),
        ScrapItem("Books",            12.0),
        ScrapItem("Cardboard",         5.0),
        ScrapItem("Plastic",          10.0),
        ScrapItem("Glass Bottle",      2.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        tvTotal = findViewById(R.id.tvTotal)

        val rv = findViewById<RecyclerView>(R.id.rvScrap)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ScrapAdapter(scrapList) { updateTotal() }

        findViewById<Button>(R.id.btnInvoice).setOnClickListener {
            val intent = Intent(this, InvoiceActivity::class.java)
            intent.putExtra("total", getTotal())
            intent.putStringArrayListExtra("names", ArrayList(scrapList
                .filter { it.weight > 0 }.map { it.name }))
            intent.putExtra("amounts", scrapList
                .filter { it.weight > 0 }.map { it.amount }.toDoubleArray())
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private fun getTotal(): Double = scrapList.sumOf { it.amount }

    private fun updateTotal() {
        tvTotal.text = "Total ₹ %.2f".format(getTotal())
    }
}
