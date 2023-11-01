package com.example.hangeunmarket.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.FragmentHomeBinding
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.example.hangeunmarket.ui.home.recyclerview.SaleItemRecyclerViewAdapter
import com.example.hangeunmarket.ui.salepost.SaleWritingActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view layout
    private lateinit var recyclerViewSaleItem : RecyclerView

    //recycler view adapter
    private lateinit var recyclerViewSaleItemAdapter : SaleItemRecyclerViewAdapter

    //HomeFragment ViewModel
    private val homeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerViewSaleItem = binding.recyclerviewSaleItem

        // 더미데이터를 ViewModel의 LiveData에 설정
        homeViewModel.saleItemsLiveData.value = initSaleItemDTOArray().toList()

        // LiveData를 관찰하여 어댑터 데이터 업데이트
        homeViewModel.saleItemsLiveData.observe(viewLifecycleOwner, Observer { items ->
            recyclerViewSaleItemAdapter.setSaleItems(items)
        })

        setAdapter() //어댑터 붙이기

        binding.btnSalesWriting.setOnClickListener {
            var intent = Intent(this@HomeFragment.activity,SaleWritingActivity::class.java)
            startActivity(intent) //SaleActivity로 엑티비티 전환
        }
        //메뉴 띄우기
        binding.ivMenu.setOnClickListener {
            showPopupMenu(it) //팝업 메뉴 띄우기
        }

        return binding.root
    }


    //팝업 메뉴 띄우기
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_optional, popupMenu.menu)

        //메뉴 클릭시 동작 정의
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item_sangsang -> {
                    // "상상관" 클릭시
                    true
                }
                R.id.menu_item_gonghak -> {
                    // "공학관" 클릭시
                    true
                }
                R.id.menu_item_taemgu -> {
                    // "탐구관" 클릭시
                    true
                }
                R.id.menu_item_insung -> {
                    // "인성관" 클릭시
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    /*
    data class SaleItem(
    var saleItemImage : String, //iv_sale_item 판매 물품 이미지
    var saleTitle : String, // txt_sale_title 판매 제목
    val salePlace : String, // txt_sale_place 판매 장소
    var salePrice : String, // txt_sale_price 판매 가격)
    * */

    private fun initSaleItemDTOArray(): Array<SaleItem> {
        return arrayOf(
            SaleItem(0,"img_for_sale_default","상상부기 인형팝니다~!","상상관","2,000원"),
            SaleItem(1,"sale_item1","엑셀 2013개정판, CRM 전문가 교재 팝니다.","상상관","12,000원"),
            SaleItem(2,"sale_item2","에듀윌 하루 행정학 싸게 올려요~","상상관","4,000원"),
            SaleItem(3,"sale_item3","안 쓰는 한성대 경영 교재 팔아요~","상상관","5,000원"),
            SaleItem(4,"sale_item4","나일론 자켓 준지 22ss 팝니다.","상상관","6,000원"),
            SaleItem(5,"sale_item5","스타벅스 오늘도 달콤하게(ICE)","상상관","10,200원"),
            SaleItem(6,"sale_item6","소니 미러리스 A7R4 바디 팝니당","상상관","2,500,000원"),
            SaleItem(7,"sale_item7","아이소이 모이스취 닥터 앰플","상상관","10,000원"),
            SaleItem(8,"sale_item8","슈펜 & 핏더사이즈 콜라보 클로그 260","상상관","40,000원"),
            SaleItem(9,"sale_item9","삶과 꿈 교재 팝니다!!","상상관","11,000원"),
        )
    }

    //리사이클러뷰에 리사이클러뷰 어댑터 부착
    private fun setAdapter(){
        recyclerViewSaleItem.layoutManager = LinearLayoutManager(this.context)
        recyclerViewSaleItemAdapter = activity?.let { SaleItemRecyclerViewAdapter(it) }!!
        recyclerViewSaleItem.adapter = recyclerViewSaleItemAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}