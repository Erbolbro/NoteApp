package com.example.kotlin3_3.data.preference

import android.content.Context
import com.example.kotlin3_3.R

private const val IS_SHOWN_ON_BOARD_KEY = "isShownOnBoard"
private const val IS_SHOW_REGISTER_KEY = "isShownRegister"
private const val IS_DARK_THEME_KEY = "isDarkTheme"
private const val TEXT_SIZE_KEY = "textSize"

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

    var isDarkTheme: Boolean
        get() = sharedPreferences.getBoolean(IS_DARK_THEME_KEY, true)
        set(value) {
            sharedPreferences.edit().putBoolean(IS_DARK_THEME_KEY, value).apply()
        }

    /** Размер текста заметок: 0 = маленький, 1 = средний, 2 = большой */
    var textSize: Int
        get() = sharedPreferences.getInt(TEXT_SIZE_KEY, 1)
        set(value) {
            sharedPreferences.edit().putInt(TEXT_SIZE_KEY, value).apply()
        }
}
