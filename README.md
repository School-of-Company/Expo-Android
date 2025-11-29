<h1 align="center"><b>EXPO</b></h1>

<p align="center">
    <h3 align="center">
        <b>교원 연수 및 박람회 사전 신청, 등록 서비스<br>
       </b>
    </h3>
    <br>
    <p>
        <b>EXPO</b>는 전국에서 열리는 박람회, 연수를 편리하게 관리하기 위해 <br>
        스타트업 동아리가 개발한 박람회, 연수 관리 서비스입니다.<br> 기존 박람회 혹은 연수에서 참가자를 관리할때 사전 신청이나 참가자 조회에서 불편함이 있었고, <br> 외주를 맡기면 약 2000만원의 비용이 들었습니다. <br> 이러한 불편함을 줄일 수 있도록 편리하게 박람회 및 연수를 관리해 주는 서비스입니다. 또한 외주 비용 2000만원 절감을 하는 성과가 있었습니다.
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

* Minumun SDK 26
* Language: ```Kotlin```
* Async: ```Coroutine```
* DI: ```Dagger-Hilt```
* Network: ```Retrofit2```, ```OKhttp3```
* Image: ```Coil```
* AndroidX Jetpack
* Animation: ```Lottie```
* CI, CD: ```Github action```
* Cooperation: ```Git```, ```Github```, ```GitFlow```
* Architecture: ```Google App Architecture```, ```MVVM```

<br>
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
            현장 qr 파트,<br>
            유저 파트
            </td>
            <td align="center">
            폼 파트,<br>
            설문조사 파트,<br>
            박람회 파트 - 주소 기능,<br>
            연수, 일발 프로그램 파트 - 참가자 확인 QR,<br>
            현장 qr 파트
            </td>
        </tr>
    </table>
</div>
<br>
<br>


# :people_holding_hands: Collaborate

### Git 전략
`develop`을 중심으로 합니다.

작업 시에는 `Issue(이슈)`를 열고 해당 이슈에 따른 브랜치 명을 작성합니다.

브랜치 이름 작성법
```
(gitflow 전략)/이슈번호-작업내용

`ex) feature/1-project-setting`

hotfix는 정말 크리티컬 이슈여서 바로 master로 머지를 해 업데이트 하는 경우만 사용
일반적인 버그 고치는 경우는 fix 혹은 bug를 사용한다.
    
간단한 수정 사항은 refactor를 사용한다.
```

<br>
    
작업을 한 후 `develop`으로 **PR**을 올립니다.

<br>
<br>

### Issue 작성 방법
이슈 제목 작성법
```
(작업내용)

ex) project setting
```
    
1. 작업에 맞는 Label을 선택합니다.

2. 개요는 작업하는 내용을 간단하게 한두줄로 정리해서 적습니다.

3. 기타는 궁금하거나 애매한 내용을 적습니다

<br>
<br>

### Pull Request 작성법
PR 제목 작성법
```
🔀 :: (이슈번호) - (브랜치 명[작업내용])

ex) 🔀 :: (#1) - project_setting
```
    
1. Assignees를 자기자신을 선택한다.

2. 작업에 맞는 Label을 선택합니다.

3. 작업 내용이 많을 경우 →
`- (내용 1)`
`- (내용 2)`

4. Reviewer가 `최소 한 명`이상 approve한 경우 develop으로 merge할 수 있습니다.

5. PR을 올린 후 `Android`를 디스코드 방에서 멘션 한다.

<br>
<br>

### Commit 작성법
commit 메세지 작성법
```
(gitmoji) :: (작업 내용)
```

한번에 commit 하지 않고 분할하여 commit한다. 
```
ex 1) ✨ :: Add SearchAPI 
ex 2)💄 :: View Publishing
```

<br>
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
