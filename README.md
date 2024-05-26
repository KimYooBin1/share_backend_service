# SharedBackend
> spring 공부내용을 적용해보는 토이프로젝트


![](../header.png)

## 설치 방법

1. 프로젝트 다운로드 및 빌드
```sh
git clone https://github.com/KimYooBin1/share_backend_service.git
cd share_backend_service
./gradlew build
```

2. 빌드 파일 실행
```sh
cd build/libs
java -jar used_stuff-0.0.1-SNAPSHOT.jar 
```
   
## api 목록
<details>
<summary>api 목록 보기</summary>
   
### [게시글 조회]
* 기능 : 게시글 10개씩 조회
   * url : [hosturl/boards?page={pageNum}&search={search}](http://localhost:8080/boards)
   * input param
      * page : 현재 페이지
      * searh : 찾고 있는 게시글의 제목 or 작성자
   * output
### [인기 게시글 조회]
* 기능 : 좋아요 개수가 많은 5개의 게시글을 조회
   * url : [hosturl/boards/best](http://localhost:8080/boards/beat)
   * input param
   * output
### [게시글 상세 조회]
* 기능 : 게시글 상세 조회
   * url : [hosturl/boards/{board_id}](http://localhost:8080/boards/1)
   * input param
      * board_id : 상세 조회하려는 board의 id
   * output
### [게시글 등록]
* 기능 : 게시글 등록
   * url : [hosturl/boards/create](http://localhost:8080/boards/create)
   * input param(requestBody를 통해 입력)
      * writer : 게시글 작성자
      * password : 게시글 비밀번호
      * title : 게시글 제목
      * content : 게시글 내용
   * output
### [게시글 수정]
* 기능 : 등록된 게시글 수정
   * url : [hosturl/boards/{board_id}/edit](http://localhost:8080/boards/1/edit)
   * input param
      * board_id : 수정하려는 board의 id
      * password : 게시글 비밀번호
      * title : 게시글 제목
      * content : 게시글 내용
   * output
### [게시글 삭제]
* 기능 : 등록된 게시글 삭제 -> DB 에서 실제 삭제하는 것이 아닌 state 를 register 에서 delete로 변경해 조회되지 않게 한다
   * url : [hosturl/boards/{board_id}/delete](http://localhost:8080/boards/1/delete)
   * input param
      * board_id : 삭제하려는 board의 id
   * output
### [게시글 좋아요]
* 기능 : 게시글 좋아요
   * url : [hosturl/boards/{board_id}/like](http://localhost:8080/boards/1/like)
   * input param
      * board_id : board의 id
   * output
### [게시글 싫어요]
* 기능 : 게시글 10개씩 조회
   * url : [hosturl/boards/{board_id}/dislike](http://localhost:8080/boards/1/dislike)
   * input param
      * board_id : board의 id
   * output
### [게시글 댓글 조회]
* 기능 : 특정 게시글의 댓글 조회
   * url : [hosturl/boards/{board_id}/comments](http://localhost:8080/boards/1/comments)
   * input param
      * board_id : board의 id
   * output
### [게시글 댓글 작성]
* 기능 : 댓글 작성
   * url : [hosturl/boards/{board_id}/comments/create](http://localhost:8080/boards/1/comments/create)
   * input param
      * board_id : board의 id
      * writer : 작성자
      * password : 댓글 비밀번호
      * content : 댓글 내용
   * output
### [게시글 댓글 수정]
* 기능 : 댓글 수정
   * url : [hosturl/comments/{comment_id}/edit](http://localhost:8080/comments/1/edit)
   * input param
      * comment_id : comment의 id
      * password : 댓글 비밀번호
      * content : 댓글 내용
   * output
### [게시글 댓글 삭제]
* 기능 : 댓글 삭제 -> 게시글과 마찬가지로 state를 delete로 변경
   * url : [hosturl/comments/{comment_id}/delete](http://localhost:8080/comments/1/delete)
   * input param
      * comment_id : comment의 id
   * output
### [로그인]
* 기능 : 로그인
  * url : [hosturl/login](http://localhost:8080/login)
  * input -> form data 형식으로 입력을 받는다
    * username : 로그인 id
    * password
    * name : 유저의 이름
    * age
    * gender
    * address
      * address
      * addressDetail
      * zipcode
  * output

## 이후부터는 accessToken 필요 

### [본인 정보 확인]
* 기능 : 마이페이지
  * url : [hosturl/user/detail](http://localhost:8080/user/detail)
  * output
### [유저 정보 변경]
* 기능 : 유저 정보 수정
  * url : [hosturl/user/edit](http://localhost:8080/user/edit)
  * input
    * password
    * age
    * gender
    * address
      * address
      * addressDetail
      * zipcode
  * output
### [비밀번호 변경]
* 기능 : 비밀번호 변경
  * url : [hosturl/user/edit/password](http:/localhost:8080/user/edit/password)
  * input
    * checkPassword : 기존 비밀번호와 조회해서 권한 획득
    * editPassword : 변경하고 싶은 비밀번호
### [유저 주문, 판매 목록 조회]
* 기능 :
  * url : [hosturl/user/orderList?type="type"&search="search"&page=1](http:/localhost:8080/user/orderList?)
  * input (url parameter)
    * type : sell(판매 목록), buy(구매 목록), null(전체 조회)
    * search : notNull(title 검색), null(전체 조회)
    * page : pagiantion
### [중고 장터 조회]
* 기능 : list로 pagination과 검색을 통해 10개씩 출력
  * url : [hosturl/shops?type="type"&search="search"&page=1&sort=id,desc](http://localhost:8080/shops)
  * input (url parameter)
    * type : sold(판매된 상품 조회), sell(판매중인 상품 조회), null(전체 조회)
    * search : notNull(title 검색), null(전체 조회)
    * page :pagination
    * sort : createDate, id, desc(내림차순), acs(오름차순)
### [중고 장터 상세 조회] 
* 기능 : 
  * url : [hosturl/shops/{shop_id}/detail](http://localhost:8080/shops/1/detail)
  * input
    * shop_id : 조회하려는 board의 아이디
### [중고 판매글 등록] 
* 기능 : 판매 등록
  * url : [hosturl/shops/create](http://localhost:8080/shops/create)
  * input (request body format)
    * title : 판매글 제목
    * content : 판매글 내용
    * url : 판매글 사진 url
    * price : 가격
    * address : 판매 장소
      * zipcode
      * address
      * addressDetail
### [판매글 수정]
* 기능 : 내용 수정
  * url : [hosturl/shops/{shop_id}/edit](http://localhost:8080/shops/1/edit)
  * input : (request body format)
    * shop_id : 수정하려는 board의 id
    * title : 판매글 제목
    * content : 판매글 내용
    * url : 판매글 사진 url
    * price : 가격
    * address : 판매 장소
      * zipcode
      * address
      * addressDetail
### [판매글 삭제]
* 기능 : 작성글 삭제
  * url : [hosturl/shops/{shop_id}/delete](http://localhost:8080/shops/1/delete)
  * input
    * board_id : 삭제하려는 board id
### [물품 구매]
* 기능 : 해당 물품 구매, 구매 후 buyer 의 point는 price 만큼 감소, seller의 point는 증가, board의 purductStatus는 sold로 변경
* url : [hosturl/shops/{shop_id}/purchase](http://localhost:8080/shops/1/purchase)
* input
  * shop_id : 구매하려는 board의 id
## [구매 취소]
* 기능 : 물품 구매 취소, seller로 부터 point 반환
* url : [hosturl/shops/{shop_id}/cancel](http://localhost:8080/shops/1/cancel)
* input
  * shop_id : 취소하려는 board의 id
        
_더 많은 예제와 사용법은 [Wiki][wiki]를 참고하세요._

</details>

## 프로젝트 정리
1. [api 정의 문서](https://docs.google.com/spreadsheets/d/1G-QWd5c6JL1anTlZtuoILePIFl0Aj8eBA71GOUsMkqg/edit?usp=sharing)
2. [erd 구조](https://www.erdcloud.com/p/7v6o6H3My5NaATopx)
3. [프로젝트 설계](https://few-fireplace-d66.notion.site/be8d253acb0043e48f38ddaf5cff6d37?v=8431170b236c4c55a8da142c709a2352&pvs=4)


## 업데이트 내역

* 0.0.1
    * 게시글, 댓글 기능 구현
    * exception 처리 구현
    * 로그인
    * naver oauth 적용중
    * 중고 장터 구현중
* 0.0.2
   * spring websocket을 사용한 chatting
   * webrtc에서 signaling server를 구현해 streaming service   

## stack

* ### IDE
![Static Badge](https://img.shields.io/badge/intellij-%23000000?style=for-the-badge&logo=intellijidea&logoColor=white)
* ### DB
![Static Badge](https://img.shields.io/badge/MySql-%234479A1?style=for-the-badge&logo=mysql&logoColor=gray)
* ### Framwork
![Static Badge](https://img.shields.io/badge/spring%20security-%236DB33F?style=for-the-badge&logo=springsecurity&logoColor=gray)
* ### Security
![Static Badge](https://img.shields.io/badge/spring%20boot-%236DB33F?style=for-the-badge&logo=springboot&logoColor=gray)
* ### Test tool
![Static Badge](https://img.shields.io/badge/Post%20Man-%23FF6C37?style=for-the-badge&logo=postman&logoColor=gray)

