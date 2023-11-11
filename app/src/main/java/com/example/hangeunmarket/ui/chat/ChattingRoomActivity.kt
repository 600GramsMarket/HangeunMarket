package com.example.hangeunmarket.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.ActivityChattingRoomBinding


//채팅방
class ChattingRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChattingRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}