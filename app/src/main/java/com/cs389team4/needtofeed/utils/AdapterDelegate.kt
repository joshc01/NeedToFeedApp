package com.cs389team4.needtofeed.utils

interface AdapterDelegate<T> {
    fun onClick(item: T)
    fun onLongClick(item: T): Boolean
}
