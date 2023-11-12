package com.example.hangeunmarket.ui.home.recyclerview

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
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
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

// 4.아이템을 유지/관리하는 Adapter
class SaleItemRecyclerViewAdapter(var context: Context) : //화면에 데이터를 붙이기 위해 context가 필요함
    RecyclerView.Adapter<SaleItemRecyclerViewAdapter.ViewHolder>() { //리사이클러뷰 어댑터를 상속, Generic 값으로 innerClass인 ViewHolder를 넣어줘야함

    private var saleItems: List<SaleItem> = emptyList() //화면에 보여줄 데이터들

    //(2) ViewHolder패턴 => View를 Holder에 넣어두었다가 재사용을 하기 위함
    //=> itemView는 onCreateViewHolder에서 전달받은 아이템 뷰의 레이아웃에 해당
    //=> onBindViewHolder에서 view에 groups의 값을 할당함
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var saleItemImage = itemView.findViewById<ImageView>(R.id.iv_sale_item)
        var saleTitle = itemView.findViewById<TextView>(R.id.txt_sale_title)
        var salePrice = itemView.findViewById<TextView>(R.id.txt_sale_price)
        var salePlace = itemView.findViewById<TextView>(R.id.txt_sale_place)
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

        // 판매상품 이미지 Storage에서 가져와서 보여주기
//        val imagePath = "gs://hangeunmarket.appspot.com/"
//        val imageName = saleItem.saleItemImage //String
//        val imageRef = Firebase.storage.getReferenceFromUrl("${imagePath}${imageName}")
//        displayImageRef(imageRef,holder.saleItemImage)

//        val imagePath = "gs://hangeunmarket.appspot.com/${saleItem.saleItemImage}"
//        Glide.with(context)
//            .load(imagePath)
//            .into(holder.saleItemImage)

        // Firebase Storage에서 이미지 참조 가져오기
        val storageReference = Firebase.storage.reference.child(saleItem.saleItemImage)

        // 다운로드 URL을 가져와 Glide로 로드하기
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(context)
                .load(uri.toString())
                .into(holder.saleItemImage)
        }.addOnFailureListener {
            // 에러 처리
        }

        holder.apply {
            saleTitle.text = saleItem.saleTitle
            salePlace.text = saleItem.salePlace
            salePrice.text = saleItem.salePrice
        }

        //아이템 클릭 이벤트 작성
        holder.itemView.setOnClickListener {
            val intent = Intent(context,SalePostActivity::class.java)

            //intent에 데이터 삽입 => 판매글 엑티비티로 넘기기
            intent.putExtra("saleTitle",saleItem.saleTitle) //제목
            intent.putExtra("salePrice",saleItem.salePrice) //가격
            intent.putExtra("saleItemInfo",saleItem.saleContent) //물건 정보
            intent.putExtra("salePlace",saleItem.salePlace) //판매장소
            intent.putExtra("sellerUId",saleItem.sellerUID) //판매자 UID
            intent.putExtra("sellerName",saleItem.sellerName) //판매자 이름
            intent.putExtra("saleItemImage",saleItem.saleItemImage) //이미지url


            context.startActivity(intent)
        }

    }

    // 스토리지에서 이미지 가져와서 표시하기
    private fun displayImageRef(imageRef: StorageReference?, view:ImageView){
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it,0,it.size)
            view.setImageBitmap(bmp) // bitmap으로 이미지뷰에 이미지 설정
        }?.addOnFailureListener {
            //Failed to download the image
        }
    }


    //리사이클러뷰의 아이템의 개수가 총 몇개인지를 리턴
    override fun getItemCount(): Int {
        return saleItems.size
    }
}