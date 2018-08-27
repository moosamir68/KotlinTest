package com.example.moosamir.myapplicationkotlin.Service

import android.accounts.Account
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.google.gson.Gson

val PREFS_FILENAME = "com.example.moosamir"
val KEY_ACCOUNT = "ACCOUNT_KEY"

class CacheManager(val contenx:Context) {

    val sharedPreferences = contenx.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)

    fun getAccount():UserAccount?{
        val accountString = sharedPreferences.getString(KEY_ACCOUNT,"")
        val account = Gson().fromJson(accountString, UserAccount::class.java)
        return account
    }

    fun saveAccount(userAccount:UserAccount){
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(userAccount)
        editor.putString(KEY_ACCOUNT, json)
        editor.commit()
    }

    fun removeAccount(){
        val editor = sharedPreferences.edit()
        editor.remove(KEY_ACCOUNT)
        editor.commit()
    }
}