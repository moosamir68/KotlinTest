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
import com.example.moosamir.myapplicationkotlin.Adapter.AlbumsAdapter
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Album
import kotlinx.android.synthetic.main.fragment_songs.view.*

class AlbumsFragment : Fragment(), MLoadMore {

    var albums:MutableList<Album?> = ArrayList<Album?>()
    var loadDefault = false
    var adapter:AlbumsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_albums, container, false)

        this.random10Data()

        var recyclerView = rootView.recycle_view as RecyclerView
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        adapter = AlbumsAdapter(recyclerView, activity, albums)
        recyclerView.adapter = adapter

        adapter!!.setLoadMore(this)

        return rootView

    }

    companion object {
        fun newInstance(): AlbumsFragment = AlbumsFragment()
    }

    //generate 10 artist with random
    fun random10Data(){
        if(!this.loadDefault) {
            this.loadDefault = true
            for (i in 0..9) {
                var name = "Album test" + i.toString()
                var artist = "Album artist test" + i.toString()
                var album = Album(name, artist)
                albums.add(album)
            }
        }
    }

    override fun onLoadMore() {
        if(albums.size < 50){
            albums!!.add(null)
            adapter!!.notifyItemInserted(albums.size - 1)

            Handler().postDelayed({
                albums.removeAt(albums.size - 1)
                adapter!!.notifyItemRemoved(albums.size)

                var index = albums.size
                var end = index + 10

                for (i in index until end){
                    val name = "Album Load more name Test" + i.toString()
                    var artist = "Album Load more artist test" + i.toString()

                    val album = Album(name, artist)
                    albums.add(album)
                }

                adapter!!.notifyDataSetChanged()
                adapter!!.setLoaded()
            }, 3000)


        }else{
            Toast.makeText(activity,"Max songs loaded", Toast.LENGTH_SHORT).show()
        }
    }
}