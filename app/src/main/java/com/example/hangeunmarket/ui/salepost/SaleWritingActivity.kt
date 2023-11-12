package com.example.hangeunmarket.ui.salepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.ActivitySaleWritingBinding


//판매 글 쓰기와 관련된 로직 처리
class SaleWritingActivity : AppCompatActivity() {

    private var _binding: ActivitySaleWritingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var saleTitle : EditText //판매 제목
    private lateinit var salePrice : EditText //판메 가격
    private lateinit var salePlace : EditText //판매 장소
    private lateinit var saleItemInfo : EditText //상품 설명


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sale_writing)

        saleTitle = findViewById(R.id.et_set_title) //판매 글 제목
        salePrice = findViewById(R.id.et_set_price) //판매 가격
        saleItemInfo = findViewById(R.id.et_set_explain) //상품 설명
        salePlace = findViewById(R.id.et_set_place) //판매 희망 장소


        var btnWriteDone = findViewById<Button>(R.id.btn_write_done)
        //글 쓰기 완료시
        btnWriteDone.setOnClickListener{
            val intent = Intent(this@SaleWritingActivity,SalePostActivity::class.java)


            //intent에 데이터 삽입 => 판매글 엑티비티로 넘기기
            intent.putExtra("saleTitle",saleTitle.text.toString())
            intent.putExtra("salePrice",salePrice.text.toString())
            intent.putExtra("saleItemInfo",saleItemInfo.text.toString())
            intent.putExtra("salePlace",salePlace.text.toString())

            startActivity(intent) //작성한 글 확인을 위해 넘어가기
            finish() //현재 엑티비티는 종료처리
        }

        var backBtn = findViewById<ImageView>(R.id.iv_back)
        backBtn.setOnClickListener {
            finish() // 백 스택
        }
    }
}