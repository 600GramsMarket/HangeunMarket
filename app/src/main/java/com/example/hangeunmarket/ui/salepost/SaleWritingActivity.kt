package com.example.hangeunmarket.ui.salepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        var btnWriteDone = findViewById<Button>(R.id.btn_write_done)

        //글 쓰기 완료시
        btnWriteDone.setOnClickListener{
            val intent = Intent(this@SaleWritingActivity,SalePostActivity::class.java)

            //번들에 데이터 넣어줘야함

            startActivity(intent) //작성한 글 확인을 위해 넘어가기
            finish() //현재 엑티비티는 종료처리
        }
    }
}