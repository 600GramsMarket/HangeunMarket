package com.example.hangeunmarket.ui.salepost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.dialog.LoadingDialogFragment
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import java.util.UUID

//판매 글 쓰기와 관련된 로직 처리
class SaleWritingActivity : AppCompatActivity() {

    private lateinit var saleTitle : EditText //판매 제목
    private lateinit var salePrice : EditText //판메 가격
    private lateinit var salePlace : EditText //판매 장소
    private lateinit var saleItemInfo : EditText //상품 설명

    private lateinit var selectedImage : ImageView

    private var selectedImageUri: Uri? = null //사용자가 선택한 이미지
    private lateinit var saleItemName:String

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }

    private lateinit var dbRef : DatabaseReference

    private var isImageUpload = false

    private lateinit var saleItemUri:String

    private val loadingDialog = LoadingDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sale_writing)

        saleTitle = findViewById(R.id.et_set_title) //판매 글 제목
        salePrice = findViewById(R.id.et_set_price) //판매 가격
        saleItemInfo = findViewById(R.id.et_set_explain) //상품 설명
        salePlace = findViewById(R.id.et_set_place) //판매 희망 장소
        selectedImage = findViewById(R.id.iv_selected_image)

        dbRef = FirebaseDatabase.getInstance().getReference("sales")
        //intent로부터 정보 가져오기(수정하기에 해당)
        //해당 게시글의 정보를 받아와서,파이어베이스로부터 해당하는 정보 긁어와서 화면에 보여주기
        val SALE_ITEM_ID = intent.getStringExtra("saleItemId")
        Log.d("테스트","#1 ${SALE_ITEM_ID}")
        //intent에 담아온 정보가 존재한다면 값 초기화
        // => 수정하기 모드에 해당함
        if(SALE_ITEM_ID != null){
            Log.d("테스트","#2 ${SALE_ITEM_ID} ")
            setValueOfPost(SALE_ITEM_ID) //아이디를 바탕으로 값 얻어와서 수정하기
        }


        var btnWriteDone = findViewById<Button>(R.id.btn_write_done)


        // 글 쓰기 완료시
        // 수정에 대한 로직 추가 작성 필요
        btnWriteDone.setOnClickListener {
            loadingDialog.show(supportFragmentManager, "loading") // 로딩창 띄우기

            val title = saleTitle.text.toString()
            val priceText = salePrice.text.toString()
            val price = priceText.toInt()
            val itemInfo = saleItemInfo.text.toString()
            val place = salePlace.text.toString()

            if (title.isEmpty() || priceText.isEmpty() || itemInfo.isEmpty() || place.isEmpty() || !isImageUpload) {

                if(!isImageUpload){
                    Toast.makeText(this,"이미지를 업로드해주세요.",Toast.LENGTH_SHORT).show()
                } else if(title.isEmpty()) {
                    Toast.makeText(this, "제목을 작성해주세요", Toast.LENGTH_SHORT).show()
                } else if(priceText.isEmpty()){
                    Toast.makeText(this, "가격을 작성해주세요", Toast.LENGTH_SHORT).show()
                } else if(itemInfo.isEmpty()){
                    Toast.makeText(this, "상품 설명을 작성해주세요", Toast.LENGTH_SHORT).show()
                } else if(place.isEmpty()){
                    Toast.makeText(this, "거래할 장소를 작성해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {

                val sharedPref = getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
                val name = sharedPref?.getString("userName","알 수 없음")
                val uid = Firebase.auth.currentUser?.uid!! //나의 uid에 해당(현재 접속한 유저)


                // Firebase 리얼타임 데이터베이스에 SaleItem 업로드
                dbRef = FirebaseDatabase.getInstance().getReference("sales")
                var saleItemId = dbRef.push().key // 새로운 키 생성 => 파이어베이스에 할당하기 위함

                // 수정에 대한 요청일 경우(상품 아이디가 존재할 경우)
                if(SALE_ITEM_ID!=null){
                    saleItemId = SALE_ITEM_ID //파이어베이스 상품 아이디 변경
                }


                // 새로운 SaleItem 객체 생성
                val newSaleItem = SaleItem(
                    id = saleItemId ?: "",
                    saleItemImage = "",
                    saleTitle = title,
                    salePlace = place,
                    salePrice = price,
                    sellerUID = uid, // 현재 사용자의 UID를 설정
                    sellerName = name!!, // 현재 사용자의 이름을 설정
                    saleContent = itemInfo
                )

                saleItemId?.let {
                    dbRef.child(it).setValue(newSaleItem).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@SaleWritingActivity,SalePostActivity::class.java)
                            // 엑티비티 이동
                            intent.putExtra("saleItemId",saleItemId) //판매상품 ID
                            intent.putExtra("saleTitle",title) // 제목
                            intent.putExtra("salePrice",price.toString()) // 가격
                            intent.putExtra("saleItemInfo",itemInfo) // 물건 정보
                            intent.putExtra("salePlace",place) // 판매장소
                            intent.putExtra("sellerName",name) // 판매자 이름
                            intent.putExtra("sellerUId",uid) // 판매자 UID
                            //처음 작성하는 경우 isSale상태는 무조건 false(아직 판매가 진행중)이라고 가정
                            intent.putExtra("isSale",false)

                            // 이미지가 존재한다면 업로드
                            if(selectedImageUri!=null){
                                selectedImageUri?.let { uri ->
                                    uploadImageToFirebaseStorage(uri) { imageName ->
                                        // 이미지 업로드 후, SaleItem 업데이트
                                        dbRef.child(it).child("saleItemImage").setValue(imageName)
                                        intent.putExtra("saleItemImage",imageName) //판매 상품 이미지 이름
                                        Log.d("ImageTest","이미지 삽입")

                                        loadingDialog.dismiss() // 로딩창 종료
                                        finish() //현재 엑티비티 종료

                                        startActivity(intent)
                                    }
                                }
                            } else{ // 별도의 이미지 업로드가 없다면
                                // 이미지 업로드 후, SaleItem 업데이트
                                // 이미지가 없는 경우 버튼을 누를 수 없으므로 selectedImageUri가 null인 경우는 수정에 해당함
                                // 수정하는 경우 기존 이미지 이름을 불러와서 그대로 삽입
                                dbRef.child(it).child("saleItemImage").setValue(saleItemUri)
                                intent.putExtra("saleItemImage",saleItemUri) //판매 상품 이미지 이름
                                loadingDialog.dismiss() //로딩창 종료
                                finish() //현재 엑티비티 종료
                                startActivity(intent)
                            }


                        } else {
                            // 실패 시 로직
                            Toast.makeText(this, "업로드에 실패하였습니다", Toast.LENGTH_SHORT).show()
                            loadingDialog.dismiss() // 로딩창도 종료
                            finish() // 실패시 엑티비티 종료
                        }
                    }
                }
            }
        }

        var backBtn = findViewById<ImageView>(R.id.iv_back)
        backBtn.setOnClickListener {
            finish() // 백 스택
        }

        //이미지 추가 버튼
        val btnAddPicture = findViewById<Button>(R.id.btn_add_picture)
        btnAddPicture.setOnClickListener {
            openGalleryForImage()
        }

    }


    //saleItemId를 바탕으로 값 얻어오기
    private fun setValueOfPost(saleItemId: String) {
        dbRef.child(saleItemId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val saleItem = snapshot.getValue(SaleItem::class.java)
                saleItem?.let { item ->

                    saleItemUri = item.saleItemImage

                    // 이미지가 존재할 경우에만 이미지 불러오기
                    if (saleItemUri != null){
                        // Firebase Storage에서 이미지 참조 가져오기
                        val storageReference = Firebase.storage.reference.child(saleItemUri)

                        isImageUpload = true //이미지가 존재한다고 알림

                        // 다운로드 URL을 가져와 Glide로 로드하기
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            Glide.with(this)
                                .load(uri.toString())
                                .into(selectedImage)
                        }.addOnFailureListener {
                            // 에러 처리
                        }
                    }

                    // UI 요소에 데이터 할당
                    saleTitle.setText(item.saleTitle)
                    salePrice.setText(item.salePrice.toString())
                    saleItemInfo.setText(item.saleContent)
                    salePlace.setText(item.salePlace)
                }
            } else {
                Log.i("firebase", "No data available for this item ID")
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }



    // 이미지 업로드 후 이미지 이름을 반환하는 콜백 추가
    private fun uploadImageToFirebaseStorage(imageUri: Uri, callback: (String) -> Unit) {
        val filename = UUID.randomUUID().toString()
        saleItemName = filename
        val ref = Firebase.storage.reference.child("$filename")

        ref.putFile(imageUri)
            .addOnSuccessListener {
                loadingDialog.dismiss() //로딩창 종료
                callback(filename) // 이미지 업로드 성공 시 콜백 호출
            }
            .addOnFailureListener {
                // 업로드 실패 시 로직
            }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }



    //사용자가 갤러리에서 이미지 선택시 호출됨
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE){
            isImageUpload = true //이미지 업로드했음을 알림
            selectedImageUri = data?.data // Uri of the selected image
            // 이후 Firebase Storage에 업로드
            selectedImageUri?.let {
                //uploadImageToFirebaseStorage(it)
                findViewById<ImageView>(R.id.iv_selected_image).setImageURI(it) // ImageView에 이미지 설정
            }

        }
    }





}