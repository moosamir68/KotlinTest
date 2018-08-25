package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.Service.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class ArtistsViewModel(val delegate: ViewModelDelegate):APIClientDelegate {
    var artists:MutableList<Artist?> = ArrayList<Artist?>()
    var errorDescription:String = ""

    fun getArtists(){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.mocky.io/v2/").build()

        val apiClient = APIClient.getInstance()
        val albumsApi = retrofit.create(INTNetworkApi::class.java)
        val request = albumsApi.getArtists()

        apiClient.send<List<Artist>>(request, this)
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "album test" + i.toString()
            var family = "family artist test" + i.toString()
            var artist = Artist(name, family)
            artists.add(artist)
        }
    }

    //MARK:- API Client delegate
    override fun <T> sucessfulyRequest(response: T) {
        this.artists.removeAt(artists.size - 1)
        this.artists.addAll(response as List<Artist>)
        this.delegate.sucessGetData()
    }

    override fun <T> faildRequest(error: MMError<T>) {
        this.artists.removeAt(artists.size - 1)
        this.errorDescription = error.errorDescription
        this.delegate.faildGetData()
    }
}