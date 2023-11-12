package com.example.hangeunmarket.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangeunmarket.ui.chat.recyclerview.ChattingRoomItem
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.google.firebase.Firebase
import com.google.firebase.database.database

class HomeViewModel : ViewModel() {
    val saleItemsLiveData: MutableLiveData<List<SaleItem>?> = MutableLiveData()

    //원하는 조건에 맞춰 라이브데이터 업데이트
    //선택한 장소에 맞춰서 리사이클러뷰의 라이브 데이터 변경하는 코드
    fun changeSaleItemForSelectedPlace(place: String) {
        // 테스트 용으로 상상관으로 설정
        val filteredList = saleItemsLiveData.value?.filter { it.salePlace == place }
        saleItemsLiveData.value = filteredList
    }


}