package com.example.moosamir.myapplicationkotlin.Service

import android.widget.Switch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class MMError(e:Exception?, conection:HttpURLConnection?, throwable: Throwable?) {
    var errorDescription:String = ""
    var errorCode:Int = 200

    init {
        if (conection != null) {
            errorDescription = getDescriptionFromResponse(conection!!)
        }else if(e != null){
            errorDescription = getDescriptionFromErrorExeption(e!!)
        }else if(throwable != null){
            errorDescription = getDescriptionFromThrowable(throwable!!)
        }
    }

    fun getDescriptionFromThrowable(error:Throwable):String{
        if(error is UnknownHostException){
            return "can not connect to server"
        }else if(error is ConnectException){
            return "can not connect to internet"
        }else if(error is TimeoutException){
            return "request time out"
        }else if(error is HttpException){
            val errorDescription = getDescriptionFromStatusCode(error.code())
            return errorDescription
        }else{
            return "can not connect to server"
        }
    }

    fun getDescriptionFromErrorExeption(error:Exception):String{

        if(error is UnknownHostException){
            return "can not connect to server"
        }else if(error is ConnectException){
            return "can not connect to internet"
        }else if(error is TimeoutException){
            return "request time out"
        }else if(error is HttpException){
            val errorDescription = getDescriptionFromStatusCode(error.code())
            return errorDescription
        }else{
            return "can not connect to server"
        }
    }

    fun getDescriptionFromResponse(response:HttpURLConnection):String{
        val responseCode:Int = response!!.responseCode
        return getDescriptionFromStatusCode(responseCode)
    }

    fun getDescriptionFromStatusCode(responseCode:Int):String{

        if(responseCode >=100 && responseCode < 200){
            return getDescriptionErrorRange100(responseCode)
        }else if(responseCode >=200 && responseCode < 300){
            return getDescriptionErrorRange200(responseCode)
        }else if(responseCode >=300 && responseCode < 400){
            return getDescriptionErrorRange300(responseCode)
        }else if(responseCode >=400 && responseCode < 500){
            return getDescriptionErrorRange400(responseCode)
        }else if(responseCode >=500 && responseCode < 600){
            return getDescriptionErrorRange500(responseCode)
        }else{
            return "can not connect to internet"
        }
    }

    fun getDescriptionErrorRange100(responseConde:Int):String{
        when(responseConde){
            100 -> return "Continue"
            101 -> return "Switching Protocols"

            else -> return "Continue"
        }
    }

    fun getDescriptionErrorRange200(responseConde:Int):String{
        when(responseConde){
            200 -> return "OK"
            201 -> return "Created"
            202 -> return "Accepted"
            203 -> return "Non-Authoritative Information"
            204 -> return "No Content"
            205 -> return "Reset Content"
            206 -> return "Partial Content"

            else -> return "OK"
        }
    }

    fun getDescriptionErrorRange300(responseConde:Int):String{
        when(responseConde){
            300 -> return "Multiple Choices"
            301 -> return "Moved Permanently"
            302 -> return "Found"
            303 -> return "See Other"
            304 -> return "Not Modified"
            305 -> return "Use Proxy"
            307 -> return "Temporary Redirect"

            else -> return "Temporary Redirect"
        }
    }

    fun getDescriptionErrorRange400(responseConde:Int):String{
        when(responseConde){
            400 -> return "Bad Request"
            401 -> return "Unauthorized"
            402 -> return "Payment Required"
            403 -> return "Forbidden"
            404 -> return "Not Found"
            405 -> return "Method Not Allowed"
            406 -> return "Not Acceptable"
            407 -> return "Proxy Authentication Required"
            408 -> return "Request Timeout"
            409 -> return "Conflict"
            410 -> return "Gone"
            411 -> return "Length Required"
            412 -> return "Precondition Failed"
            413 -> return "Request Entity Too Large"
            414 -> return "Request-URI Too Long"
            415 -> return "Unsupported Media Type"
            416 -> return "Requested Range Not Satisfiable"
            417 -> return "Expectation Failed"

            else -> return "Bad Request"
        }
    }

    fun getDescriptionErrorRange500(responseConde:Int):String{
        when(responseConde){
            500 -> return "Internal Server Error"
            501 -> return "Not Implemented"
            502 -> return "Bad Gateway"
            503 -> return "Service Unavailable"
            504 -> return "Gateway Timeout"
            505 -> return "HTTP Version Not Supported"

            else -> return "Internal Server Error"
        }
    }
}