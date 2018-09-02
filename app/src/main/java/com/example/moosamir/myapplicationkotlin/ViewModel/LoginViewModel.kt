package com.example.moosamir.myapplicationkotlin.ViewModel

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.hardware.usb.UsbRequest
import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.example.moosamir.myapplicationkotlin.R
import com.example.moosamir.myapplicationkotlin.Service.APIClient
import com.example.moosamir.myapplicationkotlin.Service.APIClientDelegate
import com.example.moosamir.myapplicationkotlin.Service.CacheManager
import com.example.moosamir.myapplicationkotlin.Service.MMError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LoginViewModelDelegate{
    fun sucessLogin()
    fun faildLogin()
}

public class LoginViewModel(delegate: LoginViewModelDelegate, activity:Activity):APIClientDelegate {
    var userNameL:String? = null
    var passwordL:String? = null
    var erroDescription:String = ""

    var userAccount:UserAccount? = null

    var delegate:LoginViewModelDelegate
    var activity:Activity

    init {
        this.delegate = delegate
        this.activity = activity
    }

    fun canShowMain(context: Context):Boolean{
        val account = CacheManager(context).getAccount()
        if(account == null){
            return false
        }else{
            return true
        }
    }

    public fun setUsername(usernameP:String?){
        this.userNameL = usernameP
    }

    public final fun setPassword(passwordP:String?){
        this.passwordL = passwordP
    }

    public fun checkValidateData():Pair<Boolean, String>{
        if(userNameL == "" || userNameL == null){
            return Pair(false, activity.getString(R.string.please_enter_username))
        }else if(passwordL == "" || passwordL == null){
            return Pair(false, activity.getString(R.string.please_enter_password))
        }

        return Pair(true, "")
    }

    public fun login(){
        val apiClient = APIClient.getInstance()
        val loginApi = apiClient.retrofit.create(INTNetworkApi::class.java)
        val request = loginApi.login(userNameL!!, passwordL!!)
        val account = UserAccount("moosa", "moosamir")
        this.userAccount = account
        this.delegate.sucessLogin()

//        apiClient.sendCall<UserAccount>(request, this)
    }

    //API Client delegate
    override fun <T> sucessfulyRequest(response: T) {
        this.userAccount = response as UserAccount
        this.delegate.sucessLogin()
    }

    override fun <T> faildRequest(error: MMError<T>) {
        this.erroDescription = error.errorDescription
        this.delegate.faildLogin()
    }
}
