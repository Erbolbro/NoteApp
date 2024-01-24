package com.example.kotlin3_3.ui.preference

import android.content.Context
import com.example.kotlin3_3.R

private const val IS_SHOWN_ON_BOARD_KEY = "isShownOnBoard"

class PreferencesHelper(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.tv_skip),
        Context.MODE_PRIVATE
    )
    var isShownOnBoard: Boolean
        get() = sharedPreferences.getBoolean(IS_SHOWN_ON_BOARD_KEY, false)
        set(value) {
            sharedPreferences.edit().putBoolean(IS_SHOWN_ON_BOARD_KEY, value).apply()
        }
}