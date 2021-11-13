package com.cs389team4.needtofeed.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs389team4.needtofeed.R

class CheckoutAdapter : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {
    private var itemCount: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_checkout_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemQuantityTextView: TextView = itemView.findViewById(R.id.checkout_item_quantity)
        private val itemNameTextView: TextView = itemView.findViewById(R.id.checkout_item_name)
        private val itemPriceTextView: TextView = itemView.findViewById(R.id.checkout_item_price)

        fun bind(itemCount: Int) {

        }
    }
}
