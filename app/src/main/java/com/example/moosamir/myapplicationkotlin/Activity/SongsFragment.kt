package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moosamir.myapplicationkotlin.Adapter.SongsAdapter
import com.example.moosamir.myapplicationkotlin.Adapter.SongsViewHolderDelegate
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.SongActivity
import com.example.moosamir.myapplicationkotlin.ViewModel.SongsViewModel
import kotlinx.android.synthetic.main.fragment_songs.view.*


class SongsFragment : Fragment(), MLoadMore, ViewModelDelegate, SongsViewHolderDelegate {

    var adapter:SongsAdapter? = null
    var viewModel:SongsViewModel

    // Initializing an empty ArrayList to be filled with animals

    init {
        this.viewModel = SongsViewModel(this)
        this.viewModel.random10Data()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_songs, container, false)

        var recyclerview = rootView.recycle_view as RecyclerView

        var linearLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = linearLayoutManager

        adapter = SongsAdapter(recyclerview, activity, this.viewModel.songs, this)
        recyclerview.adapter = adapter

        adapter!!.setLoadMore(this)

        return rootView

    }

    companion object {
        fun newInstance(): SongsFragment = SongsFragment()
    }

    //MMLoad more
    override fun onLoadMore() {
        if(this.viewModel.songs.size < 50){
            this.viewModel.songs!!.add(null)
            adapter!!.notifyItemInserted(this.viewModel.songs.size - 1)
            this.viewModel.getSongs()
        }else{
            Toast.makeText(activity,"Max songs loaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun sucessGetData() {
        this.adapter!!.notifyDataSetChanged()
        this.adapter!!.setLoaded()
    }

    override fun faildGetData() {
            Toast.makeText(activity,this.viewModel.errorDescription, Toast.LENGTH_LONG).show()
            println("error get songs")
            this.adapter!!.notifyItemRemoved(this.viewModel.songs.size)
            this.adapter!!.setLoaded()
    }

    //MARK:- SongsView holder delegate
    override fun userSelectSong(song:Song) {
        val songIntent = Intent(activity, SongActivity::class.java)
        songIntent.putExtra("SONG", song)
        activity!!.startActivity(songIntent)
    }
}