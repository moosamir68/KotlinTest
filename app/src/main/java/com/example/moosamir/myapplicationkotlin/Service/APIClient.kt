package com.example.moosamir.myapplicationkotlin.Service

import io.reactivex.Observable
import com.example.moosamir.myapplicationkotlin.Model.Album
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface APIClientDelegate{
    fun <T>sucessfulyRequest(response:T)
    fun <T>faildRequest(error:MMError<T>)
}

class APIClient() {

    val baseURL: String = "https://v2.frikadell.com/api/"
    var retrofit:Retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()

    companion object {
        var apiClient:APIClient = APIClient()
        fun getInstance(): APIClient {
            return apiClient
        }

    }

    public fun <T>sendCall(request: Call<T>, delegate:APIClientDelegate){

        request.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable) {
                val error = MMError<T>(null, null, t, null)
                delegate.faildRequest(error)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.body() != null){
                    delegate.sucessfulyRequest(response.body())
                }else if(response.errorBody() != null){
                    val error = MMError<T>(null, null, null, response)
                    delegate.faildRequest(error)
                }else{
                    val error = MMError<T>(null, null, null, null)
                    error.errorDescription = "Can not connect to server"
                    delegate.faildRequest(error)
                }
            }

        })
    }

    fun <T>send(request: Observable<T>, delegate:APIClientDelegate){
        request.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    delegate.sucessfulyRequest(it)
                },
                {
                    val error = MMError<Album>(null, null, it, null)
                    delegate.faildRequest(error)
                }
        )
    }
}