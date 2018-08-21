package com.example.moosamir.myapplicationkotlin.Interface

import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.Model.Song
import java.util.*
import io.reactivex.Observable
import retrofit2.http.*
import retrofit2.Call

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

    @POST("users/app_login/")
    @FormUrlEncoded
    fun login(@Field("username") username:String, @Field("password") password:String) : Call<UserAccount>
}