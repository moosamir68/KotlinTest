package com.example.moosamir.myapplicationkotlin.Activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.ViewModel.ProfileViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.ProfileViewModelDelegate

class ProfileFragment : Fragment(), ProfileViewModelDelegate {

    val viewModel = ProfileViewModel(this)

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_artist, container, false)
        this.initUI()
        return rootView
    }

    private fun initUI(){

    }
}
