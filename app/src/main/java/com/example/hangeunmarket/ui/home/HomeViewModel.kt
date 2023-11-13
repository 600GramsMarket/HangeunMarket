import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangeunmarket.ui.home.recyclerview.SaleItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {
    private val dbRef = FirebaseDatabase.getInstance().getReference("sales")
    private val _saleItems = MutableLiveData<List<SaleItem>>()
    val saleItemsLiveData: LiveData<List<SaleItem>> = _saleItems

    // 전체 데이터를 저장하는 리스트
    private var allSaleItems = listOf<SaleItem>()

    init {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<SaleItem>()
                for (postSnapshot in snapshot.children) {
                    val item = postSnapshot.getValue(SaleItem::class.java)
                    item?.let { items.add(it) }
                }
                allSaleItems = items.asReversed()
                _saleItems.value = allSaleItems // 전체 데이터로 초기화
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    // 필터링 메소드
    fun changeSaleItemForSelectedPlace(place: String) {
        val filteredItems = if (place == "전체") {
            allSaleItems.asReversed() // 전체 데이터 반환
        } else {
            allSaleItems.filter { it.salePlace == place } // 필터링된 데이터 반환
        }
        _saleItems.value = filteredItems.asReversed()
    }

    fun filterData(minPrice: Int, maxPrice: Int, isSale: Boolean) {
        val filteredItems = allSaleItems.filter { item ->
            // 판매 상태와 가격 범위에 맞는 항목 필터링
            (item.sale == isSale) && (item.salePrice in minPrice..maxPrice)
        }
        _saleItems.value = filteredItems // 필터링된 데이터로 업데이트
    }

}
