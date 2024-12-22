package com.example.kotlin3_3.data.preference

import android.content.Context
import com.example.kotlin3_3.R

private const val IS_SHOWN_ON_BOARD_KEY = "isShownOnBoard"

private const val IS_SHOW_REGISTER_KEY = "isShownRegister"



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
    var isRegisterShow: Boolean
        get() = sharedPreferences.getBoolean(IS_SHOW_REGISTER_KEY, false)
        set(value) {
            sharedPreferences.edit().putBoolean(IS_SHOW_REGISTER_KEY, value).apply()
        }

}