package com.example.hangeunmarket.ui.home.recyclerview


// 판매 상품 아이템 DTO
data class SaleItem(
    var saleItemImage : String, //iv_sale_item 판매 물품 이미지
    var saleTitle : String, // txt_sale_title 판매 제목
    val salePlace : String, // txt_sale_place 판매 장소
    var salePrice : String, // txt_sale_price 판매 가격
)