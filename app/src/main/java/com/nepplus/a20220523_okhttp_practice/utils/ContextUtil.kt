package com.nepplus.a20220523_okhttp_practice.utils

import android.content.Context

class ContextUtil {

    companion object{

        val prefName = "okHttpPracticePref"

        val AUTO_LOGIN = "AUTO_LOGIN"
        val LOGIN_TOKEN = "LOGIN_TOKEN"

        fun setLoginToken(context : Context, token : String){
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_TOKEN,token).apply()
        }
        fun getLoginToken (context : Context): String{
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            return pref.getString(LOGIN_TOKEN,"")!!
        }

        fun setAutoLogin( context : Context, isAuto : Boolean ){
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, isAuto).apply()
        }

        fun getAutoLogin( context : Context ) : Boolean {
            val pref = context.getSharedPreferences(prefName,Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)

        }

    }

}