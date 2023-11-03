package com.example.hangeunmarket.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangeunmarket.R

//로그인을 수행할 엑티비티 => 로그인 정보가 이미 앱에 있다면 자동 로그인?(나중에 구현할 것)
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // LoginStartFragment를 첫번째 프래그먼트로 설정
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, LoginStartFragment()) //container는 FrameLayout의 id
            commit()
        }
    }
}