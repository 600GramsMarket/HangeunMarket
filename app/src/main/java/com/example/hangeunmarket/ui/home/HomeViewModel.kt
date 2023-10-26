package com.example.hangeunmarket.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem

class HomeViewModel : ViewModel() {
    val saleItemsLiveData: MutableLiveData<List<SaleItem>> = MutableLiveData()
}