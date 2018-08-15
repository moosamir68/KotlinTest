package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class ArtistsViewModel(val delegate: ViewModelDelegate) {
    var artists:MutableList<Artist?> = ArrayList<Artist?>()
    var errorDescription:String = ""

    fun getArtists(){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.mocky.io/v2/5b7400dc3500006600531d6a/").build()

        val postsApi = retrofit.create(INTNetworkApi::class.java)

        val response = postsApi.getArtists()

        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    this.artists.removeAt(artists.size - 1)
                    this.artists.addAll(it)
                    this.delegate.sucessGetData()
                }, {

            this.artists.removeAt(artists.size - 1)
            this.errorDescription = it.toString()
            this.delegate.faildGetData()
        }
        )
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "Artist test" + i.toString()
            var family = "Artitst family test" + i.toString()
            var artist = Artist(name, family)
            artists.add(artist)
        }
    }
}