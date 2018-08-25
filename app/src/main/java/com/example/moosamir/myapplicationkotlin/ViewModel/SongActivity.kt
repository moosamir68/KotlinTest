package com.example.moosamir.myapplicationkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.example.moosamir.myapplicationkotlin.ViewModel.SongViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.SongViewModelDelegate
import kotlinx.android.synthetic.main.activity_song.*

class SongActivity : AppCompatActivity(), SongViewModelDelegate {

    val viewModel:SongViewModel

    init {
        this.viewModel = SongViewModel(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val song = intent.getSerializableExtra("SONG") as Song
        this.viewModel.song = song
        setContentView(R.layout.activity_song)
        this.initUI()
    }

    private fun initUI(){
        this.song_name_textview.text = this.viewModel.song!!.name
    }
}
