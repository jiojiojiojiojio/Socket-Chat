# 💬Socket-Chat


## 🗒목차
- [주제 및 개요](#주제-및-개요)
- [개발환경](#개발환경)
- [시스템 구성도 및 데이터베이스 모델링](#시스템-구성도-및-데이터베이스-모델링)
- [기능 구현](#기능-구현)
- [느낀 점](#느낀-점)

## 주제 및 개요

**주제 :** Sokect 통신과 Multi thread를 활용한 1:N 채팅 프로그램 </br></br>
**작업 기간 :** 2020.09 ~ 2020.12(3개월)</br></br>
**인력 구성 :** BE 2명(FE 구현까지 전담하여 업무 수행)</br></br>

<h3>주요 기능</h3>
로그인&로그아웃 / 회원가입(+우편번호 검색, 이미지 업로드) /친구 목록 / 내 정보 수정 / 친구 추가, 삭제 / 1:N 채팅방(+친구 초대, 퇴장하기)


## 🖥개발환경
**사용언어 - JAVA, MySQL**</br>
**개발 프로그램 - Eclipse 5.1, JAVA 1.8, MySQL Server 5.7**

## 시스템 구성도 및 데이터베이스 모델링
<h3>시스템 구성도</h3></br>
<img width="419" alt="채팅 시스템구성도" src="https://user-images.githubusercontent.com/112611440/214278654-8d2581da-5fac-4bb4-80cf-c4e3ff784ca2.png">


<h3>데이터베이스 모델링</h3></br>
<img width="433" alt="채팅 모델링" src="https://user-images.githubusercontent.com/112611440/214278339-7c9b9cc7-d7b6-437b-be99-ec28ea03eefd.png">

## 👩‍💻💻기능 구현 
<h3>1. 회원가입 기능</h3>
<img width="440" alt="image" src="https://user-images.githubusercontent.com/112611440/214279174-ac34d2f1-b44f-450e-a662-f34d43c57bf3.png">
<img width="467" alt="image" src="https://user-images.githubusercontent.com/112611440/214279561-dbec4110-4bde-4c9d-a589-ee2c954c912c.png">
- 회원가입 유효성 검사 구현</br>
- 시.도/구.군/동 단위로 우편번호가 검색 가능하게 구현</br>
  *쿼리문을 사용하여 주소 txt파일을 테이블에 저장함.</br>
- 프로필 사진 이미지 업로드 기능 구현</br>

<h3>2. 회원 정보 수정 기능</h3></br>
<img width="335" alt="image" src="https://user-images.githubusercontent.com/112611440/214280735-14cb232f-229d-4339-bcab-5e3ff26ffeee.png">
<img width="353" alt="image" src="https://user-images.githubusercontent.com/112611440/214280903-4b8e21e3-37de-4c42-9c63-858087e8cd1d.png">
- 회원 정보 출력 기능 구현</br>
- 회원 정보 수정 기능 구현</br>

<h3>3. 채팅방 기능</h3></br>
<img width="468" alt="image" src="https://user-images.githubusercontent.com/112611440/214281257-2635ce10-dcc3-475d-be13-1bae1926c95f.png">
- 한명의 사용자가 여러 사용자와 대화할 수 있도록 1:N 채팅방 기능 구현

## 🌟느낀 점
- 네트워크 프로그래밍을 통해 서버와 클라이언트의 데이터 송수신 관계를 알게 되었다.</br>
- 스레드와 멀티스레드의 차이점을 알게 되었고, 기능에 따라 필요한 것을 선택하여 사용할 수 있게 되었다.</br>
- 프로젝트의 DB설계 방법과 데이터 관리 방법에 대해 알게 되었다.</br>
- MySQL 쿼리문을 활용하여 주소 txt파일 내용을 테이블에 insert하여 단순 반복을 피하고 시간을 단축시킬 수 있었다.</br>
- 프로젝트 유지보수의 필요성을 깨닫게 되었다.
