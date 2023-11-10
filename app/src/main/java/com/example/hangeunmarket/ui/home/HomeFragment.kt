package com.example.hangeunmarket.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
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
        //homeViewModel.saleItemsLiveData.value = initSaleItemDTOArray().toList()

        // LiveData를 관찰하여 어댑터 데이터 업데이트
        homeViewModel.saleItemsLiveData.observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                recyclerViewSaleItemAdapter.setSaleItems(items)
            }
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
                    // 데이터모델에서 모델 데이터를 변경하도록 설정
                    homeViewModel.changeSaleItemForSelectedPlace() //상상관으로 변경

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