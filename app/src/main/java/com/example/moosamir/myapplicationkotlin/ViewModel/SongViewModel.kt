package com.example.moosamir.myapplicationkotlin.ViewModel

import com.example.moosamir.myapplicationkotlin.Model.Song

interface SongViewModelDelegate{

}

class SongViewModel(val delegate:SongViewModelDelegate) {
    var song:Song? = null
}