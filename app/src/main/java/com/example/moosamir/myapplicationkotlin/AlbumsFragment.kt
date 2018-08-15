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
import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_songs.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://www.mocky.io/v2/5b7400933500006600531d64/").build()

            val postsApi = retrofit.create(INTNetworkApi::class.java)

            val response = postsApi.getAlbums()

            response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                if (it != null) {
                                    this.albums.removeAt(albums.size - 1)
                                    this.adapter!!.notifyItemRemoved(albums.size)
                                    this.albums.addAll(it)
                                    this.adapter!!.notifyDataSetChanged()
                                    this.adapter!!.setLoaded()
                                }
                            },{
                        Toast.makeText(activity,it.toString(), Toast.LENGTH_LONG).show()
                        println("error get songs")
                        println(it.toString())
                    }
                    )

        }else{
            Toast.makeText(activity,"Max songs loaded", Toast.LENGTH_SHORT).show()
        }
    }
}