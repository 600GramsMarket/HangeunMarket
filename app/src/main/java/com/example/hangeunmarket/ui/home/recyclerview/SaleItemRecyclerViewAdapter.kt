package com.example.hangeunmarket.ui.home.recyclerview

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hangeunmarket.R
import com.example.hangeunmarket.ui.salepost.SalePostActivity
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

// 4.아이템을 유지/관리하는 Adapter
class SaleItemRecyclerViewAdapter(var context: Context) : //화면에 데이터를 붙이기 위해 context가 필요함
    RecyclerView.Adapter<RecyclerView.ViewHolder>() { //리사이클러뷰 어댑터를 상속, Generic 값으로 innerClass인 ViewHolder를 넣어줘야함

    private var saleItems: List<SaleItem> = emptyList() //화면에 보여줄 데이터들

    // 사용할 뷰 홀더가 2개이므로, 타입을 정하기 위해
    val SALE: Int = 1 //판매중
    val SOLDOUT: Int = 2 //판매완료


    //판매중인 아이템
    class SaleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var saleItemImage = itemView.findViewById<ImageView>(R.id.iv_sale_item)
        var saleTitle = itemView.findViewById<TextView>(R.id.txt_sale_title)
        var salePrice = itemView.findViewById<TextView>(R.id.txt_sale_price)
        var salePlace = itemView.findViewById<TextView>(R.id.txt_sale_place)
    }

    //거래 완료 아이템
    class SoldOutItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        // 받은 메시지 텍스트뷰 객체 구현
        var saleItemImage = itemView.findViewById<ImageView>(R.id.iv_sale_item)
        var saleTitle = itemView.findViewById<TextView>(R.id.txt_sale_title)
        var salePrice = itemView.findViewById<TextView>(R.id.txt_sale_price)
        var salePlace = itemView.findViewById<TextView>(R.id.txt_sale_place)
    }


    override fun getItemViewType(position: Int): Int {
        //현재 물건
        var saleItem = saleItems[position]

        //현재 메시지의 id를 확인하여 나의 uid와 같다면 SEND 리턴
        if(saleItem.sale == false){
            Log.d("soldout","false")
            return SALE //판매중일 경우
        } else {
            return SOLDOUT
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == SALE){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_sale, parent, false)
            SaleItemViewHolder(view) // 판매 중
        } else{
            //
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_sold_out, parent, false)
            SoldOutItemViewHolder(view) //판매 완료
        }
    }


    fun setSaleItems(items: List<SaleItem>) {
        this.saleItems = items
        notifyDataSetChanged()
    }

    //(3)
    //itemView에 Array<SaleItem>의 값을 할당함
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val saleItem : SaleItem = saleItems[position]

        // 판매상품 이미지 Storage에서 가져와서 보여주기
        // Firebase Storage에서 이미지 참조 가져오기
        val storageReference = Firebase.storage.reference.child(saleItem.saleItemImage)

        if(holder.javaClass == SaleItemViewHolder::class.java){
            //DownCasting
            val viewHolder = holder as SaleItemViewHolder
            viewHolder.apply {
                saleTitle.text = saleItem.saleTitle
                salePlace.text = saleItem.salePlace
                salePrice.text = saleItem.salePrice.toString()
            }
            // 다운로드 URL을 가져와 Glide로 로드하기
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri.toString())
                    .into(holder.saleItemImage)
            }.addOnFailureListener {
                // 에러 처리
            }
        } else { //받는 메시지라면
            //DownCasting
            val viewHolder = holder as SoldOutItemViewHolder
            viewHolder.apply {
                saleTitle.text = saleItem.saleTitle
                salePlace.text = saleItem.salePlace
                salePrice.text = saleItem.salePrice.toString()
            }
            // 다운로드 URL을 가져와 Glide로 로드하기
            // 이미지가 존재할 경우에만
            if(holder.saleItemImage != null){
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(context)
                        .load(uri.toString())
                        .into(holder.saleItemImage)
                }.addOnFailureListener {
                    // 에러 처리
                }
            }
        }


        //아이템 클릭 이벤트 작성r
        holder.itemView.setOnClickListener {
            val intent = Intent(context,SalePostActivity::class.java)

            //intent에 데이터 삽입 => 판매글 엑티비티로 넘기기
            intent.putExtra("saleTitle",saleItem.saleTitle) //제목
            intent.putExtra("salePrice",saleItem.salePrice.toString()) //가격
            intent.putExtra("saleItemInfo",saleItem.saleContent) //물건 정보
            intent.putExtra("salePlace",saleItem.salePlace) //판매장소
            intent.putExtra("sellerUId",saleItem.sellerUID) //판매자 UID
            intent.putExtra("sellerName",saleItem.sellerName) //판매자 이름
            intent.putExtra("saleItemImage",saleItem.saleItemImage) //이미지url
            intent.putExtra("saleItemId",saleItem.id) //판매상품의 id
            intent.putExtra("sale",saleItem.sale) //현재 아이템의 판매정보


            context.startActivity(intent)
        }

    }



    //리사이클러뷰의 아이템의 개수가 총 몇개인지를 리턴
    override fun getItemCount(): Int {
        return saleItems.size
    }
}