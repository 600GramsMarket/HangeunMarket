package com.example.hangeunmarket.ui.home.recyclerview


// 판매 상품 아이템 DTO
data class SaleItem(
    var id:String="", //판매 상품에 대한 아이디
    val saleItemImage: String = "",
    val saleTitle: String = "", // txt_sale_title 판매 제목
    val salePlace: String = "", // txt_sale_place 판매 장소
    val salePrice: Int = 0, // txt_sale_price 판매 가격
    val sellerUID: String = "",
    val isSale: Boolean = false,
    val sellerName: String = "",
    val saleContent: String = "",
)