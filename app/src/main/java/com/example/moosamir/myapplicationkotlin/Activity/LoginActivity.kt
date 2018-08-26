package com.example.moosamir.myapplicationkotlin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.moosamir.myapplicationkotlin.ViewModel.LoginViewModel
import com.example.moosamir.myapplicationkotlin.ViewModel.LoginViewModelDelegate
import kotlinx.android.synthetic.main.activity_login.*
import android.view.View
import com.example.moosamir.myapplicationkotlin.R


class LoginActivity : AppCompatActivity(), LoginViewModelDelegate {

    val viewModel = LoginViewModel(this)

    init {
        print("")
    }
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

        this.username.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                viewModel.setUsername(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                print("before text change")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                print("on text change")
            }
        })

        this.password.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                viewModel.setPassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                print("before text change")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                print("on text change")
            }
        })
    }

    private fun userDidTapOnLoginButton(){
        val result = this.viewModel.checkValidateData()
        val validate = result.first
        val errorTitle = result.second
        if(validate){
            this.viewModel.login()
            this.showLoading()
            return
        }

        Toast.makeText(this@LoginActivity, errorTitle, Toast.LENGTH_SHORT).show()
        return
    }

    private fun showLoading(){
        this.loadingBoxView.visibility = View.VISIBLE
    }

    private fun changeValueUsernameEditText(){

    }
    private fun userDidTapOnRegisterButton(){
        //show register page form login controller
        val register = Intent(this, RegisterActivity::class.java)
        startActivity(register)
    }

    //login view model delegate
    override fun sucessLogin() {
        //show next page
        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
    }

    override fun faildLogin() {
        this.loadingBoxView.visibility = View.INVISIBLE
        Toast.makeText(this@LoginActivity, this.viewModel.erroDescription, Toast.LENGTH_SHORT).show()
    }
}
