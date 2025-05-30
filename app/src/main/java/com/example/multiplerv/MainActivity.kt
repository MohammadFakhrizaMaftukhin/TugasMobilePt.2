package com.example.multiplerv

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ProductAdapter.OnProductChangeListener {
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val productList = arrayListOf(
            Product("Jus", 0, 25000),
            Product("Jus", 0, 30000),
            Product("Jus", 0, 31000),
            Product("Jus", 0, 32000),
            Product("Jus", 0, 33000),
            Product("Jus", 0, 34000),
            Product("Jus", 0, 35000),
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val manager = GridLayoutManager(this, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1 // Header takes full width
            }
        }
        recyclerView.layoutManager = manager
        adapter = ProductAdapter(productList)
        adapter.setOnProductChangeListener(this)
        recyclerView.adapter = adapter
    }

    override fun onProductChanged(position: Int) {
        adapter.notifyItemChanged(position + 1) // +1 because of header
    }
}