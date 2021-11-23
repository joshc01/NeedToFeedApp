package com.cs389team4.needtofeed.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.model.Model
import com.bumptech.glide.Glide
import com.cs389team4.needtofeed.R

class RecyclerViewAdapter<T : Model>(var values: List<ViewModel<T>>,
                                     private val delegate: AdapterDelegate<T>
) : RecyclerView.Adapter<RecyclerViewAdapter<T>.ViewHolder>(){
    private var filteredList: MutableList<ViewModel<T>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_content, parent, false)
        return ViewHolder(view)
    }

    @SuppressWarnings("unchecked") // Cast from Any! to ViewModel<T>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.name.text = item.getTitle()
        holder.category.text = item.getCategory()
        Glide.with(holder.itemView).load(item.getImage()).into(holder.bannerImage)
        holder.location.text = item.getLocation()
        holder.hours.text = item.getHours()

        with(holder.itemView) {
            tag = item
            setOnClickListener { v ->
                delegate.onClick((v.tag as ViewModel<T>).model)
            }
            setOnLongClickListener { v ->
                delegate.onLongClick((v.tag as ViewModel<T>).model)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    private fun onSearchQuery(filteredList: List<ViewModel<T>>) {
        this.values = filteredList
        Log.d("onSearchQuery", "Filter ran")
        notifyDataSetChanged()
    }

    
     fun performFiltering(userFilter: String): Void? {

         if (userFilter.isEmpty()) {
            onSearchQuery(values)
        }
        else {
            for (restaurant in values) {
                if (restaurant.getTitle().lowercase().contains(userFilter)) filteredList.add(restaurant)
            }
        }
         onSearchQuery(filteredList)
         return null
}

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.restaurant_list_item_name)
        val category: TextView = view.findViewById(R.id.restaurant_list_item_category)
        val bannerImage: ImageView = view.findViewById(R.id.restaurant_list_item_banner)
        val location: TextView = view.findViewById(R.id.restaurant_list_item_location)
        val hours: TextView = view.findViewById(R.id.restaurant_list_item_hours)
    }

}