package com.example.hangeunmarket.ui.chat


// 판매 상품 아이템 DTO
data class ChatItem(
    var chatItemImage : String, //채팅 방 이미지
    var chatUserName : String, //상대방 이름
    val lastChat : String, //마지막 채팅
)