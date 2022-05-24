package com.nepplus.a20220523_okhttp_practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nepplus.a20220523_okhttp_practice.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

        lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

    }


}