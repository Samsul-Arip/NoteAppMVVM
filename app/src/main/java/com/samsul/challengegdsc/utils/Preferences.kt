package com.samsul.challengegdsc.utils

import android.content.Context
import android.content.SharedPreferences
class Preferences {

    companion object {
        private lateinit var sharedPreferences: SharedPreferences

        private var editor: SharedPreferences.Editor? = null

        fun localPreferences(context: Context) {
            sharedPreferences = context.getSharedPreferences("samplePref", Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()
        }

        fun getSharedPreferences(): SharedPreferences {
            return sharedPreferences
        }

        fun getEditor(): SharedPreferences.Editor? {
            return editor
        }
    }

}