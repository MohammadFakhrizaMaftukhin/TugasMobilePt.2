package com.example.multiplerv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.multiplerv.databinding.ItemHeaderBinding
import com.example.multiplerv.databinding.ItemProductBinding

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_PRODUCT = 1
    }

    interface OnProductChangeListener {
        fun onProductChanged(position: Int)
    }

    private var onProductChangeListener: OnProductChangeListener? = null

    fun setOnProductChangeListener(listener: OnProductChangeListener) {
        onProductChangeListener = listener
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvHeaderTitle.text = "Menu Minuman"
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, position: Int) {
            binding.apply {
                tvProductName.text = product.name
                tvProductPrice.text = product.price.toString()
                tvProductCount.text = product.count.toString()
                imageView2.setImageResource(product.imageResId)

                // Set favorite button state
                updateFavoriteButtonState(product.isFavorite)

                if (product.count > 0) {
                    buttonAdd.visibility = View.GONE
                    groupButtomView.visibility = View.VISIBLE
                } else {
                    buttonAdd.visibility = View.VISIBLE
                    groupButtomView.visibility = View.GONE
                }

                buttonPlusOne.setOnClickListener {
                    product.count++
                    onProductChangeListener?.onProductChanged(position)
                }

                buttonMinusOne.setOnClickListener {
                    if (product.count > 0)
                        product.count--
                    onProductChangeListener?.onProductChanged(position)
                }

                buttonAdd.setOnClickListener {
                    product.count++
                    onProductChangeListener?.onProductChanged(position)
                }

                buttonFavorite.setOnClickListener {
                    product.isFavorite = !product.isFavorite
                    updateFavoriteButtonState(product.isFavorite)
                    onProductChangeListener?.onProductChanged(position)
                }
            }
        }

        private fun updateFavoriteButtonState(isFavorite: Boolean) {
            binding.buttonFavorite.setColorFilter(
                if (isFavorite) {
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark)
                } else {
                    ContextCompat.getColor(binding.root.context, android.R.color.darker_gray)
                }
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_PRODUCT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = products.size + 1 // +1 for header

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind()
            is ProductViewHolder -> holder.bind(products[position - 1], position - 1)
        }
    }
}