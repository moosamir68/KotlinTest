package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moosamir.myapplicationkotlin.Helper.LocaleHelper
import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.ViewModel.ProfileViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.ProfileViewModelDelegate
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

interface ProfileFragmentDelegate{
    fun changeLanguageTo(newLang:String)
}

public class ProfileFragment : Fragment(), ProfileViewModelDelegate {

    val viewModel:ProfileViewModel
    var delegate:ProfileFragmentDelegate? = null

    init {
        this.viewModel = ProfileViewModel(this)
    }

    override fun onStart() {
        super.onStart()
        this.initUI()
    }

    companion object {
        fun newInstance(delegateP:ProfileFragmentDelegate): ProfileFragment{
            val profileFragment = ProfileFragment()
            profileFragment.delegate = delegateP
            return profileFragment
        }
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
        val loginActivity = Intent(this.activity, LoginActivity::class.java)
        startActivity(loginActivity)
        this.activity!!.finish()
    }

    private fun userDidTapOnChangeLanguageButton(){

        this.profile_progress.visibility = View.VISIBLE
        var newLanguageCode = LocaleHelper.getLanguage(this.activity!!)

        if(newLanguageCode == "fa"){
            newLanguageCode = "en"
        }else{
            newLanguageCode = "fa"
        }

        this.delegate!!.changeLanguageTo(newLanguageCode)
    }
}
