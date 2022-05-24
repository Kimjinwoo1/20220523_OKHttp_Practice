package com.nepplus.a20220523_okhttp_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nepplus.a20220523_okhttp_practice.utils.ContextUtil
import com.nepplus.a20220523_okhttp_practice.utils.ServerUtil
import org.json.JSONObject

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

        var isTokenOk = false

        ServerUtil.getRequestUserInfo(mContext,object : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                isTokenOk = (code == 200)
            }})

        myHandler.postDelayed({
//            자동로그인을 해도되는가?
//            1) 사용자가 자동로그인을 한다고 했는가?
            val isAutoLoginOk = ContextUtil.getAutoLogin(mContext)

//            2)token이 있는가?(토큰의 유효성 Text)
//            2-1) 저장된 토큰이 있는지?

//            2-2) 그토큰이 유효한가?
            if(isAutoLoginOk && isTokenOk){
//                둘다 ok 바로 메인화면
                val myIntent = Intent(mContext,MainActivity::class.java)
                startActivity(myIntent)
                finish()
            }
            val myIntent = Intent(mContext,LoginActivity::class.java)
            startActivity(myIntent)
            finish()

            }, 2500)




    }
}