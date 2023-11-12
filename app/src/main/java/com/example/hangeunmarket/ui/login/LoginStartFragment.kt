package com.example.hangeunmarket.ui.login

import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.media.Image
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.hangeunmarket.R
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

// 앱의 초기화면에 해당
class LoginStartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_start, container, false)
    }

    // view가 다 생성된 이후 동작 처리 로직 작성
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitleLogoDesign() // 글자 부분 색상 변경 '한' '근' 만 붉은 색으로

        // <시작하기> => 회원가입 로직
        // 'btn_sign_up' 클릭시 SignUpFragment로 교체
        val signUpButton = view.findViewById<TextView>(R.id.btn_sign_up)
        signUpButton.setOnClickListener {
            // 프래그먼트 교체 작업 실행
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, SignUpFragment()) // 회원가입 페이지로 이동
                addToBackStack(null) // Back stack에 추가
                commit()
            }
        }


        //Glide test
//        val imageView = view.findViewById<ImageView>(R.id.iv_meat_logo)
//        Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);


        //Fireabse Storage test
//        val imageView = view.findViewById<ImageView>(R.id.iv_meat_logo)
//        // 이미지 참조 가져오기
//        val imageRef = Firebase.storage.getReferenceFromUrl(
//            "gs://hangeunmarket.appspot.com/bugi.png"
//        )
//        displayImageRef(imageRef,imageView)

        // <이미 계정이 있나요? 로그인> 로직
        // 'text_button_sign_in' 클릭 시 SignInFragment로 교체
        val signInButton = view.findViewById<TextView>(R.id.text_button_sign_in)
        signInButton.setOnClickListener {
            // 프래그먼트 교체 작업 실행
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, SignInFragment()) // 로그인 페이지로 이동
                addToBackStack(null) // Back stack에 추가
                commit()
            }
        }

    }

    // 스토리지에서 이미지 가져와서 표시하기
    private fun displayImageRef(imageRef:StorageReference?,view:ImageView){
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it,0,it.size)
            view.setImageBitmap(bmp) // bitmap으로 이미지뷰에 이미지 설정
        }?.addOnFailureListener {
            //Failed to download the image
        }
    }


    private fun setTitleLogoDesign(){
//        val logoTitle = binding.tvTitleLogo
        val logoTitle = view?.findViewById<TextView>(R.id.tv_title_logo)
        // 텍스트 가져오기
        val text = "한성대 근처에서 직거래"
        // SpannableString 생성
        val spannableString = SpannableString(text)
        // '한'의 글자색 변경
        val colorSpan1 = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bright_red))
        spannableString.setSpan(colorSpan1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '한'
        // '한'의 스타일을 볼드로 변경
        val styleSpan1 = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan1, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '한'

        // '근'의 글자색 변경
        val colorSpan2 = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.bright_red))
        spannableString.setSpan(colorSpan2, 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '근'
        // '근'의 스타일을 볼드로 변경
        val styleSpan2 = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan2, 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // '근'

        // 변경한 글자 적용
        logoTitle?.text = spannableString
    }
}