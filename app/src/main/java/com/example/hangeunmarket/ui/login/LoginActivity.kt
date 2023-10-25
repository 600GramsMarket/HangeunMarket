package com.example.hangeunmarket.ui.login

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.example.hangeunmarket.R
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

        val logoTitle = binding.tvTitleLogo
        // 텍스트 가져오기
        val text = "한성대 근처에서 직거래"
        // SpannableString 생성
        val spannableString = SpannableString(text)
        // '한'의 글자색 변경
        val colorSpan1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.bright_red))
        spannableString.setSpan(colorSpan1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '한'
        // '한'의 스타일을 볼드로 변경
        val styleSpan1 = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '한'

        // '근'의 글자색 변경
        val colorSpan2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.bright_red))
        spannableString.setSpan(colorSpan2, 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '근'
        // '근'의 스타일을 볼드로 변경
        val styleSpan2 = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan2, 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '근'

        // 변경한 글자 적용
        logoTitle.text = spannableString
    }
}