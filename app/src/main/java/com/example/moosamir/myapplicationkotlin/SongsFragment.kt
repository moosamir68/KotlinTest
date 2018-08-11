package com.example.moosamir.myapplicationkotlin
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moosamir.myapplicationkotlin.Adapter.SongsAdapter
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Song
import kotlinx.android.synthetic.main.fragment_songs.view.*

class SongsFragment : Fragment(), MLoadMore {

    var songs:MutableList<Song?> = ArrayList<Song?>()
    var adapter:SongsAdapter? = null
    var loadDefailt:Boolean = false

    // Initializing an empty ArrayList to be filled with animals

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_songs, container, false)

        random10Data()

        var recyclerview = rootView.recycle_view as RecyclerView

        var linearLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = linearLayoutManager

        adapter = SongsAdapter(recyclerview, activity, songs)
        recyclerview.adapter = adapter

        adapter!!.setLoadMore(this)

        return rootView

    }

    companion object {
        fun newInstance(): SongsFragment = SongsFragment()
    }

    fun random10Data(){
        if(!this.loadDefailt) {
            this.loadDefailt = true
            for (i in 0..9) {
                var name = "Song test" + i.toString()
                var artist = "Artitst song test" + i.toString()
                var song = Song(name, artist)
                songs.add(song)
            }
        }
    }

    override fun onLoadMore() {
        if(songs.size < 50){
            songs!!.add(null)
            adapter!!.notifyItemInserted(songs.size - 1)

            Handler().postDelayed({
                songs.removeAt(songs.size - 1)
                adapter!!.notifyItemRemoved(songs.size)

                var index = songs.size
                var end = index + 10

                for (i in index until end){
                    val name = "Song Load more Test" + i.toString()
                    var artistName = "Song Load more name test" + i.toString()

                    val song = Song(name, artistName)
                    songs.add(song)
                }

                adapter!!.notifyDataSetChanged()
                adapter!!.setLoaded()
            }, 3000)


        }else{
            Toast.makeText(activity,"Max songs loaded", Toast.LENGTH_SHORT).show()
        }
    }
}