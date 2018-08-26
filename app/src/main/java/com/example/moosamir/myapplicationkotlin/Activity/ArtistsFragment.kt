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
import com.example.moosamir.myapplicationkotlin.Adapter.ArtistsAdapter
import com.example.moosamir.myapplicationkotlin.Adapter.ArtistsViewHolderDelegate
import com.example.moosamir.myapplicationkotlin.ArtistActivity
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.ViewModel.ArtistsViewModel
import kotlinx.android.synthetic.main.fragment_songs.view.*

class ArtistsFragment : Fragment(), MLoadMore, ViewModelDelegate, ArtistsViewHolderDelegate {

    var adapter:ArtistsAdapter? = null
    val viewModel:ArtistsViewModel

    init {
        this.viewModel = ArtistsViewModel(this)
        this.viewModel.random10Data()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_artist, container, false)

        var recyclerView = rootView.recycle_view as RecyclerView
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        adapter = ArtistsAdapter(recyclerView, activity, this.viewModel.artists, this)
        recyclerView.adapter = adapter

        adapter!!.setLoadMore(this)

        return rootView

    }

    companion object {
        fun newInstance(): ArtistsFragment = ArtistsFragment()
    }

    //load more delegate
    override fun onLoadMore() {
        if(this.viewModel.artists.size < 50){
            this.viewModel.artists!!.add(null)
            adapter!!.notifyItemInserted(this.viewModel.artists.size - 1)
            this.viewModel.getArtists()
        }else{
            Toast.makeText(activity,"Max Artists loaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun sucessGetData() {
        this.adapter!!.notifyDataSetChanged()
        this.adapter!!.setLoaded()
    }

    override fun faildGetData() {
        Toast.makeText(activity,this.viewModel.errorDescription, Toast.LENGTH_LONG).show()
        println("error get songs")
        this.adapter!!.notifyItemRemoved(this.viewModel.artists.size)
        this.adapter!!.setLoaded()
    }

    //MARK:- artist view holder delegate
    override fun userSelectArtist(artist: Artist) {
        val artistIntent = Intent(activity, ArtistActivity::class.java)
        artistIntent.putExtra("ARTIST", artist)
        activity!!.startActivity(artistIntent)
    }
}