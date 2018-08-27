package com.example.moosamir.myapplicationkotlin.ViewModel

import android.content.Context
import com.example.moosamir.myapplicationkotlin.Service.CacheManager

interface LoadingViewModelDelegate{

}

class LoadingViewModel(val delegate:LoadingViewModelDelegate) {
    fun canShowMain(context:Context):Boolean{
        val account = CacheManager(context).getAccount()
        if(account == null){
            return false
        }else{
            return true
        }
    }
}