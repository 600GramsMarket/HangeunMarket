package com.example.hangeunmarket.ui.home

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.FragmentHomeBinding
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.example.hangeunmarket.ui.home.recyclerview.SaleItemRecyclerViewAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view layout
    lateinit var recyclerViewSaleItem : RecyclerView

    //recycler view adapter
    lateinit var recyclerViewSaleItemAdapter : SaleItemRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerViewSaleItem = binding.recyclerviewSaleItem
        var saleItems = initGroupDTOArray() //더미데이터 생성
        setAdapter(saleItems) //어댑터 붙이기


        return binding.root
    }

    /*
    data class SaleItem(
    var saleItemImage : String, //iv_sale_item 판매 물품 이미지
    var saleTitle : String, // txt_sale_title 판매 제목
    val salePlace : String, // txt_sale_place 판매 장소
    var salePrice : String, // txt_sale_price 판매 가격)
    * */

    fun initGroupDTOArray(): Array<SaleItem> {
        return arrayOf(
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","3,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","4,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","5,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","6,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","7,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","8,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","9,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","10,000원"),
            SaleItem("상상부기","상상부기 인형 팝니다!","상상관","11,000원"),
        )
    }

    //리사이클러뷰에 리사이클러뷰 어댑터 부착
    fun setAdapter(groups: Array<SaleItem>){
        recyclerViewSaleItem.layoutManager = LinearLayoutManager(this.context)
        //어탭더 생성
        //it(fragment의 context)이 null일수도 있음 => 검사 필요
        recyclerViewSaleItemAdapter = activity?.let { SaleItemRecyclerViewAdapter(groups, it) }!!
        recyclerViewSaleItem.adapter = recyclerViewSaleItemAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}