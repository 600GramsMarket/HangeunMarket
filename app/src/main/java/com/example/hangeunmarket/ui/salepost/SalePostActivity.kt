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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hangeunmarket.ui.chat.ChattingRoomActivity
import com.google.android.play.integrity.internal.f
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
    private lateinit var saleItemId:String //판매 상품의 id값

    private lateinit var currentUserUid: String //현재 유저의 uid
    private lateinit var sellerUId:String // 판매자 uid

    //판매 글 보기에 대한 로직
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_post)

        //Init Layout
        tvSaleTitle = findViewById(R.id.tv_sale_title)
        tvSalePlace = findViewById(R.id.tv_sale_place)
        tvSaleItemInfo = findViewById(R.id.tv_sale_item_info)
        tvSalePrice = findViewById(R.id.tv_sale_price)
        tvSellerName = findViewById(R.id.tv_user_name)
        ivSaleItemImage = findViewById(R.id.iv_sale_item_image)
        spinnerSaleStatus = findViewById(R.id.spinner_sale_status)
        goChatting = findViewById(R.id.btn_go_chatting)

        Log.d("ImageTest","엑티비티 이동")

        //판매 상품 id
        saleItemId = intent.getStringExtra("saleItemId")!!

        var saleTitle = intent.getStringExtra("saleTitle") // 제목
        var salePrice = intent.getStringExtra("salePrice") // 가격
        var saleItemInfo = intent.getStringExtra("saleItemInfo") // 물건 정보
        var salePlace = intent.getStringExtra("salePlace") // 판매장소
        var sellerName = intent.getStringExtra("sellerName") // 판매자 이름
        sellerUId = intent.getStringExtra("sellerUId")!! // 판매자 UID
        var saleItemImageName = intent.getStringExtra("saleItemImage") //판매 상품 이미지 이름
        var isSale = intent.getBooleanExtra("isSale",false) //판매중이라면 false임

        //화면에 정보 뿌리기
        tvSaleTitle.text = saleTitle
        tvSalePlace.text = "판매장소 : ${salePlace}"
        tvSaleItemInfo.text = saleItemInfo
        tvSalePrice.text = salePrice.toString()
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

        // Firebase Authentication 인스턴스
        currentUserUid = FirebaseAuth.getInstance().currentUser?.uid!!

        // 수정하기 팝업메뉴
        val popup = findViewById<ImageView>(R.id.iv_item_setting)
        popup.setOnClickListener {
            //내가 작성한 글인 경우에만 팝업 띄우기
            showPopupMenu(it)
        }



        // 현재 사용자 UID와 게시글 판매자 UID 비교하여 스피너 가시성 설정
        if (currentUserUid == sellerUId) {
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

        if(currentUserUid == sellerUId) {
            inflater.inflate(R.menu.menu_popup, popupMenu.menu)
        } else {
            inflater.inflate(R.menu.menu_declaration, popupMenu.menu)
        }

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
                    finish()
                    true
                }
                R.id.menu_declaration -> {
                    Toast.makeText(this, "신고가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}