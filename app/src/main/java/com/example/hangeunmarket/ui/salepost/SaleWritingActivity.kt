package com.example.hangeunmarket.ui.salepost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.ActivitySaleWritingBinding


//판매 글 쓰기와 관련된 로직 처리
class SaleWritingActivity : AppCompatActivity() {

    private var _binding: ActivitySaleWritingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sale_writing)

        //글 쓰기 완료시
    }
}