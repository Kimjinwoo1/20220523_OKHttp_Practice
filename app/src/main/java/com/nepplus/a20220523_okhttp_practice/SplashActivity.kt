package com.nepplus.a20220523_okhttp_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({
//            자동로그인을 해도되는가?
//            1) 사용자가 자동로그인을 한다고 했는가?

//            2)token이 있는가?(토큰의 유효성 Text)
//            2-1) 저장된 토큰이 있는지?

//            2-2) 그토큰이 유효한가?


        },2500)

    }
}