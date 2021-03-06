package com.example.moosamir.myapplicationkotlin.Adapter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.example.moosamir.myapplicationkotlin.R
import kotlinx.android.synthetic.main.load_more_list_cell.view.*
import kotlinx.android.synthetic.main.song_item_view.view.*

interface SongsViewHolderDelegate{
    fun userSelectSong(song:Song)
}

internal class LoadingViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    var progressBar = itemView.progress_bar
}

internal class SongViewHolder(itemView: View, val delegateP:SongsViewHolderDelegate):RecyclerView.ViewHolder(itemView), View.OnClickListener{
    var delegate:SongsViewHolderDelegate
    var songNameText = itemView.song_name_textview
    var song:Song? = null

    init {
        this.delegate = delegateP
        this.songNameText.setOnClickListener(this)
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v!!.id == songNameText.id){

        }else if(v!!.id == itemView.id){
            this.delegate.userSelectSong(this.song!!)
        }
    }
}

class SongsAdapter(recyclerView:RecyclerView, activity: FragmentActivity?, var songs:MutableList<Song?>,val delegate:SongsViewHolderDelegate):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val VIEW_ITEMTYPE=0
    val VIEW_LOADINGTYPE=1

    internal var loadMore:MLoadMore? = null
    internal var isLoading:Boolean = false
    internal var visibleThresold = 5
    internal var lastVisibleItem:Int = 0
    internal var totalItemCount:Int = 0

    init {
        var linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if(!isLoading && totalItemCount < lastVisibleItem + visibleThresold) {
                    isLoading = true
                    if (loadMore != null)
                        loadMore!!.onLoadMore()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
       if(type == VIEW_ITEMTYPE){
           val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.song_item_view, viewGroup, false)

           return SongViewHolder(view, delegateP = delegate)
       }else{
           val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.load_more_list_cell, viewGroup, false)

           return LoadingViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        if(holder is SongViewHolder){
            val song = songs[p1]
            holder.song = song
            holder.songNameText.text = song!!.name
        }else if(holder is LoadingViewHolder){
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(songs[position] == null) VIEW_LOADINGTYPE else VIEW_ITEMTYPE
    }

    fun setLoaded(){
        isLoading = false
    }

    fun setLoadMore(mLoadMore:MLoadMore){
        this.loadMore = mLoadMore
    }

}