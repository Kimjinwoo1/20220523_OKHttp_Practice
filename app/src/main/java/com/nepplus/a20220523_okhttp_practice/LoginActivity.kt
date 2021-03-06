package com.nepplus.a20220523_okhttp_practice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.a20220523_okhttp_practice.databinding.ActivityLoginBinding
import com.nepplus.a20220523_okhttp_practice.utils.ContextUtil
import com.nepplus.a20220523_okhttp_practice.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents(){
        binding.autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            ContextUtil.setAutoLogin(mContext,isChecked)
        }


        binding.signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }
    binding.loginBtn.setOnClickListener {
        val inputEmail = binding.emailEdt.text.toString()
        val inputPw = binding.passwordEdt.text.toString()

        ServerUtil.postRequestLogin(inputEmail, inputPw, object  : ServerUtil.Companion.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {

//                UI입장에서, 로그인 결과를 받아서 처리할 코드
//                서버에 다녀오고 나서 실행: OkHttp 라이브러리가 자동으로 백그라운드에서 돌도록 만들어둔 코드
                val code = jsonObj.getInt("code")

                if (code == 200){

                    val dataObj = jsonObj.getJSONObject("data")
                    val userObj = dataObj.getJSONObject("user")
                    val nickname = userObj.getString("nick_name")
                    val token = dataObj.getString("token")

                    ContextUtil.setLoginToken(mContext,token)


                    runOnUiThread {
                        Toast.makeText(mContext, "${nickname}님 환영합니다", Toast.LENGTH_SHORT).show()
                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }

                }
                else{
                    val message = jsonObj.getString("message")
                    runOnUiThread {
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()


                    }
                }
            }
        })

        ServerUtil.BASE_URL


    }
    }



    override fun setValues(){
        binding.autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }
}