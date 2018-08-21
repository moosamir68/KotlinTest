package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelGetDataDelegate
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.Service.HttpManager
import com.example.moosamir.myapplicationkotlin.Service.MMError
import com.example.moosamir.myapplicationkotlin.Service.MMResponse
import com.google.gson.Gson
import org.json.JSONArray

public class ArtistsViewModel(val delegate: ViewModelDelegate):ViewModelGetDataDelegate {
    var artists:MutableList<Artist?> = ArrayList<Artist?>()
    var errorDescription:String = ""

    fun getArtists() {

        val request = HttpManager.instance.createApiRequestWithToken("", this)
        request.setMethod("GET")
        request.setProtocol("http")
        request.setBaseUrl("www.mocky.io")
        request.appendPathParameter("v2")
        request.appendPathParameter("5b7400dc3500006600531d6a")
        request.setCorrectResponseCode(201)
        request.send()
    }

    fun random10Data(){
        for (i in 0..9) {
            var name = "Artist test" + i.toString()
            var family = "Artitst family test" + i.toString()
            var artist = Artist(name, family)
            artists.add(artist)
        }
    }

    override fun sucessGetResponse(response: MMResponse) {

        val jsonArray = JSONArray(response.content)
        val list = java.util.ArrayList<Artist>()

        var x = 0
        while (x < jsonArray.length()){
            val jsonString = jsonArray.getString(x)
            val artistFromGson = Gson().fromJson(jsonString, Artist::class.java)
            list.add(artistFromGson)

            x++
        }

        this.artists.removeAt(artists.size - 1)
        this.artists.addAll(list)
        this.delegate.sucessGetData()
    }

    override fun faildGetResponse(error: MMError<Any>) {
        this.artists.removeAt(artists.size - 1)
        this.errorDescription = error.errorDescription
        this.delegate.faildGetData()
    }
}
