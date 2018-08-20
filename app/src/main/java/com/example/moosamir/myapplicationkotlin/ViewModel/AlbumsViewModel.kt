package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class AlbumsViewModel(val delegate: ViewModelDelegate) {
    var albums:MutableList<Album?> = ArrayList<Album?>()
    var errorDescription:String = ""

    fun getAlbums(){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.mocky.io/v2/5b7400933500006600531d64/").build()

        val postsApi = retrofit.create(INTNetworkApi::class.java)

        val response = postsApi.getAlbums()

        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    this.albums.removeAt(albums.size - 1)
                    this.albums.addAll(it)
                    this.delegate.sucessGetData()
                }, {

            this.albums.removeAt(albums.size - 1)
            this.errorDescription = it.toString()
            this.delegate.faildGetData()
        }
        )
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "album test" + i.toString()
            var artist = "album artist test" + i.toString()
            var album = Album(name, artist)
            albums.add(album)
        }
    }
}

