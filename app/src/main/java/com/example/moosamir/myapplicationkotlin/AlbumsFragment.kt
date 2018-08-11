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


class AlbumsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_albums, container, false)

        var recyclerview = rootView.recycle_view as RecyclerView
        val linearLayoutManager = LinearLayoutManager(activity)

        recyclerview.layoutManager = linearLayoutManager
        val songs:Array<String> = arrayOf("Albumtest11","Albumtest12","Albumtest13","Albumtest21","Albumtest22","Albumtest23","Albumtest31","Albumtest32","Albumtest33","Albumtest41","Albumtest42","Albumtest43","Albumtest51","Albumtest52","Albumtest53","Albumtest61","Albumtest62","Albumtest63","Albumtest71","Albumtest72","Albumtest73")
//        recyclerview.adapter = SongsAdapter(songs)


        return rootView

    }

    companion object {
        fun newInstance(): AlbumsFragment = AlbumsFragment()
    }

    fun loadMoreData(int: Int){

    }
}