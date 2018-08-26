package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.moosamir.myapplicationkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var songsFragment: SongsFragment = SongsFragment.newInstance()
    var albumsFragment: AlbumsFragment = AlbumsFragment.newInstance()
    var artinstsFragment: ArtistsFragment = ArtistsFragment.newInstance()
    var profileFragment: ProfileFragment = ProfileFragment.newInstance()

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.initUI()
    }

    private fun initUI() {
        this.navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_songs -> {
                this.toolbar.setTitle("Songs")

                openFragment(this.songsFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_albums -> {
                this.toolbar.setTitle("Albums")
                openFragment(this.albumsFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_artists -> {
                this.toolbar.setTitle("Artists")
                openFragment(this.artinstsFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_profile -> {
                this.toolbar.setTitle("Profile")
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
}
