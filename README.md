<h1 align="center"><b>EXPO</b></h1>

<p align="center">
    <h3 align="center">
        <b>교원 연수 및 박람회 사전 신청, 등록 서비스<br>
       </b>
    </h3>
    <br>
    <p>
        <b>EXPO</b>는 전국에서 열리는 박람회, 연수를 편리하게 관리하기 위해 <br>
        스타트업 동아리가 개발한 교내 동아리 관리 플랫폼입니다.<br> 기존 박람회 혹은 연수에서 참가자를 관리할때 사전 신청이나 참가자 조회에서 불편함이 있었고, <br> 외주를 맡기면 약 2000만원의 비용이 들었습니다. <br> 이러한 불편함을 줄일 수 있도록 편리하게 박람회 및 연수를 관리해 주는 서비스입니다. 또한 외주 비용 2000만원 절감을 하는 성과가 있었습니다.
    </p>
    <img src = "https://github.com/user-attachments/assets/f4a33c31-c48e-47a0-ab03-ceeff857830d" />
    <img src = "https://github.com/user-attachments/assets/3b002377-e02e-4cfb-a575-371979ecc1df" />

</p>
<br>
<br>

<h2>
    Installation 🎁 
</h2>

- PlayStore: [EXPO](https://play.google.com/store/apps/details?id=com.school_of_company.expo_android)

<br>
<h2>
Architecture
</h2>
<img src = "https://user-images.githubusercontent.com/82383983/220412681-daafd612-8375-4496-86ea-286b4b05e169.png"/>

EXPO Android 공식문서에 서술된 [Android App Architecture](https://developer.android.com/topic/architecture?hl=ko#recommended-app-arch)를 기반으로 작성되었습니다.
<br>

<h2>
Team 👯‍♂️
</h2>
<div align = "center">
    <table>
    <th>👑<a href="https://github.com/audgns10">이명훈</a></th>
        <th><a href="https://github.com/answad">문혜성</a></th>
        <tr>
             <td align="center">
                <img src="https://github.com/user-attachments/assets/3afffc34-96c9-4e59-b216-db4c667c9a0b" width='120' />
            </td>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/1fd9b9da-5e3d-4c2b-912d-5a3a47be1933" width='120' />
            </td>
        </tr>
        <tr>
            <td align="center">
            로그인 파트,<br> 
            박람회 파트,<br>
            연수, 일반 프로그램 파트,<br>
            참가자 관리 파트,<br>
            현장 qr 파트 
            </td>
            <td align="center">
            폼 파트,<br>
            설문조사 파트,<br>
            현장 qr 파트
            </td>
        </tr>
    </table>
</div>
<br>

## 🗂️ Packages
```
Expo Android
 ┣ 📂app
 ┃ ┣ 📂navigation
 ┃ ┣ 📂ui
 ┃ ┗ 📂activity
 ┣ 📂build-logic
 ┣ 📂core
 ┃ ┣ 📂common
 ┃ ┣ 📂data
 ┃ ┃ ┣ 📂di
 ┃ ┃ ┗ 📂repository
 ┃ ┣ 📂datastore
 ┃ ┃ ┣ 📂di
 ┃ ┃ ┗ 📂proto
 ┃ ┣ 📂design-system
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┣ 📂icon
 ┃ ┃ ┣ 📂theme
 ┃ ┃ ┗ 📂util
 ┃ ┣ 📂model
 ┃ ┃ ┣ 📂enum
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┣ 📂response
 ┃ ┃ ┗ 📂util
 ┃ ┣ 📂network
 ┃ ┃ ┣ 📂api
 ┃ ┃ ┣ 📂datasource
 ┃ ┃ ┣ 📂di
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┣ 📂mapper
 ┃ ┃ ┗ 📂util
 ┃ ┗ 📂ui
 ┗ 📂feature
 ┃ ┗ 📂project element

```
