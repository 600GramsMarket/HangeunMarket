package com.example.hangeunmarket.ui.salepost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.ActivitySaleWritingBinding
import com.google.firebase.Firebase
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
            // 각 EditText에서 텍스트를 가져옴
            val title = saleTitle.text.toString()
            val price = salePrice.text.toString()
            val itemInfo = saleItemInfo.text.toString()
            val place = salePlace.text.toString()

            // 모든 필드가 채워졌는지 확인
            if (title.isEmpty() || price.isEmpty() || itemInfo.isEmpty() || place.isEmpty()) {
                // 하나라도 비어있으면 사용자에게 알림
                Toast.makeText(this, "모든 필드를 채워주세요", Toast.LENGTH_SHORT).show()
            } else {
                // 모든 필드가 채워져 있으면 다음 액티비티로 넘어감
                val intent = Intent(this@SaleWritingActivity, SalePostActivity::class.java)
                intent.putExtra("saleTitle", title)
                intent.putExtra("salePrice", price)
                intent.putExtra("saleItemInfo", itemInfo)
                intent.putExtra("salePlace", place)
                startActivity(intent)
                finish()
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

    //파이어베이스 Storage에 이미지 업로드
    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val filename = UUID.randomUUID().toString()
        val ref = Firebase.storage.reference.child("$filename")

        ref.putFile(imageUri)
            .addOnSuccessListener {
                // 업로드 성공 시 로직
            }
            .addOnFailureListener {
                // 업로드 실패 시 로직
            }
    }



}