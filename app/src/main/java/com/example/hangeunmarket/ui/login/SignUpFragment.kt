package com.example.hangeunmarket.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sign


//회원가입 로직 작성
class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSingUp = view.findViewById<Button>(R.id.btn_signUp)
        btnSingUp.setOnClickListener {
            val userEmail = view.findViewById<EditText>(R.id.et_email)?.text.toString()
            val userPassword = view.findViewById<EditText>(R.id.et_password)?.text.toString()

            //유효성 검사 로직 작성 필요
            //아이디와 비밀번호를 모두 입력했는지, 이메일의 형식을 지켰는지, 비밀번호는 특수문자를 포함하고있는지 등
            signUp(userEmail,userPassword)
        }

    }

    //회원가입 수행 => 회원가입에 성공하면 메인 엑티비티로 이동
    private fun signUp(userEmail: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공, 메인 액티비티로 이동
                    val intent = Intent(this@SignUpFragment.activity, MainActivity::class.java)
                    // 회원가입 시 입력한 이메일을 인텐트에 담아 메인 액티비티로 전달
                    intent.putExtra("userEmail", userEmail)
                    startActivity(intent) // 메인 액티비티로 이동
                    // 현재 액티비티 종료
                    activity?.finish()
                } else {
                    // 회원가입 실패
                    task.exception?.let {
                        Log.d("SignUpError", "SignUp failed", it)
                        Toast.makeText(activity, "회원가입 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }



}