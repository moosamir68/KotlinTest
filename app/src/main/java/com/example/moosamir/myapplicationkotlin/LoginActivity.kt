package com.example.moosamir.myapplicationkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.initUI()
    }

    private fun initUI(){
        this.titleLabel.text = "Your welcome"
        this.loginButton.setOnClickListener {
            this.userDidTapOnLoginButton()
        }

        this.registerButton.setOnClickListener {
            this.userDidTapOnRegisterButton()
        }
    }

    private fun userDidTapOnLoginButton(){
        val result = this.checkValidateData()
        val validate = result.first
        val errorTitle = result.second
        if(validate){
            //show next page
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)
            return
        }

        Toast.makeText(this@LoginActivity, errorTitle, Toast.LENGTH_SHORT).show()
        return
    }

    private fun userDidTapOnRegisterButton(){
        //show register page form login controller
        val register = Intent(this, RegisterActivity::class.java)
        startActivity(register)
    }

    private fun checkValidateData():Pair<Boolean, String?>{
        val userName = this.username.text.toString()
        val passwrod = this.password.text.toString()

        if(userName == ""){
            return  Pair(false, "Please enter username")
        }else if(passwrod == ""){
            return  Pair(false, "Please enter password")
        }

        return  Pair(true, null)
    }
}
