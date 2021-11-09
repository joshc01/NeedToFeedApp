package com.cs389team4.needtofeed.utils

import com.amplifyframework.core.model.Model
import com.amplifyframework.datastore.generated.model.Restaurant

abstract class ViewModel<T : Model>(val model: T) {
    fun getId(): String {
        return model.id
    }

    fun model(): T {
        return model
    }

    abstract fun getTitle(): String
    abstract fun getCategory(): String
    abstract fun getImage(): String
    abstract fun getLocation(): String
    abstract fun getHours(): String

    abstract fun getPrice(): Double
}
