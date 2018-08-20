package com.example.moosamir.myapplicationkotlin.Service

import android.net.Uri
import android.os.AsyncTask
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelGetDataDelegate
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.URL

class MMRequest(val delegate:ViewModelGetDataDelegate) {

    private var baseUrl:String = ""
    private var protocol:String = ""
    private var pathParams:MutableList<String> = mutableListOf<String>()
    private var queryParams:MutableMap<String, String> = mutableMapOf<String, String>()
    private var error:MMError? = null
    private var response:MMResponse? = null
    private var requestMethod:String = "GET"
    private var currectResponseCode:Int = 200

    fun setBaseUrl(baseUrl:String){
        this.baseUrl = baseUrl
    }

    fun setProtocol(protocol:String){
        this.protocol = protocol
    }

    fun appendPathParameter(path:String){
        pathParams.add(path)
    }

    fun appendQuesryParam(key:String, value:String){
        queryParams.set(key, value)
    }

    fun setMethod(methode:String){
        requestMethod = methode
    }

    fun setCorrectResponseCode(code:Int){
        currectResponseCode = code
    }

    fun send(){
        var url = Uri.Builder()
        url.scheme(protocol)
        url.authority(baseUrl)
        for (path in pathParams){
            url.appendPath(path)
        }

        for (queryParam in queryParams){
            url.appendQueryParameter(queryParam.key, queryParam.value)
        }

        url.build()
        AsyncTaskHandleJson().execute(url.toString())
    }

    inner class AsyncTaskHandleJson:AsyncTask<String, String, String>(){
        override fun doInBackground(vararg url: String?): String {

            var text:String = ""
            var connection = URL(url[0]).openConnection() as HttpURLConnection
            connection.requestMethod = requestMethod
            try{
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
                println("statusCode" + connection.responseCode)
                println("message = " + connection.content)
                if(connection.responseCode == currectResponseCode){
                    response = MMResponse(connection, text)
                }else{
                    error = MMError(null, connection, null)
                }
            }catch (e: Exception) {
                e.printStackTrace()
                error = MMError(e, null, null)
            }finally {
                connection.disconnect()
                return ""
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleResult()
        }
    }

    private fun handleResult(){
        if(response != null) {
            this.delegate.sucessGetResponse(response!!)
        }else if(error != null){
            this.delegate.faildGetResponse(error!!)
        }
    }

}