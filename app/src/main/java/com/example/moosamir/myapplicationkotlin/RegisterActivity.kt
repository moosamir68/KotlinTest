package com.example.moosamir.myapplicationkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.initUI()
    }

    private fun initUI(){
        this.titleLabel.text = "Register page"
        this.loginButton.setOnClickListener {
            this.userDidTapOnLoginButton()
        }

        this.registerButton.setOnClickListener {
            this.userDidTapOnRegisterButton()
        }
    }

    private fun userDidTapOnLoginButton(){
        finish()
//        val login = Intent(this, LoginActivity::class.java)
//        startActivity(login)
    }

    private fun userDidTapOnRegisterButton(){
        //show register page form login controller

        val result = this.checkValidateData()
        val validate = result.first
        val errorTitle = result.second
        if(validate){
            //show next page
            return
        }

        Toast.makeText(this@RegisterActivity, errorTitle, Toast.LENGTH_SHORT).show()
        return
    }

    private fun checkValidateData():Pair<Boolean, String?>{
        val userName = this.username.text.toString()
        val passwrod = this.password.text.toString()
        val rePasswrod = this.rePassword.text.toString()

        if(userName == ""){
            return  Pair(false, "Please enter username")
        }else if(passwrod == ""){
            return  Pair(false, "Please enter password")
        }else if(rePasswrod == ""){
            return  Pair(false, "Please enter confirm password")
        }else if(rePasswrod != passwrod){
            return  Pair(false, "Password and repassword is not equal")
        }

        return  Pair(true, null)
    }
}
