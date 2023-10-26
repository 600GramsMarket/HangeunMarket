package com.example.hangeunmarket.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangeunmarket.ui.chat.recyclerview.ChattingRoomItem

class ChatViewModel : ViewModel() {
    val chattingRoomItemsLiveData: MutableLiveData<List<ChattingRoomItem>> = MutableLiveData()
}