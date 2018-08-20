package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelGetDataDelegate
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.example.moosamir.myapplicationkotlin.Service.HttpManager
import com.example.moosamir.myapplicationkotlin.Service.MMError
import com.example.moosamir.myapplicationkotlin.Service.MMResponse
import com.google.gson.Gson
import org.json.JSONArray
import java.util.*

public class SongsViewModel(val delegate:ViewModelDelegate):ViewModelGetDataDelegate {
    var songs:MutableList<Song?> = ArrayList<Song?>()
    var errorDescription:String = ""

    fun getSongs(){

        val request = HttpManager.instance.createApiRequestWithToken("", this)
        request.setMethod("GET")
        request.setProtocol("http")
        request.setBaseUrl("www.mocky.io")
        request.appendPathParameter("v2")
        request.appendPathParameter("5b781a912e00001200864c16")
        request.setCorrectResponseCode(200)
        request.send()
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "Song test" + i.toString()
            var artist = "Artitst song test" + i.toString()
            var song = Song(name, artist)
            songs.add(song)
        }
    }

    //result data
    override fun sucessGetResponse(response: MMResponse) {

        val jsonArray = JSONArray(response.content)
        val list = ArrayList<Song>()

        var x = 0
        while (x < jsonArray.length()){
            val jsonString = jsonArray.getString(x)
            val song = Gson().fromJson(jsonString, Song::class.java)
            list.add(song)

            x++
        }

        this.songs.removeAt(songs.size - 1)
        this.songs.addAll(list)
        this.delegate.sucessGetData()
    }


    override fun faildGetResponse(error:MMError) {
        this.songs.removeAt(songs.size - 1)
        this.errorDescription = error.errorDescription
        this.delegate.faildGetData()
    }
}