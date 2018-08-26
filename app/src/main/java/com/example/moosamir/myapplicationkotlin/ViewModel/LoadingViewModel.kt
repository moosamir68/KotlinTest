package com.example.moosamir.myapplicationkotlin.ViewModel

interface LoadingViewModelDelegate{

}

class LoadingViewModel(val delegate:LoadingViewModelDelegate) {
    fun canShowMain():Boolean{
        return false
    }
}