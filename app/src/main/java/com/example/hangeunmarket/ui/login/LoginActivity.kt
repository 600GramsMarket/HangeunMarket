package com.example.hangeunmarket.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangeunmarket.databinding.ActivityLoginBinding
import com.example.hangeunmarket.ui.MainActivity

//로그인을 수행할 엑티비티 => 로그인 정보가 이미 앱에 있다면 자동 로그인?(나중에 구현할 것)
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            //0. Intent 생성(요청 생성)
            //1. 현재 앱의 Context와 이동할 엑티비티의 클래스
            //의도 : LoginActivity에서 MainActivity로 화면 전환
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent) //시스템에 요청 전송
        }
    }
}