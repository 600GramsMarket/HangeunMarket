package com.example.hangeunmarket.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


// 로그인 로직 => 파이어베이스와 연동하여 사용자가 입력한 로그인 정보가 맞는지 확인하여
class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // <로그인> 로직 작성
        // 사용자가 입력한 아이디와 비밀번호가 올바른지 확인하고, 로그인 처리
        val signInButton = view.findViewById<TextView>(R.id.btn_sign_in)

        //로그인 버튼 클릭시 로그인 시도
        signInButton.setOnClickListener {
            //사용자가 입력한 아이디와 비밀번호 받아오기
            val userEmail = view.findViewById<EditText>(R.id.et_id)?.text.toString()
            val password = view.findViewById<EditText>(R.id.et_password)?.text.toString()

            //유효성 검사 로직 작성
            //
            // 사용자가 입력한 아이디와 비밀번호가 올바른지 확인
            // 사용자가 값을 입력했는지 확인
            // if - else
            doLogin(userEmail, password)
        }
    }
    private fun doLogin(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(requireActivity()) { // it: Task<AuthResult!>
                if (it.isSuccessful) { //사용자가 입력한 아이디와 비밀번호가 올바를 경우
                    // Intent생성
                    val intent = Intent(this@SignInFragment.activity,MainActivity::class.java)
                    // 사용자가 입력한 아이디 => intent에 담아서 넘기는 로직 작성 필요
                    //
                    //
                    startActivity(intent) // 메인 엑티비티로 이동 => 메인 엑티비티에서 사용자 정보에 따른 로직 처리 필요
                    // 엑티비티를 이동한 후에, 현재 엑티비티는 종료시켜야함(백스택에서 제거하기 위함)
                    activity?.finish()
                } else {
                    Log.d("SignInError", "There is no such Email/Password in Firebase")
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

}