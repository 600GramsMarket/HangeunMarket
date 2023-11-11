package com.example.hangeunmarket.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.ActivityChattingRoomBinding


//채팅방
class ChattingRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingRoomBinding

    //채팅방 관련 정보들
    private lateinit var receiverName : String
    private lateinit var receivedUid : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 정보 담기
        receiverName = intent.getStringExtra("name").toString() //상대방 이름
        receivedUid = intent.getStringExtra("uId").toString() //상대방 uId

        binding.tvReceiverName.text = receiverName //상대방 이름 상단에 띄우기

        //뒤로가기 버튼 구현
        binding.ivBack.setOnClickListener {
            finish() //엑티비티 종료
        }

    }
}