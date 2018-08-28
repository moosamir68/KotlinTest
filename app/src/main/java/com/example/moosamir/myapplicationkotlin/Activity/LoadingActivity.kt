package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.moosamir.myapplicationkotlin.Helper.LocaleHelper
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.ViewModel.LoadingViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.LoadingViewModelDelegate


class LoadingActivity : AppCompatActivity(), LoadingViewModelDelegate {

    val viewModel = LoadingViewModel(this)

    override fun onResume() {
        super.onResume()
        this.initUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
    }

    private fun initUI(){
        if(this.viewModel.canShowMain(this)){
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }else{
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!));
    }
}
