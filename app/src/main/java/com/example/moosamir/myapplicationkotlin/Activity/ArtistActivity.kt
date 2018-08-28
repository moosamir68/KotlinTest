package com.example.moosamir.myapplicationkotlin

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.moosamir.myapplicationkotlin.Helper.LocaleHelper
import com.example.moosamir.myapplicationkotlin.Model.Artist
import com.example.moosamir.myapplicationkotlin.ViewModel.ArtistViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.ArtistViewModelDelegate
import kotlinx.android.synthetic.main.activity_artist.*

class ArtistActivity : AppCompatActivity(), ArtistViewModelDelegate {

    val viewModel:ArtistViewModel

    init {
        this.viewModel = ArtistViewModel(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(this.getAnimateForIn(), this.getAnimateForOut());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artist = intent.getSerializableExtra("ARTIST") as Artist
        this.viewModel.artist = artist
        setContentView(R.layout.activity_artist)

        this.initUI()
    }

    private fun initUI(){
        this.artist_name_textview.text = this.viewModel.artist!!.name
        var arrowBack = R.drawable.abc_ic_ab_back_material

        this.toolbar_artist.setNavigationIcon(arrowBack)

        this.toolbar_artist.setTitle("Artist")

        this.toolbar_artist.inflateMenu(R.menu.menu_toolbar)

        this.toolbar_artist.setNavigationOnClickListener(){
            this.userDidTapOnBackButton()
        }

        this.toolbar_artist.setOnMenuItemClickListener {
            this.userDidTapOnMenuItem(it)
            return@setOnMenuItemClickListener true
        }

        val menuItemListIcon = this.toolbar_artist.menu.findItem(R.id.menu_list).icon
        menuItemListIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        val menuItemShareIcon = this.toolbar_artist.menu.findItem(R.id.menu_share).icon
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

    private fun getAnimateForIn():Int{
        val language = LocaleHelper.getLanguage(this)
        if(language == "fa"){
            return R.anim.fa_anim_semi_in
        }else{
            return R.anim.en_anim_semi_in
        }
    }

    private fun getAnimateForOut():Int{
        val language = LocaleHelper.getLanguage(this)
        if(language == "fa"){
            return R.anim.fa_anim_out
        }else{
            return R.anim.en_animate_out
        }
    }
}
