package com.example.moosamir.myapplicationkotlin.ViewModel

import android.accounts.Account
import com.example.moosamir.myapplicationkotlin.Interface.INTNetworkApi
import com.example.moosamir.myapplicationkotlin.Model.UserAccount
import com.example.moosamir.myapplicationkotlin.Service.APIClient
import com.example.moosamir.myapplicationkotlin.Service.MMError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LoginViewModelDelegate{
    fun sucessLogin()
    fun faildLogin()
}

public class LoginViewModel(delegate: LoginViewModelDelegate) {
    var userNameL:String? = null
    var passwordL:String? = null
    var erroDescription:String = ""

    var userAccount:UserAccount? = null

    var delegate:LoginViewModelDelegate

    init {
        this.delegate = delegate
    }
    public fun setUsername(usernameP:String?){
        this.userNameL = usernameP
    }

    public final fun setPassword(passwordP:String?){
        this.passwordL = passwordP
    }

    public fun checkValidateData():Pair<Boolean, String>{
        if(userNameL == "" || userNameL == null){
            return Pair(false, "Please enter username")
        }else if(passwordL == "" || passwordL == null){
            return Pair(false, "please enter password")
        }

        return Pair(true, "")
    }

    public fun login(){

        val loginApi = APIClient.client.create(INTNetworkApi::class.java)

        val response = loginApi.login(userNameL!!, passwordL!!)

        response.enqueue(object : Callback<UserAccount> {
            override fun onFailure(call: Call<UserAccount>?, t: Throwable) {
                erroDescription = MMError<UserAccount>(null, null, t, null).errorDescription
                delegate.faildLogin()
            }

            override fun onResponse(call: Call<UserAccount>, response: Response<UserAccount>) {
                if(response.body() != null){
                    userAccount = response.body()
                    delegate.sucessLogin()
                }else if(response.errorBody() != null){
                    erroDescription = MMError<UserAccount>(null, null, null, response).errorDescription
                    delegate.faildLogin()
                }else{
                    erroDescription = "Can not connect to server"
                    delegate.faildLogin()
                }
            }

        })
    }
}
