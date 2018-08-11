package com.example.moosamir.myapplicationkotlin
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moosamir.myapplicationkotlin.Adapter.SongsAdapter
import kotlinx.android.synthetic.main.fragment_songs.view.*

class ArtistsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_artist, container, false)

        var recyclerview = rootView.recycle_view as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val songs:Array<String> = arrayOf("Artisttest11","Artisttest12","Artisttest13","Artisttest21","Artisttest22","Artisttest23","Artisttest31","Artisttest32","Artisttest33","Artisttest41","Artisttest42","Artisttest43","Artisttest51","Artisttest52","Artisttest53","Artisttest61","Artisttest62","Artisttest63","Artisttest71","Artisttest72","Artisttest73")
//        recyclerview.adapter = ArtistsFragment(songs)

        return rootView

    }

    companion object {
        fun newInstance(): ArtistsFragment = ArtistsFragment()
    }
}