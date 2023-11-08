package com.example.hangeunmarket.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem

class HomeViewModel : ViewModel() {
    val saleItemsLiveData: MutableLiveData<List<SaleItem>?> = MutableLiveData()

    init {
        // 테스트용 초기 더미 데이터 설정
        saleItemsLiveData.value = listOf(
            SaleItem(0,"img_for_sale_default","상상부기 인형팝니다~!","상상관","2,000원"),
            SaleItem(1,"sale_item1","엑셀 2013개정판, CRM 전문가 교재 팝니다.","탐구관","12,000원"),
            SaleItem(2,"sale_item2","에듀윌 하루 행정학 싸게 올려요~","공학관","4,000원"),
            SaleItem(3,"sale_item3","안 쓰는 한성대 경영 교재 팔아요~","상상관","5,000원"),
            SaleItem(4,"sale_item4","나일론 자켓 준지 22ss 팝니다.","탐구관","6,000원"),
            SaleItem(5,"sale_item5","스타벅스 오늘도 달콤하게(ICE)","탐구관","10,200원"),
            SaleItem(6,"sale_item6","소니 미러리스 A7R4 바디 팝니당","상상관","2,500,000원"),
            SaleItem(7,"sale_item7","아이소이 모이스취 닥터 앰플","공학관","10,000원"),
            SaleItem(8,"sale_item8","슈펜 & 핏더사이즈 콜라보 클로그 260","상상관","40,000원"),
            SaleItem(9,"sale_item9","삶과 꿈 교재 팝니다!!","공학관","11,000원"),
        )
    }

    //원하는 조건에 맞춰 라이브데이터 업데이트
    //선택한 장소에 맞춰서 리사이클러뷰의 라이브 데이터 변경하는 코드
    fun changeSaleItemForSelectedPlace() {
        // 테스트 용으로 상상관으로 설정
        val filteredList = saleItemsLiveData.value?.filter { it.salePlace == "상상관" }
        saleItemsLiveData.value = filteredList
    }


}