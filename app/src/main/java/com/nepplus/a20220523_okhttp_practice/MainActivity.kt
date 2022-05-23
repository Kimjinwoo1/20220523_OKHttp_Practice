package com.nepplus.a20220523_okhttp_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.a20220523_okhttp_practice.databinding.ActivityMainBinding
import com.nepplus.a20220523_okhttp_practice.utils.ServerUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()
    }

    fun setupEvents(){
    binding.loginBtn.setOnClickListener {
        val inputEmail = binding.emailEdt.text.toString()
        val inputPw = binding.passwordEdt.text.toString()

        ServerUtil.postRequestLogin(inputEmail, inputPw)

        ServerUtil.BASE_URL


    }
    }

    fun setValues(){

    }
}