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
import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.google.gson.GsonBuilder
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_songs.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://www.mocky.io/v2/5b73e33b3500009d01531ccc/").build()

            val postsApi = retrofit.create(INTNetworkApi::class.java)

            val response = postsApi.getSongs()

            response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            if (it != null) {
                                this.songs.removeAt(songs.size - 1)
                                this.adapter!!.notifyItemRemoved(songs.size)
                                this.songs.addAll(it)
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