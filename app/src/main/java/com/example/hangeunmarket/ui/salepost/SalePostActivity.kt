package com.example.hangeunmarket.ui.salepost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.example.hangeunmarket.R


//판매 글 보기와 관련된 로직 처리
class SalePostActivity : AppCompatActivity() {

/*tv_sale_title
* tv_sale_place
* tv_sale_item_info
* tv_sale_price //판매 가격
* */

    private lateinit var tvSaleTitle : TextView
    private lateinit var tvSalePlace : TextView
    private lateinit var tvSaleItemInfo : TextView
    private lateinit var tvSalePrice : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post)

        tvSaleTitle = findViewById(R.id.tv_sale_title)
        tvSalePlace = findViewById(R.id.tv_sale_place)
        tvSaleItemInfo = findViewById(R.id.tv_sale_item_info)
        tvSalePrice = findViewById(R.id.tv_sale_price)


        /*
        *   intent.putExtra("saleTitle",saleTitle.text)
            intent.putExtra("salePrice",salePrice.text)
            intent.putExtra("saleItemInfo",saleItemInfo.text)
            intent.putExtra("salePlace",salePlace.text)
        * */

        var saleTitle = intent.getStringExtra("saleTitle")
        var salePrice = intent.getStringExtra("salePrice")
        var saleItemInfo = intent.getStringExtra("saleItemInfo")
        var salePlace = intent.getStringExtra("salePlace")

        Log.d("get data : ","${saleTitle},${salePrice},${saleItemInfo},${salePlace}")

        //화면에 정보 뿌리기
        tvSaleTitle.text = saleTitle
        tvSalePlace.text = salePlace
        tvSaleItemInfo.text = saleItemInfo
        tvSalePrice.text = salePrice

    }
}