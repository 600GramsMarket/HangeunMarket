package com.example.hangeunmarket.ui.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hangeunmarket.databinding.FragmentMypageBinding
import com.example.hangeunmarket.ui.dto.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Firebase database reference
    private lateinit var database: DatabaseReference

    //Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        //Firebase init
        database = Firebase.database.reference
        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //현재 사용자의 정보 업데이트
        getUserInfo() //sharedPreference에서 정보 얻어오기
        //setUserInfo()
    }


    //firebase에서 직접 데이터 얻어와서 반영 => 서버 딜레이로 폐기
    private fun setUserInfo(){
        val uid = auth.currentUser?.uid!!
        //데이터 한번 읽기
        //https://firebase.google.com/docs/database/android/read-and-write?hl=ko#read_data_once
        database.child("user").child(uid).get().addOnSuccessListener {
            // uid로 사용자 정보 읽어오기 성공 시
            Log.i("firebase", "Got value ${it.value}")
            val user = it.value as Map<String, Any> // 데이터를 Map으로 캐스팅
            val email = user["email"] as String? // email 값을 추출
            val name = user["name"] as String? //name
            val school = user["school"] as String? //school
            // null이 아니면 설정, null이면 기본 문구 설정
            binding.tvMyEmail.text = email ?: "이메일 정보 없음"
            binding.tvMyName.text = name ?: "이름 정보 없음"
            binding.tvMySchool.text = school ?: "소속 정보 없음"

        }.addOnFailureListener {
            // 실패 시
            Log.e("firebase", "Error getting data", it)
        }

    }


    //sharedPreference로부터 사용자 정보 얻어오기
    private fun getUserInfo(){
        val sharedPref = activity?.getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        sharedPref?.apply {
            binding.tvMyEmail.text = getString("userEmail","이메일 정보 없음")
            binding.tvMyName.text = getString("userName","이름 정보 없음")
            binding.tvMySchool.text = getString("userSchool","학교 정보 없음")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}