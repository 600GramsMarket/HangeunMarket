package com.example.hangeunmarket.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hangeunmarket.databinding.ActivityChattingRoomBinding
import com.example.hangeunmarket.ui.chat.recyclerview.MessageAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.example.hangeunmarket.ui.dto.Message
import com.example.hangeunmarket.ui.dto.User
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//채팅방
class ChattingRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingRoomBinding

    //채팅방 관련 정보들
    private lateinit var receiverName : String
    private lateinit var receivedUid : String

    //Firebase
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    //chatting room
    private lateinit var receiverRoom: String //받는쪽 대화방
    private lateinit var senderRoom: String //보내는쪽 대화방

    //대화 목록
    private lateinit var messageList: ArrayList<Message>

    //채팅방 어댑터
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageRecyclerViewAdapter : MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase init
        auth = Firebase.auth
        database = Firebase.database.reference

        // 정보 담기
        receiverName = intent.getStringExtra("name").toString() //상대방 이름
        receivedUid = intent.getStringExtra("uId").toString() //상대방 uId

        getUserInfo(receivedUid) // 상대방 정보 얻어오기

        // 접속자 UID
        val senderUid = auth.currentUser?.uid

        // 보내는쪽 대화방 ID 설정
        senderRoom = receivedUid + senderUid
        // 받는쪽 대화방 ID 설정
        receiverRoom = senderUid + receivedUid
        //=> 위 값을 활용하여 채팅을 보내면 2개의 방에 각각 데이터 삽입


        binding.tvReceiverName.text = receiverName //상대방 이름 상단에 띄우기

        //뒤로가기 버튼 구현
        binding.ivBack.setOnClickListener {
            finish() //엑티비티 종료
        }

        //메시지 전송 버튼
        binding.btnSendMessage.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val messageObject = Message(message,senderUid) //메시지 객체 생성

            //데이터 저장 : 보내는쪽 대화방
            //chats -> senderRoom -> messages
            database.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener { // 보낸 메시지 DB에 삽입
                    //삽입 성공시
                    //데이터 저장 : 받는 쪽 대화방
                    database.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }
            binding.etMessage.setText("") //초기화
        }
        //대화목록 초기화
        messageList = ArrayList()

        //채팅목록 리사이클러뷰 초기화
        messageRecyclerView = binding.recyclerviewChatting
        messageRecyclerViewAdapter = MessageAdapter(messageList) // 어댑터 생성
        messageRecyclerView.layoutManager = LinearLayoutManager(this) //레이아웃 설정
        messageRecyclerView.adapter = messageRecyclerViewAdapter // 어댑터 부착

        // 대화 목록 가져오기
        database.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object :ValueEventListener{
                // 메시지 변경시 호출됨
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear() //empty

                    for(postSnapshat in snapshot.children){
                        val message = postSnapshat.getValue(Message::class.java)
                        messageList.add(message!!) //대화 삽입
                        //리사이클러뷰 위치를 화면 아래로 댕기기 => 채팅 바로 확인 가능하도록
                        messageRecyclerView.scrollToPosition(messageList.size - 1)
                    }
                    messageRecyclerViewAdapter.notifyDataSetChanged() // 데이터 변경여부 알리기
                }

                // 오류 발생시
                override fun onCancelled(error: DatabaseError) {
                    Log.d("firebae","error : chattingRoom error")
                }
            })


    }

    private fun getUserInfo(receivedUid:String){
        val dbRef = database.child("user").child(receivedUid)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user!=null){
                    messageRecyclerViewAdapter.setReceiverInformation(user.colorId,user.name)
                }
                Log.d("userInformation",user.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.d("userInformation","fail to get data")
            }
        })
    }
}