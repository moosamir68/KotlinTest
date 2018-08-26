package com.example.moosamir.myapplicationkotlin

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.moosamir.myapplicationkotlin.Model.Song
import com.example.moosamir.myapplicationkotlin.ViewModel.SongViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.SongViewModelDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_song.*

class SongActivity : AppCompatActivity(), SongViewModelDelegate {

    val viewModel:SongViewModel

    init {
        this.viewModel = SongViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val song = intent.getSerializableExtra("SONG") as Song
        this.viewModel.song = song
        setContentView(R.layout.activity_song)

        this.initUI()
    }

    private fun initUI(){
        this.song_name_textview.text = this.viewModel.song!!.name
        var arrowBack = R.drawable.abc_ic_ab_back_material

        this.toolbar_song.setNavigationIcon(arrowBack)


        this.toolbar_song.setTitle("Song")

        this.toolbar_song.inflateMenu(R.menu.menu_toolbar)

        this.toolbar_song.setNavigationOnClickListener(){
            this.userDidTapOnBackButton()
        }

        this.toolbar_song.setOnMenuItemClickListener {
            this.userDidTapOnMenuItem(it)
            return@setOnMenuItemClickListener true
        }

        val menuItemListIcon = this.toolbar_song.menu.findItem(R.id.menu_list).icon
        menuItemListIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        val menuItemShareIcon = this.toolbar_song.menu.findItem(R.id.menu_share).icon
        menuItemShareIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }

    private fun userDidTapOnBackButton(){
        this.finish()
    }

    private fun userDidTapOnMenuItem(menuItem:MenuItem){
        if(menuItem!!.itemId == R.id.menu_list){
            this.userDidTapOnMenuList()
        }else if(menuItem!!.itemId == R.id.menu_share){
            this.userDidTapOnMenuShare()
        }
    }

    private fun userDidTapOnMenuList(){
        print("user did tap on menu list")
    }

    private fun userDidTapOnMenuShare(){
        print("user did tap on menu share")
    }
}
