package com.example.hangeunmarket.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.main.MainActivity


// 로그인 로직 => 파이어베이스와 연동하여 사용자가 입력한 로그인 정보가 맞는지 확인하여
class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        // <로그인> 로직 작성
        // 사용자가 입력한 아이디와 비밀번호가 올바른지 확인하고, 로그인 처리
        val signInButton = view.findViewById<TextView>(R.id.btn_sign_in)
        signInButton.setOnClickListener {

            //유효성 검사 로직 작성
            //
            // 사용자가 입력한 아이디와 비밀번호가 올바른지 확인
            // if - else

            // Intent생성
            val intent = Intent(this@SignInFragment.activity,MainActivity::class.java)
            // 사용자 정보 Intent에 삽입
            //
            //
            startActivity(intent) // 메인 엑티비티로 이동 => 메인 엑티비티에서 사용자 정보에 따른 로직 처리 필요
            // 엑티비티를 이동한 후에, 현재 엑티비티는 종료시켜야함(백스택에서 제거하기 위함)
            activity?.finish()
        }

        return view
    }

}