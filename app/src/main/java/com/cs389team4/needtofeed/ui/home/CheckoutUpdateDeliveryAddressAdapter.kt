package com.cs389team4.needtofeed.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs389team4.needtofeed.R
import com.google.gson.JsonObject

class CheckoutUpdateDeliveryAddressAdapter(private var keys: Array<String>, private var addressJson: JsonObject) : RecyclerView.Adapter<CheckoutUpdateDeliveryAddressAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val addressLabel: TextView = itemView.findViewById(R.id.checkout_edit_address_label)
        val addressContent: TextView = itemView.findViewById(R.id.checkout_edit_address_content)

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addressLabel.text = keys[position]

        val streetAddress = addressJson.getAsJsonObject(keys[position]).get("street_address").toString()
        holder.addressContent.text = streetAddress
    }

    override fun getItemCount(): Int {
        return keys.size
    }
}