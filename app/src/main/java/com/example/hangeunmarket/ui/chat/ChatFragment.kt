package com.example.hangeunmarket.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangeunmarket.databinding.FragmentChatBinding
import com.example.hangeunmarket.ui.chat.recyclerview.ChattingRoomItem
import com.example.hangeunmarket.ui.chat.recyclerview.ChattingRoomItemRecyclerViewAdapter

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    //recycler view layout
    private lateinit var recyclerViewChattingItem : RecyclerView

    //recycler view adapter
    private lateinit var recyclerViewChattingItemAdapter : ChattingRoomItemRecyclerViewAdapter

    //HomeFragment ViewModel
    private val chatViewModel by lazy {
        ViewModelProvider(this)[ChatViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)


        recyclerViewChattingItem = binding.recyclerviewChattingRoom

        // 더미데이터를 ViewModel의 LiveData에 설정
        chatViewModel.chattingRoomItemsLiveData.value = initChattingRoomItemDTOArray().toList()

        // LiveData를 관찰하여 어댑터 데이터 업데이트
        chatViewModel.chattingRoomItemsLiveData.observe(viewLifecycleOwner, Observer { items ->
            recyclerViewChattingItemAdapter.setChattingRoomItem(items)
        })

        setAdapter() //어댑터 붙이기

        return binding.root
    }


    /*
    * data class ChattingRoomItem(
    var chatItemImage : String, //채팅 방 이미지
    var chatUserName : String, //상대방 이름
    val lastChat : String, //마지막 채팅
    * */

    private fun initChattingRoomItemDTOArray(): Array<ChattingRoomItem> {
        return arrayOf(
            ChattingRoomItem(0,"User1","좋은 물건 감사합니다~!"),
            ChattingRoomItem(2,"User2","넵 상상관에서 뵐게요!"),
            ChattingRoomItem(1,"User3","네고 가능할까요??"),
            ChattingRoomItem(0,"User4","넵 알겠습니다!"),
            ChattingRoomItem(3,"User5","네네"),
            ChattingRoomItem(2,"User6","곧 있으면 도착해요!"),
            ChattingRoomItem(0,"User7","넵"),
            ChattingRoomItem(3,"User8","네ㅎㅎ 감사합니다"),
            ChattingRoomItem(1,"User9","네 다음주에 봬요!"),
        )
    }

    //리사이클러뷰에 리사이클러뷰 어댑터 부착
    private fun setAdapter(){
        recyclerViewChattingItem.layoutManager = LinearLayoutManager(this.context)
        recyclerViewChattingItemAdapter = activity?.let { ChattingRoomItemRecyclerViewAdapter(it) }!!
        recyclerViewChattingItem.adapter = recyclerViewChattingItemAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}