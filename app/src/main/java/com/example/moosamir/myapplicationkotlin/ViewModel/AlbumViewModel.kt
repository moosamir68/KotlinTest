package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Model.Album

interface AlbumViewModelDelegate{

}

class AlbumViewModel(val delegate:AlbumViewModelDelegate) {
    var album:Album? = null
}