package com.example.exp23.utility

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object Base64Utils {
    fun decodeBase64ToString(base64String: String): String {
        val bytes = Base64.decode(base64String, Base64.DEFAULT)
        return String(bytes, Charsets.UTF_8)
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        val bytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}