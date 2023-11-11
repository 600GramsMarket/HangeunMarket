package com.example.hangeunmarket.ui.login

import android.content.Context
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
import com.example.hangeunmarket.databinding.FragmentSignInBinding
import com.example.hangeunmarket.databinding.FragmentSignUpBinding
import com.example.hangeunmarket.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


// 로그인 로직 => 파이어베이스와 연동하여 사용자가 입력한 로그인 정보가 맞는지 확인하여
class SignInFragment : Fragment() {

    //Firebase database reference
    private lateinit var database: DatabaseReference

    //Firebase Authentication
    private lateinit var auth: FirebaseAuth

    private lateinit var binding : FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Firebase init
        database = Firebase.database.reference
        auth = Firebase.auth

        // <로그인> 로직 작성
        // 사용자가 입력한 아이디와 비밀번호가 올바른지 확인하고, 로그인 처리
        //val signInButton = view.findViewById<TextView>(R.id.btn_sign_in)

        //로그인 버튼 클릭시 로그인 시도
        binding.btnSignIn.setOnClickListener {
            //사용자가 입력한 아이디와 비밀번호 받아오기
            val userEmail = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

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
                    startActivity(intent) // 메인 엑티비티로 이동 => 메인 엑티비티에서 사용자 정보에 따른 로직 처리 필요
                    saveUserInfoToSharedPreference() // 현재 로그인한 사용자 정보 저장

                    // 엑티비티를 이동한 후에, 현재 엑티비티는 종료시켜야함(백스택에서 제거하기 위함)
                    activity?.finish()
                } else {
                    Log.d("SignInError", "There is no such Email/Password in Firebase")
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //SharedPreference에 사용자 정보 저장
    private fun saveUserInfoToSharedPreference(){
        val uid = auth.currentUser?.uid!!
        //uId를 활용하여 정보 얻어오기
        //https://firebase.google.com/docs/database/android/read-and-write?hl=ko#read_data_once
        database.child("user").child(uid).get().addOnSuccessListener {
            // uid로 사용자 정보 읽어오기 성공 시
            Log.i("firebase", "Got value ${it.value}")
            val user = it.value as Map<String, Any> // 데이터를 Map으로 캐스팅
            // 얻어온 정보들
            val userEmail = user["email"] as String? // email 값을 추출
            val name = user["name"] as String? //name
            val school = user["school"] as String? //school
            // null이 아니면 설정, null이면 기본 문구 설정
            val sharedPref = activity?.getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
            //editor를 사용하여 사용자 정보 저장
            val editor = sharedPref?.edit()
            editor?.apply {
                putString("userName",name?.trim())
                putString("userEmail",userEmail?.trim())
                putString("userSchool",school?.trim())
                apply() //저장
            }

        }.addOnFailureListener {
            // 실패 시
            Log.e("firebase", "Error getting data", it)
        }

    }

}