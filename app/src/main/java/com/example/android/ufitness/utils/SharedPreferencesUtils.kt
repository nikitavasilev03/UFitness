package com.example.android.ufitness.utils

import android.content.Context

object SharedPreferencesUtils {
    const val INFO_KEY="info_key"
    private const val APP_SHARED_PREFERENCES = "AppSharedPref"

    fun saveString(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String): String? {
        val sp = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sp.getString(key, null)
    }

}