package com.example.hangeunmarket.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        return binding.root
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