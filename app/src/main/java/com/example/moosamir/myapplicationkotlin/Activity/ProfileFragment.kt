package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.ViewModel.ProfileViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.ProfileViewModelDelegate
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*

public class ProfileFragment : Fragment(), ProfileViewModelDelegate {

    val viewModel:ProfileViewModel

    init {
        this.viewModel = ProfileViewModel(this)
    }

    override fun onStart() {
        super.onStart()
        this.initUI()
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        return rootView
    }

    private fun initUI(){
        val account = this.viewModel.getProfile(this.context!!)
        name_textview.text = account!!.username

        this.logout_button.setOnClickListener{
            this.userDidTapOnLogoutButton()
        }

        this.change_language_button.setOnClickListener{
            this.userDidTapOnChangeLanguageButton()
        }
    }

    private fun userDidTapOnLogoutButton(){
        this.viewModel.signOut(this.context!!)
        this.activity!!.finish()
    }

    private fun userDidTapOnChangeLanguageButton(){

    }
}
