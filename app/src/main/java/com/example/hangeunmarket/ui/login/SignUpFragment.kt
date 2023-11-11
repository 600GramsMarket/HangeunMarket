package com.example.hangeunmarket.ui.login

import android.content.Context
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
import com.example.hangeunmarket.databinding.FragmentSignUpBinding
import com.example.hangeunmarket.ui.dto.User
import com.example.hangeunmarket.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

// firebase document
// https://firebase.google.com/docs/build?hl=ko

// firebase realtimedatabase document
// https://firebase.google.com/docs/database/android/start?hl=ko

//회원가입 로직 작성
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    //Firebase Database Reference
    private lateinit var database: DatabaseReference

    //Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference //init database reference
        auth = Firebase.auth //init authentication

        val btnSingUp = view.findViewById<Button>(R.id.btn_signUp)
        btnSingUp.setOnClickListener {
            val userEmail = binding.etEmail.text.toString() // 사용자 이메일
            val userPassword = binding.etPassword.text.toString() // 사용자 비밀번호
            val userName = binding.etName.text.toString() // 사용자 이름
            val userSchool = binding.etSchool.text.toString() // 사용자 소속

            //유효성 검사 로직 작성 필요
            //아이디와 비밀번호를 모두 입력했는지, 이메일의 형식을 지켰는지, 비밀번호는 특수문자를 포함하고있는지 등
            signUp(userEmail,userPassword,userName,userSchool)
        }

    }

    //회원가입 수행 => 회원가입에 성공하면 메인 엑티비티로 이동
    private fun signUp(userEmail: String, password: String, name:String, school:String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공, 메인 액티비티로 이동
                    val intent = Intent(this@SignUpFragment.activity, MainActivity::class.java)
                    // 회원가입 시 입력한 이메일을 인텐트에 담아 메인 액티비티로 전달
                    intent.putExtra("userEmail", userEmail)
                    startActivity(intent) // 메인 액티비티로 이동

                    //RealTimeDatabase에 사용자 정보 추가 => 사용자가 생성된 상태이므로 UID가 부여된 상태임
                    val uId = auth.currentUser?.uid!! //현재 사용자의 uid
                    addUser(userEmail, name, school, uId) //데이터베이스에 사용자 추가

                    //sharedPreference에 사용자 정보 저장
                    saveUserInFoToSharedPreference(userEmail, name, school)

                    activity?.finish() // 현재 액티비티 종료
                } else {
                    // 회원가입 실패
                    task.exception?.let {
                        Log.d("SignUpError", "SignUp failed", it)
                        Toast.makeText(activity, "회원가입 실패: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    //DB에 사용자 정보 추가
    private fun addUser(userEmail: String, name:String, school:String, uId:String){
        database.child("user").child(uId).setValue(User(name,userEmail,school,uId))

    }

    //SharedPreference에 사용자 정보 저장
    private fun saveUserInFoToSharedPreference(userEmail: String, name:String, school:String){
        val sharedPref = activity?.getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        //editor를 사용하여 사용자 정보 저장
        val editor = sharedPref?.edit()
        editor?.apply {
            putString("userName",name)
            putString("userEmail",userEmail)
            putString("userSchool",school)
            apply() //저장
        }
    }


}