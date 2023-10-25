package com.example.hangeunmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//로그인을 수행할 엑티비티 => 로그인 정보가 이미 앱에 있다면 자동 로그인?(나중에 구현할 것)
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}