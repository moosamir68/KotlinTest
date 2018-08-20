package com.example.moosamir.myapplicationkotlin.Service

import android.provider.ContactsContract
import java.net.HttpURLConnection

class MMResponse(connection: HttpURLConnection, text:String) {
    var statusCode:Int = 0
    var content:String
    var body:ContactsContract.Data? = null

    init {
        this.content = text
        this.statusCode = connection.responseCode
    }
}