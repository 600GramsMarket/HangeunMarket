package com.example.hangeunmarket.ui.home.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.home.HomeFragment

// 4.아이템을 유지/관리하는 Adapter
class SaleItemRecyclerViewAdapter(var context: Context) : //화면에 데이터를 붙이기 위해 context가 필요함
    RecyclerView.Adapter<SaleItemRecyclerViewAdapter.ViewHolder>() { //리사이클러뷰 어댑터를 상속, Generic 값으로 innerClass인 ViewHolder를 넣어줘야함

    private var saleItems: List<SaleItem> = emptyList() //화면에 보여줄 데이터들

    //(2) ViewHolder패턴 => View를 Holder에 넣어두었다가 재사용을 하기 위함
    //=> itemView는 onCreateViewHolder에서 전달받은 아이템 뷰의 레이아웃에 해당
    //=> onBindViewHolder에서 view에 groups의 값을 할당함
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var saleItemImage : ImageView //iv_sale_item 판매 물품 이미지
        var saleTitle : TextView // txt_sale_title 판매 제목
        var salePlace : TextView // txt_sale_place 판매 장소
        var salePrice : TextView // txt_sale_price 판매 가격
        init { //innerClass의 생성자에 해당 => 뷰의 레이아웃 가져오기 => 화면에 붙이기 위한 하나의 뷰를 만드는 과정에 해당
            saleItemImage = itemView.findViewById(R.id.iv_sale_item)
            saleTitle = itemView.findViewById(R.id.txt_sale_title)
            salePrice = itemView.findViewById(R.id.txt_sale_price)
            salePlace = itemView.findViewById(R.id.txt_sale_place)

//            아이템 클릭에 대한 이벤트 정의
//            itemView.setOnClickListener {
//            }
        }
    }

    //아이템 뷰의 레이아웃을 가져와서 화면에 붙임 (1)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        //화면에 뷰를 붙이기 위해 inflater가 필요
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //아이템 뷰 레이아웃 가져오기
        val view = inflater.inflate(R.layout.item_for_sale, parent, false)

        return ViewHolder(view)
    }


    fun setSaleItems(items: List<SaleItem>) {
        this.saleItems = items
        notifyDataSetChanged()
    }

    //(3)
    //itemView에 Array<SaleItem>의 값을 할당함
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val saleItem : SaleItem = saleItems[position]
//        holder.saleItemImage
        holder.saleTitle.text = saleItem.saleTitle
        holder.salePlace.text = saleItem.salePlace
        holder.salePrice.text = saleItem.salePrice
    }


    //리사이클러뷰의 아이템의 개수가 총 몇개인지를 리턴
    override fun getItemCount(): Int {
        return saleItems.size
    }
}