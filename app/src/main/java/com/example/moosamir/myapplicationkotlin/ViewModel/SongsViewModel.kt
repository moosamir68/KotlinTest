package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class SongsViewModel(val delegate:ViewModelDelegate) {
    var songs:MutableList<Song?> = ArrayList<Song?>()
    var errorDescription:String = ""

    fun getSongs(){
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.mocky.io/v2/5b73e33b3500009d01531ccc/").build()

        val postsApi = retrofit.create(INTNetworkApi::class.java)

        val response = postsApi.getSongs()

        response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    this.songs.removeAt(songs.size - 1)
                    this.songs.addAll(it)
                    this.delegate.sucessGetData()
                }, {

            this.songs.removeAt(songs.size - 1)
            this.errorDescription = it.toString()
            this.delegate.faildGetData()
        }
        )
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "Song test" + i.toString()
            var artist = "Artitst song test" + i.toString()
            var song = Song(name, artist)
            songs.add(song)
        }
    }
}