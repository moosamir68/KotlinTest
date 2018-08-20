package com.example.moosamir.myapplicationkotlin.Interface

import com.example.moosamir.myapplicationkotlin.Service.MMError
import com.example.moosamir.myapplicationkotlin.Service.MMResponse
import okhttp3.Response
import org.json.JSONArray

interface ViewModelGetDataDelegate {

    fun sucessGetResponse(response:MMResponse)
    fun faildGetResponse(error:MMError)
}