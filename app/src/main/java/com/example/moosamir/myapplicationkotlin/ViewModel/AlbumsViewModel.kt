package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.Service.APIClient
import com.example.moosamir.myapplicationkotlin.Service.APIClientDelegate
import com.example.moosamir.myapplicationkotlin.Service.MMError
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class AlbumsViewModel(val delegate: ViewModelDelegate):APIClientDelegate {
    var albums:MutableList<Album?> = ArrayList<Album?>()
    var errorDescription:String = ""

    fun getAlbums(){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.mocky.io/v2/").build()

        val apiClient = APIClient.getInstance()
        val albumsApi = retrofit.create(INTNetworkApi::class.java)
        val request = albumsApi.getAlbums()

        apiClient.sendCall<List<Album>>(request, this)
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "album test" + i.toString()
            var artist = "album artist test" + i.toString()
            var album = Album(name, artist)
            albums.add(album)
        }
    }

    //MARK:- API Client delegate
    override fun <T> sucessfulyRequest(response: T) {
        this.albums.removeAt(albums.size - 1)
        this.albums.addAll(response as List<Album>)
        this.delegate.sucessGetData()
    }

    override fun <T> faildRequest(error: MMError<T>) {
        this.albums.removeAt(albums.size - 1)
        this.errorDescription = error.errorDescription
        this.delegate.faildGetData()
    }
}

