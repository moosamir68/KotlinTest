package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.moosamir.myapplicationkotlin.Helper.LocaleHelper
import com.example.moosamir.myapplicationkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

interface MainActivityDelegate{
    fun reloadApp()
}

class MainActivity : AppCompatActivity(), ProfileFragmentDelegate {

    var songsFragment: SongsFragment = SongsFragment.newInstance()
    var albumsFragment: AlbumsFragment = AlbumsFragment.newInstance()
    var artinstsFragment: ArtistsFragment = ArtistsFragment.newInstance()
    var profileFragment: ProfileFragment = ProfileFragment.newInstance(this)

    var delegate:MainActivityDelegate? = null

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(this.getAnimateForIn(), this.getAnimateForOut())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.initUI()
    }

    private fun initUI() {
        this.toolbar.setTitle(R.string.songs)
        openFragment(this.songsFragment)
        this.navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_songs -> {
                this.toolbar.setTitle(R.string.songs)
                openFragment(this.songsFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_albums -> {
                this.toolbar.setTitle(R.string.albums)
                openFragment(this.albumsFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_artists -> {
                this.toolbar.setTitle(R.string.artists)
                openFragment(this.artinstsFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_profile -> {
                this.toolbar.setTitle(R.string.profile)
                openFragment(this.profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!));
    }

    //MARK:- profile delegate
    override fun changeLanguageTo(newLang: String) {
        LocaleHelper.setLocale(this, newLang)
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
        this.finish()
    }

    private fun getAnimateForIn():Int{
        val language = LocaleHelper.getLanguage(this)
        if(language == "fa"){
            return R.anim.fa_anim_in
        }else{
            return R.anim.en_animate_in
        }
    }

    private fun getAnimateForOut():Int{
        val language = LocaleHelper.getLanguage(this)
        if(language == "fa"){
            return R.anim.fa_anim_semi_out
        }else{
            return R.anim.en_anim_semi_out
        }
    }
}
