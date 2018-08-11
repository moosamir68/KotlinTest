package com.example.moosamir.myapplicationkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    var songsFragment:SongsFragment = SongsFragment.newInstance()
    var albumsFragment:AlbumsFragment = AlbumsFragment.newInstance()
    var artinstsFragment:ArtistsFragment = ArtistsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.initUI()
    }

    private fun initUI() {
//        this.titleLabel.text = "Moosa's app"

        toolbar = supportActionBar!!
        this.navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_songs -> {
                toolbar.title = "Songs"

                openFragment(this.songsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_albums -> {
                toolbar.title = "Albums"
                openFragment(this.albumsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_artists -> {
                toolbar.title = "Artists"
                openFragment(this.artinstsFragment)
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
}
