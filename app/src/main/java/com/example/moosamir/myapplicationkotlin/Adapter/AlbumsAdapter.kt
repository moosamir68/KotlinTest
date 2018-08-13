package com.example.moosamir.myapplicationkotlin.Adapter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moosamir.myapplicationkotlin.Interface.MLoadMore
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.R
import kotlinx.android.synthetic.main.album_item_view.view.*

internal class AlbumViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    var albumNameText = itemView.album_name_textview
}

class AlbumsAdapter(recyclerView:RecyclerView, activity:FragmentActivity?, var albums:MutableList<Album?>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val VIEW_ALBUMTYPE = 0
    val VIEW_LOADINGTYPE = 1

    internal var loadMore: MLoadMore? = null
    internal var isLoading:Boolean = false
    internal var visibleThresold = 5
    internal var lastVisibleItem:Int = 0
    internal var totalItemCount:Int = 0

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if(!isLoading && totalItemCount < lastVisibleItem + visibleThresold) {
                    isLoading = true
                    if(loadMore != null){
                        loadMore!!.onLoadMore()
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if(type ==VIEW_ALBUMTYPE){
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.album_item_view,viewGroup, false)

            return AlbumViewHolder(view)
        }else{
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.load_more_list_cell,viewGroup, false)

            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if(viewHolder is AlbumViewHolder){
            val album = this.albums[position]
            viewHolder.albumNameText.text = album!!.name
        }else if(viewHolder is LoadingViewHolder){
            viewHolder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(albums[position] == null) VIEW_LOADINGTYPE else VIEW_ALBUMTYPE
    }

    fun setLoaded(){
        this.isLoading = false
    }

    fun setLoadMore(mLoadMore: MLoadMore){
        this.loadMore = mLoadMore
    }
}