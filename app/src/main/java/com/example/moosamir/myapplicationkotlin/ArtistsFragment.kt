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
import com.example.moosamir.myapplicationkotlin.Adapter.ArtistsAdapter
import com.example.moosamir.myapplicationkotlin.Adapter.SongsAdapter
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.Model.Song
import kotlinx.android.synthetic.main.fragment_songs.view.*

class ArtistsFragment : Fragment(), MLoadMore {

    var artists:MutableList<Artist?> = ArrayList<Artist?>()
    var loadDefault = false
    var adapter:ArtistsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_artist, container, false)

        this.random10Data()

        var recyclerView = rootView.recycle_view as RecyclerView
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        adapter = ArtistsAdapter(recyclerView, activity, artists)
        recyclerView.adapter = adapter

        adapter!!.setLoadMore(this)

        return rootView

    }

    companion object {
        fun newInstance(): ArtistsFragment = ArtistsFragment()
    }

    //generate 10 artist with random
    fun random10Data(){
        if(!this.loadDefault) {
            this.loadDefault = true
            for (i in 0..9) {
                var name = "Artist test" + i.toString()
                var family = "Artitst family test" + i.toString()
                var artist = Artist(name, family)
                artists.add(artist)
            }
        }
    }

    override fun onLoadMore() {
        if(artists.size < 50){
            artists!!.add(null)
            adapter!!.notifyItemInserted(artists.size - 1)

            Handler().postDelayed({
                artists.removeAt(artists.size - 1)
                adapter!!.notifyItemRemoved(artists.size)

                var index = artists.size
                var end = index + 10

                for (i in index until end){
                    val name = "Artist Load more name Test" + i.toString()
                    var family = "Artist Load more family test" + i.toString()

                    val artist = Artist(name, family)
                    artists.add(artist)
                }

                adapter!!.notifyDataSetChanged()
                adapter!!.setLoaded()
            }, 3000)


        }else{
            Toast.makeText(activity,"Max songs loaded", Toast.LENGTH_SHORT).show()
        }
    }
}