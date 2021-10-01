package com.example.usetrademarket.data.db.converters

import android.net.Uri
import androidx.room.TypeConverter

object UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }
}