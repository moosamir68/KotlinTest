package com.example.moosamir.myapplicationkotlin

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.moosamir.myapplicationkotlin.Model.Album
import com.example.moosamir.myapplicationkotlin.ViewModel.AlbumViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.AlbumViewModelDelegate
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_main.*

class AlbumActivity : AppCompatActivity(), AlbumViewModelDelegate {

    val viewModel:AlbumViewModel

    init {
        this.viewModel = AlbumViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val album = intent.getSerializableExtra("ALBUM") as Album
        this.viewModel.album = album
        setContentView(R.layout.activity_album)

        this.initUI()
    }

    private fun initUI(){
        this.album_name_textview.text = this.viewModel.album!!.name
        var arrowBack = R.drawable.abc_ic_ab_back_material

        this.toolbar_album.setNavigationIcon(arrowBack)


        this.toolbar_album.setTitle("Album")

        this.toolbar_album.inflateMenu(R.menu.menu_toolbar)

        this.toolbar_album.setNavigationOnClickListener(){
            this.userDidTapOnBackButton()
        }

        this.toolbar_album.setOnMenuItemClickListener {
            this.userDidTapOnMenuItem(it)
            return@setOnMenuItemClickListener true
        }

        val menuItemListIcon = this.toolbar_album.menu.findItem(R.id.menu_list).icon
        menuItemListIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        val menuItemShareIcon = this.toolbar_album.menu.findItem(R.id.menu_share).icon
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
