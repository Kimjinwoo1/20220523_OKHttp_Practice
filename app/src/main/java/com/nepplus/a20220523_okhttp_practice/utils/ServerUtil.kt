package com.nepplus.a20220523_okhttp_practice.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {


//    서버유틸로 돌아온 응답을 => 엑티비티에서 처리하도록, 일처리 넘기기
//    나에게 생긴 일을 > 다른 클래스에게 처리 요청 : interface 활용



//    서버에 Requeset 날리는 역할
//    함수를 만들려고 하는데, 어떤 객체가 실행해도 결과만 잘 나오면 그만임 함수
//    코틀린에서 JAVA의 static에 해당하는 개념? Companion object {  } 에 만들자


    companion object {

        interface JsonResponseHandler {
            fun onResponse(jsonObj: JSONObject)
        }

        //        서버 컴퓨터 주소만 변수로 저장 (관리 일원화)
        val BASE_URL = "http://54.180.52.26"

        //        로그인 기능 호출 함수
        fun postRequestLogin(email: String, pw: String, handler: JsonResponseHandler?) {

//            Request 제작 -> 실제 호출 -> 서버의 응답을 화면에 전달
//            제작1) 어느 주소 (접url)로 근 할지 ? => 서버주소 + 기능 주소
            val urlString = "${BASE_URL}/user"

//            제작 2) 파라미터 담아두기 => 어떤 이름표 / 어느 공간에
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//           제작 3)  모든 Request정보를 종합한 객체 생성 ( 어떤주소로 + 어떤 메쏘드로 + 어떤 파라미터로 )
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

//            종합한 Request도 실제로 호출을 해줘야 API 호출이 실행(Intent StartActivity 같은 동작 필요)

//            실제호출 : 앱이 클라이언트로써 동작 > OKHttpClient 클래스활용
            val client = OkHttpClient()

//          OkHttpclient 객체 이용 > 서버에 로그인 기능 실제 호출
//          호출을 했으면 , 서버가 수행한 결과 (Response)를 받아서 처리
//          =>서버에 다녀와서 할일을 등록 :  enqueue ( Callback)

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                //
                override fun onResponse(call: Call, response: Response) {


                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)    //.string()1회용 딱 한번만 사용할수있다.

                    Log.d("서버 테스트", jsonObj.toString())

//                연습  : 로그인 성공 / 실패에 따른 로그 추출
//                "code" 이름표의 Int를 추출, 그값을 조건문 확인

//                val code = jsonObj.getInt("code")
//
//                if(code == 200){
////                    로그인 시도 성공
//                    Log.d("로그인 시도","성공")
//                    val dataObj = jsonObj.getJSONObject("data")
//                    val userObj = dataObj.getJSONObject("user")
//                    val nickname = userObj.getString("nick_name")
//
//                    Log.d("로그인 성공","닉네임 : $nickname" )
//                }
//                else{
//                    Log.d("로그인 시도","실패")
//                    val message = jsonObj.getString("message")
//                    Log.d("실패 사유",message)
//                }
//                 실제 : handler 변수에 jsonObj를 가지고 화면에서 어떻게 처리할지 계획이 들어있다.
//                    계획이 있을때만
                    handler?.onResponse(jsonObj)

                }
            })

        }
//        회원 가입 기능 호출 함수
        fun putRequestSignUp( email :String, pw :String, nickname : String, handler: JsonResponseHandler?){

            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email",email)
                .add("password", pw)
                .add("nick_name",nickname)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    handler?.onResponse(jsonObj)
                }
            })

        }
//        중복 검사 기능 호출함수
        fun getRequestUserCheck( type : String , value : String, handler : JsonResponseHandler? ){



//    밑에 안될경우 val urlBuilder = HttpUrI.parse("${BASE.URL}/user_Check"
        val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            .addEncodedQueryParameter("type",type)
            .addEncodedQueryParameter("value",value)
            .build()

        val urlString = urlBuilder.toString()

//        Log.d("완성된 url",urlString)

        val request = Request.Builder()
            .url(urlString)
            .get()
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object  : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val bodyString = response.body!!.string()
                val jsonObj = JSONObject(bodyString)
                Log.d("서버응답", jsonObj.toString())

                handler?.onResponse(jsonObj)

            }
        })

        }

        //    사용자 정보 조회 (Token값의 유효성 검사)
        fun getRequestUserInfo(context : Context,handler : JsonResponseHandler?){
            val token = ContextUtil.getLoginToken(context)

            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
                .build()


            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token",token)
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)


                }
            })

        }
//
        fun getRequestMainInfo(context : Context,handler : JsonResponseHandler?){
    val token = ContextUtil.getLoginToken(context)

    val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
        .build()


    val urlString = urlBuilder.toString()

    val request = Request.Builder()
        .url(urlString)
        .get()
        .header("X-Http-Token",token)
        .build()

    val client = OkHttpClient()

    client.newCall(request).enqueue(object : Callback{
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
            val jsonObj = JSONObject(response.body!!.string())
            Log.d("서버응답", jsonObj.toString())

            handler?.onResponse(jsonObj)


        }
    })

}
    }
}
