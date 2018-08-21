package com.example.moosamir.myapplicationkotlin.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dnyaneshwar Dalvi on 21/11/17.
 */
class APIClient {


    companion object {

        val baseURL: String = "https://v2.frikadell.com/api/"
        var retofit: Retrofit? = null

        val client: Retrofit
            get() {

                if (retofit == null) {
                    retofit = Retrofit.Builder()
                            .baseUrl(baseURL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retofit!!
            }
    }
}