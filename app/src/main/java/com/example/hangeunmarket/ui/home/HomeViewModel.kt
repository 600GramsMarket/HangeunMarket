package com.example.hangeunmarket.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem

class HomeViewModel : ViewModel() {
    val saleItemsLiveData: MutableLiveData<List<SaleItem>?> = MutableLiveData()

    init{
        /*
        * val saleItemImage: String, //iv_sale_item 판매 물품 이미지 URL
        val saleTitle: String, // txt_sale_title 판매 제목
        val salePlace: String, // txt_sale_place 판매 장소
        val salePrice: String, // txt_sale_price 판매 가격
        val sellerUID: String,
        val isSale: Boolean,
        val sellerName: String,
        val saleContent: String
        *
        * */
        saleItemsLiveData.value = listOf(
            SaleItem("bugi.png",
                "상상부기 인형팝니다~!",
                "상상관",
                "2,000원",
                "oJOaFwoBDDQj1XC2f1BhGnc9D5l2",
                false,
                "최강",
                "귀여운 상상부기 인형 팝니다!\n 새것처럼 깨끗해요. \n 상상관에서 거래 희망합니다."),
            SaleItem("sale_item1.png",
                "엑셀 2013개정판, CRM 전문가 교재 팝니다.",
                "탐구관",
                "12,000원",
                "mDAtgMsmdjUybgW2W81RsofIg0a2",
                false,
                "김한성",
                "제목에서 보셨다시피 엑셀과 파워포인트 2013 개정판과, Mastering CRM 전문가 책 팝니다.\n" +
                        "\n" +
                        "교재는 한 번도 사용하지 않아 매우 깨끗합니다.\n" +
                        "\n" +
                        "각각 12000원으로 팔겠습니다. 탐구관에서 거래하고 싶습니다!"),
            SaleItem("sale_item2.png",
                "에듀윌 하루 행정학 싸게 올려요~",
                "공학관",
                "4,000원",
                "69tcxAYxRzNEvshBTE6OmEd5cGD3",
                false,
                "임수한",
                "2024 153 하루 행정학 윤세훈 교재입니다. 유튜브에 강의가 있으니 보시면서 하시면 매우 좋습니다!\n" +
                        "공부를 하지 않아 책 안에는 필기가 하나도 없습니다! 공학관에서 거래하고 싶어요!\n" +
                        "15000원에 거래하겠습니다. 네고 x "),
            SaleItem("sale_item3.png",
                "안 쓰는 한성대 경영 교재 팔아요~",
                "상상관",
                "5,000원",
                "ZgCznOMxYpd5CETstRha07gzmUn2",
                false,
                "최지훈",
                "앞에서부터 15000,12000,13000,9000원으로 팔겠습니다.\n" +
                        "\n" +
                        "모두다 구매시 3000원 할인해드리겠습니다. 상상관에서 거래 희망합니다."),
            SaleItem("sale_item4.png",
                "나일론 자켓 준지 22ss 팝니다.",
                "탐구관",
                "400,000원",
                "CdUA1l9QyjcEtNayE3eV5U0PjcI3",
                false,
                "김상상",
                "작년 12월에 60만원 정도에 구매하였지만, 별로 입지 않아서 판매하게 되었습니다. L사이즈이구요\n" +
                        "\n" +
                        "가격은 40만원으로 생각하고 있습니다. 네고받으니 편히 쪽지 보내주세요~"),
            SaleItem("sale_item5.png",
                "스타벅스 오늘도 달콤하게(ICE)",
                "탐구관",
                "10,200원",
                "RkkFUKhh8daC1TCgtF4lZhfdPyc2",
                false,
                "최강",
                "유효기간 5월 11일까지 인 스타벅스 쿠폰 팝니다~ \n" +
                        "관심있으신 분들 댓글이나 쪽지주세요 협상 x"),
            SaleItem("sale_item6.png",
                "소니 미러리스 A7R4 바디 팝니당",
                "상상관",
                "2,500,000원",
                "ZgCznOMxYpd5CETstRha07gzmUn2",
                false,
                "최지훈",
                "구매시 128GB 익스트림 프로 SD카드랑 L플레이트랑 \n" +
                        "정품 충전기 새거랑 정품 배터리 드려용 \n" +
                        "관리 빡빡하게 해서 기스나 흠집 없고 신품 같은 중고 물품 이에요"),
            SaleItem("sale_item7.png",
                "아이소이 모이스취 닥터 앰플",
                "공학관",
                "10,000원",
                "RkkFUKhh8daC1TCgtF4lZhfdPyc2",
                false,
                "최강",
                "새제품 만원에~~ 팔아요~ \n" +
                        "행사로 받고 개봉 안한 미개봉 품이에요 \n" +
                        "추가 구매 원하시면 말해주세요 \n" +
                        "복수 구매 시 일정 부분 추가 할인해드려요~~"),
            SaleItem("sale_item8.png",
                "슈펜 & 핏더사이즈 콜라보 클로그 260",
                "상상관",
                "40,000원",
                "69tcxAYxRzNEvshBTE6OmEd5cGD3",
                false,
                "임수한",
                "1회착, 발에 안 맞아서 팝니다. \n" +
                        "사이즈가 크게 나와 270 신어도 맞으실 겁니다. \n" +
                        "(본인 265) 학교 직거래 원합니다."),
            SaleItem("sale_item9.png",
                "삶과 꿈 교재 팝니다!!",
                "공학관",
                "11,000원",
                "ZgCznOMxYpd5CETstRha07gzmUn2",
                false,
                "최지훈",
                "삶과 꿈 수업 교재 팝니다. \n" +
                            "교양 수업하시려면 꼭 필요하실거에요 \n" +
                            "네고 없이 11,000원만 받겠습니다."),
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