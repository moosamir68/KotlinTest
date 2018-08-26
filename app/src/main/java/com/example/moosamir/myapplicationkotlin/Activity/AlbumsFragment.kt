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
import com.example.moosamir.myapplicationkotlin.Adapter.AlbumsAdapter
import com.example.moosamir.myapplicationkotlin.Adapter.AlbumsViewHolderDelegate
import com.example.moosamir.myapplicationkotlin.AlbumActivity
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Interface.ViewModelDelegate
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.ViewModel.AlbumsViewModel
import kotlinx.android.synthetic.main.fragment_songs.view.*

class AlbumsFragment : Fragment(), MLoadMore, ViewModelDelegate, AlbumsViewHolderDelegate {

    var adapter:AlbumsAdapter? = null
    val viewModel:AlbumsViewModel

    init {
        this.viewModel = AlbumsViewModel(this)
        this.viewModel.random10Data()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_albums, container, false)

        var recyclerView = rootView.recycle_view as RecyclerView
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        adapter = AlbumsAdapter(recyclerView, activity, this.viewModel.albums, this)
        recyclerView.adapter = adapter

        adapter!!.setLoadMore(this)

        return rootView

    }

    companion object {
        fun newInstance(): AlbumsFragment = AlbumsFragment()
    }

    //load more delegate
    override fun onLoadMore() {
        if(this.viewModel.albums.size < 50){
            this.viewModel.albums!!.add(null)
            adapter!!.notifyItemInserted(this.viewModel.albums.size - 1)
            this.viewModel.getAlbums()
        }else{
            Toast.makeText(activity,"Max Album loaded", Toast.LENGTH_SHORT).show()
        }
    }

    //view model delegate
    override fun sucessGetData() {
        this.adapter!!.notifyDataSetChanged()
        this.adapter!!.setLoaded()
    }

    override fun faildGetData() {
        Toast.makeText(activity,this.viewModel.errorDescription, Toast.LENGTH_LONG).show()
        println("error get songs")
        this.adapter!!.notifyDataSetChanged()
        this.adapter!!.setLoaded()
    }

    //MARK:- album view holder delegate
    override fun userSelectAlbum(album: Album) {
        val albumIntent = Intent(activity, AlbumActivity::class.java)
        albumIntent.putExtra("ALBUM", album)
        activity!!.startActivity(albumIntent)
    }
}