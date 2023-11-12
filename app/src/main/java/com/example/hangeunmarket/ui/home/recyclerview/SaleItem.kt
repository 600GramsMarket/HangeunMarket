package com.example.hangeunmarket.ui.home.recyclerview


// 판매 상품 아이템 DTO
data class SaleItem(
    val saleItemImage: String = "",
    val saleTitle: String = "", // txt_sale_title 판매 제목
    val salePlace: String = "", // txt_sale_place 판매 장소
    val salePrice: String = "", // txt_sale_price 판매 가격
    val sellerUID: String = "",
    val isSale: Boolean = false,
    val sellerName: String = "",
    val saleContent: String = "",
)