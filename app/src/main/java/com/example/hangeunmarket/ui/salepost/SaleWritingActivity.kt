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
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.ActivitySaleWritingBinding
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import java.util.UUID


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


    private var selectedImageUri: Uri? = null //사용자가 선택한 이미지
    private lateinit var saleItemName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sale_writing)

        saleTitle = findViewById(R.id.et_set_title) //판매 글 제목
        salePrice = findViewById(R.id.et_set_price) //판매 가격
        saleItemInfo = findViewById(R.id.et_set_explain) //상품 설명
        salePlace = findViewById(R.id.et_set_place) //판매 희망 장소


        var btnWriteDone = findViewById<Button>(R.id.btn_write_done)
        //글 쓰기 완료시
        btnWriteDone.setOnClickListener {
            val title = saleTitle.text.toString()
            val priceText = salePrice.text.toString()
            val price = priceText.toInt()
            val itemInfo = saleItemInfo.text.toString()
            val place = salePlace.text.toString()

            if (title.isEmpty() || priceText.isEmpty() || itemInfo.isEmpty() || place.isEmpty()) {
                Toast.makeText(this, "모든 필드를 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
                val name = sharedPref?.getString("userName","알 수 없음")
                val uid = Firebase.auth.currentUser?.uid!!
                // 새로운 SaleItem 객체 생성
                val newSaleItem = SaleItem(
                    saleItemImage = "",
                    saleTitle = title,
                    salePlace = place,
                    salePrice = price,
                    sellerUID = uid, // 현재 사용자의 UID를 설정
                    sellerName = name!!, // 현재 사용자의 이름을 설정
                    saleContent = itemInfo
                )

                // Firebase 리얼타임 데이터베이스에 SaleItem 업로드
                val dbRef = FirebaseDatabase.getInstance().getReference("sales")
                val saleItemId = dbRef.push().key // 새로운 키 생성
                saleItemId?.let {
                    dbRef.child(it).setValue(newSaleItem).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@SaleWritingActivity,SalePostActivity::class.java)
                            // 엑티비티 이동
                            intent.putExtra("saleTitle",title) // 제목
                            intent.putExtra("salePrice",price) // 가격
                            intent.putExtra("saleItemInfo",itemInfo) // 물건 정보
                            intent.putExtra("salePlace",place) // 판매장소
                            intent.putExtra("sellerName",name) // 판매자 이름
                            intent.putExtra("sellerUId",uid) // 판매자 UID
                            //처음 작성하는 경우 isSale상태는 무조건 false(아직 판매가 진행중)이라고 가정
                            intent.putExtra("isSale",false)

                            // 성공 시, 이미지 업로드
                            selectedImageUri?.let { uri ->
                                uploadImageToFirebaseStorage(uri) { imageName ->
                                    // 이미지 업로드 후, SaleItem 업데이트
                                    dbRef.child(it).child("saleItemImage").setValue(imageName)
                                    intent.putExtra("saleItemImage",imageName) //판매 상품 이미지 이름
                                    Log.d("ImageTest","이미지 삽입")

                                    finish() //현재 엑티비티 종료
                                    startActivity(intent)
                                }
                            }

                        } else {
                            // 실패 시 로직
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


    // 이미지 업로드 후 이미지 이름을 반환하는 콜백 추가
    private fun uploadImageToFirebaseStorage(imageUri: Uri, callback: (String) -> Unit) {
        val filename = UUID.randomUUID().toString()
        saleItemName = filename
        val ref = Firebase.storage.reference.child("$filename")

        ref.putFile(imageUri)
            .addOnSuccessListener {
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

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }

    //사용자가 갤러리에서 이미지 선택시 호출됨
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE){
            selectedImageUri = data?.data // Uri of the selected image
            // 이후 Firebase Storage에 업로드
            selectedImageUri?.let {
                //uploadImageToFirebaseStorage(it)
                findViewById<ImageView>(R.id.iv_selected_image).setImageURI(it) // ImageView에 이미지 설정
            }
        }
    }





}