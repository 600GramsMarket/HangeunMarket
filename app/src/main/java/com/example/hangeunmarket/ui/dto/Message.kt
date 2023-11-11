package com.example.hangeunmarket.ui.dto

// 메시지 DTO
data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}