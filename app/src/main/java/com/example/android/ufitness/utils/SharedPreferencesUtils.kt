package com.example.android.ufitness.utils

import android.content.Context

object SharedPreferencesUtils {
    const val INFO_KEY="info_key"
    const val FIRST_LAUNCH_KEY="first_launch_key"
    private const val APP_SHARED_PREFERENCES = "AppSharedPref"

    fun saveString(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String): String? {
        val sp = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sp.getString(key, null)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        val sp = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sp.getBoolean(key, true)
    }

}