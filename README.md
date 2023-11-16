![header](https://capsule-render.vercel.app/api?type=waving&color=gradient&height=200&section=header&text=한근마켓&fontSize=60&fontAlign=78&fontAlignY=38)

<div align="center">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2F600GramsMarket%2FHangeunMarket&count_bg=%2379C83D&title_bg=%23555555&icon=github.svg&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

</div>

<br><br>

## Introduction
> '당'신 '근'처의 마켓, 당근 마켓🥕 클론 코딩 프로젝트 <br>
> '한'성대 '근'처의 마켓, 한근 마켓🍖

'한근 마켓'은 파이어베이스와 안드로이드 활용능력을 기르기 위한, 2023-2 고급 모바일 프로그래밍 기말 대체 사이드 프로젝트입니다.<br>
당근마켓의 이름 뜻인 '당'신 '근'처의 마켓에서 아이디어를 따와,'한'성대 '근'처에서 한성대 학생들끼리 중고거래를 한다는 컨셉으로 이름을 지었습니다.

## 1. 회원가입 / 로그인

<p float="left">
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/133d69ab-694c-4acc-871e-1f20a024f955" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/67a08611-82e1-4e1f-9585-55d4eccf4e09" width="200"/> 
</p>

- Email / Password를 이용한 회원가입
  - 사용자로부터 Email, Password, 이름, 소속, 등의 정보를 입력받아 회원가입을 진행합니다.
  - 회원가입은 `Firebase Authentication`를 통해 이루어지며, 사용자 정보는 `Firebase Realtime Database`에 저장됩니다.
  
- Email / Password를 이용한 로그인
  - `Firebase Authentication`를 이용하여 사용자가 입력한 이메일과 비밀번호가 유효한지 확인 후, 로그인을 승인합니다.

https://github.com/600GramsMarket/HangeunMarket/assets/109474668/581cd552-0444-4352-b360-b876c9ac8a97

https://github.com/600GramsMarket/HangeunMarket/assets/109474668/9fc7215f-0244-4993-9400-41f2cdb47eab

## 2. 게시글 등록/수정/삭제

<p float="left">
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/5bed04ba-e3b1-469a-a1c8-b936fade04f8" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/59f5a0c3-7db8-4e6d-b7b9-7ee9aa0717c1" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/b3a10eb7-d066-46c4-974b-92d050f4c52c" width="200"/> 
</p>
<p float="left">
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/5b1fbef9-560c-4779-a59a-fa7286986ea5" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/c6d08978-22b9-43a7-97e5-1dc752c9b15d" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/a6159ecc-9b69-4515-9c0e-882666d2a827" width="200"/>
</p>

- 게시글 등록
  - 사용자로부터 등록할 물건의 사진과 정보를 전달받아 이미지는 `Firebase Storage`에 글 정보는 `Firebase RealtimeDatabase` 업로드합니다.
  - 게시글의 업로드로 인해 `Firebase Realtime Database`에 변화가 발생하면, LiveData의 observe를 통해 즉각적으로 반영됩니다.
  - 게시글의 사진은 `Glide`라이브러리를 통해 얻어옵니다.
  
- 게시글 수정/삭제
  - 게시글 수정은 현재 로그인한 계정과 게시글의 작성자가 동일한 경우에만 승인됩니다.
  - 사용자와 판매자가 동일하지 않은 경우에만 '거래 제안하기' 버튼이 활성화 됩니다.
  - 게시글 수정버튼을 눌러서 상품 이미지를 포함한 상품 정보를 수정할 수 있습니다.
  - 작성자가 동일할 경우 '수정/삭제' 팝업메뉴가 활성화되고, 동일하지 않을 경우 '신고' 메뉴가 활성화됩니다.
  - 신고 기능은 별도로 구현되지 않았으며, 계정이 다를경우에 대한 차별성을 두기 위해 제작하였습니다.
  - 게시글 상품 상태를 '판매중' 상태와 '판매 완료' 상태로 변경 가능하며, 메인 페이지에서 확인 할 수 있습니다.
  - '판매중','판매완료' 상태와 상품의 거래 위치 정보, 가격 정보 등은 검색을 통해 필터링할 수 있습니다.



https://github.com/600GramsMarket/HangeunMarket/assets/109474668/f725e6f5-aac8-44f9-afee-2d9d78df81f1


https://github.com/600GramsMarket/HangeunMarket/assets/109474668/7adc1016-a428-4cf1-9062-eff400e3cc12

  
## 3. 채팅(거래 제안)기능

<p float="left">
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/271ff6e8-9767-47d8-989b-7c7516473352" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/81dd4cc8-c4da-4ddb-a03f-3cf0a9e05657" width="200"/>
</p>

- `Firebase RealtimeDatabase` 를 활용한 채팅기능
  - 상품의 구매 희망자는 판매자와 1대1 채팅이 가능합니다.
  - 사용자의 채팅이 실시간으로 반영되어 보여집니다.
 

https://github.com/600GramsMarket/HangeunMarket/assets/109474668/ca55017d-4905-4bbf-88d6-eb12928158e4

https://github.com/600GramsMarket/HangeunMarket/assets/109474668/68f71172-1e82-4dbd-b0af-44977ebeb104


 
## 4. 상품 필터링 기능

<p float="left">
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/972f7ebd-d5d9-42f5-8695-c8eb9db18621" width="200"/>
  <img src="https://github.com/600GramsMarket/HangeunMarket/assets/109474668/d7774aae-5be5-436c-9ae1-74a9140d0403" width="200"/>
</p>


- 상품 필터링 검색 기능
  - 왼쪽의 장소 선택을 통해, 상품의 거래 위치를 선택할 수 있습니다.
  - 오른쪽 상단의 돋보기를 눌러 상품의 상태(판매완료/판매중)와 가격 범위를 선택받아 원하는 상품 검색이 가능합니다.


https://github.com/600GramsMarket/HangeunMarket/assets/109474668/c718b745-7e1c-475e-b0e7-350694880ab1


<br><br>
## 🔨Used Skill
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Android Studio-3DDC84?style=flat&logo=androidstudio&logoColor=white"/>
<img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white"/>



