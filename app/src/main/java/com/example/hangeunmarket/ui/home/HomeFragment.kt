package com.example.hangeunmarket.ui.home

import HomeViewModel
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangeunmarket.R
import com.example.hangeunmarket.databinding.FragmentHomeBinding
import com.example.hangeunmarket.ui.chat.recyclerview.ChattingRoomItem
import com.example.hangeunmarket.ui.dto.User
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.example.hangeunmarket.ui.home.recyclerview.SaleItemRecyclerViewAdapter
import com.example.hangeunmarket.ui.salepost.SaleWritingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //recycler view layout
    private lateinit var recyclerViewSaleItem : RecyclerView

    //recycler view adapter
    private lateinit var recyclerViewSaleItemAdapter : SaleItemRecyclerViewAdapter

    //Firebase database reference
    private lateinit var database: DatabaseReference
    //Firebase Authentication
    private lateinit var auth: FirebaseAuth

    //HomeFragment ViewModel
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var tvPlace: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerViewSaleItem = binding.recyclerviewSaleItem

        tvPlace = binding.tvPlace

        //Firebase init
        database = Firebase.database.reference
        auth = Firebase.auth

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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

        binding.ivSearch.setOnClickListener {
            // 다이얼로그 레이아웃 설정
            val dialogView = layoutInflater.inflate(R.layout.dialog_search, null)
            val etMinPrice = dialogView.findViewById<EditText>(R.id.etMinPrice)
            val etMaxPrice = dialogView.findViewById<EditText>(R.id.etMaxPrice)
            val rgSaleStatus = dialogView.findViewById<RadioGroup>(R.id.rgSaleStatus)

            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("검색") { dialog, _ ->
                    val minPrice = etMinPrice.text.toString().toIntOrNull() ?: 0
                    val maxPrice = etMaxPrice.text.toString().toIntOrNull() ?: Int.MAX_VALUE
                    val isSale = when (rgSaleStatus.checkedRadioButtonId) {
                        R.id.rbSale -> false
                        R.id.rbSoldOut -> true
                        else -> false // 기본값 설정(아직 판매 안된글만)
                    }

                    homeViewModel.filterData(minPrice, maxPrice, isSale)
                    tvPlace.text = "검색결과"
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
                .create()

            dialog.show()
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
                R.id.menu_item_all -> {
                    // "전체" 클릭시
                    homeViewModel.changeSaleItemForSelectedPlace("전체")
                    tvPlace.text = "한성대"
                    true
                }
                R.id.menu_item_sangsang -> {
                    // "상상관" 클릭시
                    homeViewModel.changeSaleItemForSelectedPlace("상상관")
                    tvPlace.text = "상상관"
                    true
                }
                R.id.menu_item_gonghak -> {
                    // "공학관" 클릭시
                    homeViewModel.changeSaleItemForSelectedPlace("공학관")
                    tvPlace.text = "공학관"
                    true
                }
                R.id.menu_item_taemgu -> {
                    // "탐구관" 클릭시
                    homeViewModel.changeSaleItemForSelectedPlace("탐구관") //탐구관
                    tvPlace.text = "탐구관"
                    true
                }
                R.id.menu_item_insung -> {
                    // "인성관" 클릭시
                    homeViewModel.changeSaleItemForSelectedPlace("인성관") //인성관
                    tvPlace.text = "인성관"
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