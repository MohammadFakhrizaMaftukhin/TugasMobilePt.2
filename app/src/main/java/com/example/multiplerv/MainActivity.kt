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
            Product("Jus Jeruk", 0, 25000, false, R.drawable.orange),
            Product("Jus Mangga", 0, 30000, false, R.drawable.mango),
            Product("Jus Alpukat", 0, 31000, false, R.drawable.avocado),
            Product("Jus Strawberry", 0, 32000, false, R.drawable.strawberry),
            Product("Jus Anggur", 0, 33000, false, R.drawable.grape),
            Product("Jus Semangka", 0, 34000, false, R.drawable.watermelon),
            Product("Jus Melon", 0, 35000, false, R.drawable.melon),
            Product("Jus Jambu", 0, 36000, false, R.drawable.guava)
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