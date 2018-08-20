package com.example.moosamir.myapplicationkotlin.Interface

import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.Model.Song
import retrofit2.http.GET
import java.util.*
import io.reactivex.Observable

interface INTNetworkApi {

    @GET("songs")
    fun getSongs() : Observable<List<Song>>

    @GET("artist")
    fun getArtists() : Observable<List<Artist>>

    fun getDataWithJson() : Observable<Dictionary<String, Any>>

    @GET("Album")
    fun getAlbums() : Observable<List<Album>>

    @GET("Songs")
    fun getDataWithArray() : List<Any>
}