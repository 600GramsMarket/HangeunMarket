package com.example.hangeunmarket.ui.salepost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.chat.ChattingRoomActivity
import com.google.firebase.Firebase
import com.google.firebase.storage.storage


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
    private lateinit var tvSellerName : TextView
    private lateinit var ivSaleItemImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post)

        tvSaleTitle = findViewById(R.id.tv_sale_title)
        tvSalePlace = findViewById(R.id.tv_sale_place)
        tvSaleItemInfo = findViewById(R.id.tv_sale_item_info)
        tvSalePrice = findViewById(R.id.tv_sale_price)
        tvSellerName = findViewById(R.id.tv_user_name)
        ivSaleItemImage = findViewById(R.id.iv_sale_item_image)


        /*
        *   intent.putExtra("saleTitle",saleItem.saleTitle) //제목
            intent.putExtra("salePrice",saleItem.salePrice) //가격
            intent.putExtra("saleItemInfo",saleItem.saleContent) //물건 정보
            intent.putExtra("salePlace",saleItem.salePlace) //판매장소
            intent.putExtra("sellerUId",saleItem.sellerUID) //판매자 UID
            intent.putExtra("sellerName",saleItem.sellerName) //판매자 이름
            intent.putExtra("saleItemImage",saleItem.saleItemImage) //이미지url
        * */
        Log.d("ImageTest","엑티비티 이동")

        var saleTitle = intent.getStringExtra("saleTitle") // 제목
        var salePrice = intent.getStringExtra("salePrice") // 가격
        var saleItemInfo = intent.getStringExtra("saleItemInfo") // 물건 정보
        var salePlace = intent.getStringExtra("salePlace") // 판매장소
        var sellerName = intent.getStringExtra("sellerName") // 판매자 이름
        var sellerUId = intent.getStringExtra("sellerUId") // 판매자 UID
        var saleItemImageName = intent.getStringExtra("saleItemImage") //판매 상품 이미지 이름
        Log.d("ImageTest","이미지 추출 ${saleItemImageName}")


        Log.d("get data : ","${saleTitle},${salePrice},${saleItemInfo},${salePlace}")

        //화면에 정보 뿌리기
        tvSaleTitle.text = saleTitle
        tvSalePlace.text = "판매장소 : ${salePlace}"
        tvSaleItemInfo.text = saleItemInfo
        tvSalePrice.text = salePrice
        tvSellerName.text = sellerName

        // 이미지가 존재할 경우에만 이미지 불러오기
        if (saleItemImageName != null){
            // Firebase Storage에서 이미지 참조 가져오기
            val storageReference = Firebase.storage.reference.child(saleItemImageName)

            // 다운로드 URL을 가져와 Glide로 로드하기
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri.toString())
                    .into(ivSaleItemImage)
            }.addOnFailureListener {
                // 에러 처리
            }
        }


        //
        val popup = findViewById<ImageView>(R.id.iv_item_setting)
        popup.setOnClickListener {
            showPopupMenu(it) //it == popup
        }




        //채팅 버튼 클릭시
        /*
        receiverName = intent.getStringExtra("name").toString() //상대방 이름
        receivedUid = intent.getStringExtra("uId").toString() //상대방 uId
        * */
        var goChatting = findViewById<Button>(R.id.btn_go_chatting)
        goChatting.setOnClickListener {
            //채팅방으로 넘어가기
            val intent = Intent(this@SalePostActivity,ChattingRoomActivity::class.java)
            intent.putExtra("name",sellerName) //판매자 이름
            intent.putExtra("uId",sellerUId) //판매자 uId

            startActivity(intent) //엑티비티 이동
        }

        //뒤로 가기
        val btnBack = findViewById<ImageView>(R.id.iv_back)
        btnBack.setOnClickListener {
            finish()
        }


    }


    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        //어떤 메뉴를 띄워줄지
        inflater.inflate(R.menu.menu_popup, popupMenu.menu)

        //메뉴 클릭시 동작 정의
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item_all -> {
                    // 수정 클릭시
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}