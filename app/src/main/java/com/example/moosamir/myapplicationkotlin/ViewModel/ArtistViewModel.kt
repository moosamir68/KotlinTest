package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Model.Artist

interface ArtistViewModelDelegate{

}

class ArtistViewModel(val delegate:ArtistViewModelDelegate) {
    var artist:Artist? = null
}