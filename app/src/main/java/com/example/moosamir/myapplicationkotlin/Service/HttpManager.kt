package com.example.moosamir.myapplicationkotlin.Service

import com.example.moosamir.myapplicationkotlin.Interface.ViewModelGetDataDelegate

public class HttpManager private constructor() {
    init {
        println("This ($this) is a singleton")
    }

    private object Holder { val INSTANCE = HttpManager() }

    companion object {
        val instance: HttpManager by lazy { Holder.INSTANCE }
    }


    fun createApiRequestWithToken(token:String, delegate:ViewModelGetDataDelegate): MMRequest{
        val request = MMRequest(delegate)

        return request
    }
}