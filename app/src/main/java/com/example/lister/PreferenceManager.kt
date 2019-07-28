package com.example.lister

import android.content.Context
import android.content.SharedPreferences
import com.example.lister.data.model.LoggedInUser

class PreferenceManager(context: Context){
    val preferences: SharedPreferences = context.getSharedPreferences("ListerSharedPreferences", Context.MODE_PRIVATE)
    fun logoutAccount(){
        val prefEditor = preferences.edit()
        prefEditor.remove("userNick")
        prefEditor.remove("userId")
        prefEditor.remove("sessionKey")
        prefEditor.remove("logged")
        prefEditor.apply()
    }

    fun loginAccount(data: LoggedInUser){
        val prefEditor = preferences.edit()
        prefEditor.putString("userNick", data.displayName)
        prefEditor.putInt("userId", data.userId)
        prefEditor.putString("sessionKey", data.sessionKey)
        prefEditor.putBoolean("logged", true)
        prefEditor.apply()
    }
}
