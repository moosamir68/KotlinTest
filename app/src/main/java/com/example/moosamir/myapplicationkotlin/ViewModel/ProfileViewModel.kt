package com.example.moosamir.myapplicationkotlin.ViewModel

import android.content.Context
import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.example.moosamir.myapplicationkotlin.Service.CacheManager

interface ProfileViewModelDelegate{

}

class ProfileViewModel(val delegate:ProfileViewModelDelegate) {

    fun getProfile(contenxt: Context):UserAccount?{
        return CacheManager(contenxt).getAccount()
    }

    fun signOut(contenxt: Context){
        CacheManager(contenxt).removeAccount()
    }
}