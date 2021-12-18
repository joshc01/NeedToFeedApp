package com.cs389team4.needtofeed.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs389team4.needtofeed.R

class CheckoutUpdateContactInfoAdapter(private var data: MutableList<String>?) : RecyclerView.Adapter<CheckoutUpdateContactInfoAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val phone: TextView = itemView.findViewById(R.id.lbl_checkout_edit_contact_list_phone)

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkout_contact_info_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.phone.text = data!![position]
    }

    override fun getItemCount(): Int {
        return data!!.size
    }
}
