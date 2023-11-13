package com.example.hangeunmarket.ui.salepost

import com.example.hangeunmarket.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hangeunmarket.ui.chat.ChattingRoomActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var goChatting:Button
    private lateinit var spinnerSaleStatus: Spinner //판매상태 수정 스피너

    private lateinit var saleItemId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post)

        tvSaleTitle = findViewById(R.id.tv_sale_title)
        tvSalePlace = findViewById(R.id.tv_sale_place)
        tvSaleItemInfo = findViewById(R.id.tv_sale_item_info)
        tvSalePrice = findViewById(R.id.tv_sale_price)
        tvSellerName = findViewById(R.id.tv_user_name)
        ivSaleItemImage = findViewById(R.id.iv_sale_item_image)
        spinnerSaleStatus = findViewById<Spinner>(R.id.spinner_sale_status)
        goChatting = findViewById<Button>(R.id.btn_go_chatting)

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

        //판매 상품 id
        saleItemId = intent.getStringExtra("saleItemId")!!

        var saleTitle = intent.getStringExtra("saleTitle") // 제목
        var salePrice = intent.getStringExtra("salePrice") // 가격
        var saleItemInfo = intent.getStringExtra("saleItemInfo") // 물건 정보
        var salePlace = intent.getStringExtra("salePlace") // 판매장소
        var sellerName = intent.getStringExtra("sellerName") // 판매자 이름
        var sellerUId = intent.getStringExtra("sellerUId") // 판매자 UID
        var saleItemImageName = intent.getStringExtra("saleItemImage") //판매 상품 이미지 이름
        var isSale = intent.getBooleanExtra("isSale",false) //판매중이라면 false임
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


        // 수정하기 팝업메뉴
        val popup = findViewById<ImageView>(R.id.iv_item_setting)
        popup.setOnClickListener {
            showPopupMenu(it) //it == popup
        }


        // Firebase Authentication 인스턴스
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        // 현재 사용자 UID와 게시글 판매자 UID 비교하여 스피너 가시성 설정
        if (currentUserUid != null && sellerUId != null && currentUserUid == sellerUId) {
            // 현재 사용자가 판매자인 경우
            spinnerSaleStatus.visibility = View.VISIBLE // 판매상태 수정 권한 부여
            goChatting.isEnabled = false //판매 제안 버튼 비활성화
            val spinnerSaleStatusIndex = if (isSale) 1 else 0 // '판매 중': 0, '판매 완료': 1
            spinnerSaleStatus.setSelection(spinnerSaleStatusIndex)
        } else {
            spinnerSaleStatus.visibility = View.GONE // 현재 사용자가 판매자가 아닌 경우 스피너 숨기기
        }

        // 판매중 - 판매완료 스피너
        // 스피너 초기값 설정
        val spinnerSaleStatusIndex = if (isSale) 1 else 0 // '판매 중': 0, '판매 완료': 1

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.sale_status_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSaleStatus.adapter = adapter
        spinnerSaleStatus.setSelection(spinnerSaleStatusIndex)





        //채팅 버튼 클릭시
        /*
        receiverName = intent.getStringExtra("name").toString() //상대방 이름
        receivedUid = intent.getStringExtra("uId").toString() //상대방 uId
        * */

        goChatting.setOnClickListener {
            //채팅방으로 넘어가기
            Log.d("채팅","버튼 눌려짐")
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
        inflater.inflate(com.example.hangeunmarket.R.menu.menu_popup, popupMenu.menu)

        //메뉴 클릭시 동작 정의
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    // 수정 클릭시
                    Log.d("테스트","수정 클릭됨")
                    val intent = Intent(this@SalePostActivity,SaleWritingActivity::class.java)
                    //게시글 아이디 넣어서 화면 이동
                    intent.putExtra("saleItemId",saleItemId) //판매 아이템 아이디 넘겨주기
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}