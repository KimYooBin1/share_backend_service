# SharedBackend
> 게시판, 중고 물품 판매 기능을 구현한 오픈소스 api

[![NPM Version][npm-image]][npm-url]
[![Build Status][travis-image]][travis-url]
[![Downloads Stats][npm-downloads]][npm-url]

front 처음 개발하시는 개발자들을 위한 backend 서버

![](../header.png)

## 설치 방법

OS X & 리눅스:
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

### [게시글 조회]
* 기능 : 게시글 10개씩 조회
   * url : [hosturl/boards?page={pageNum}&search={search}](http://localhost:8080/boards)
   * input param
      * page : 현재 페이지
      * searh : 찾고 있는 게시글의 제목 or 작성자 
### [인기 게시글 조회]
* 기능 : 좋아요 개수가 많은 5개의 게시글을 조회
   * url : [hosturl/boards/best](http://localhost:8080/boards/beat)
   * input param
### [게시글 상세 조회]
* 기능 : 게시글 상세 조회
   * url : [hosturl/boards/{board_id}](http://localhost:8080/boards/1)
   * input param
      * board_id : 상세 조회하려는 board의 id
### [게시글 등록]
* 기능 : 게시글 등록
   * url : [hosturl/boards/create](http://localhost:8080/boards/create)
   * input param(requestBody를 통해 입력)
      * writer : 게시글 작성자
      * password : 게시글 비밀번호
      * title : 게시글 제목
      * content : 게시글 내용
### [게시글 수정]
* 기능 : 등록된 게시글 수정
   * url : [hosturl/boards/{board_id}/edit](http://localhost:8080/boards/1/edit)
   * input param
      * board_id : 수정하려는 board의 id
      * password : 게시글 비밀번호
      * title : 게시글 제목
      * content : 게시글 내용
### [게시글 삭제]
* 기능 : 등록된 게시글 삭제 -> DB 에서 실제 삭제하는 것이 아닌 state 를 register 에서 delete로 변경해 조회되지 않게 한다
   * url : [hosturl/boards/{board_id}/delete](http://localhost:8080/boards/1/delete)
   * input param
      * board_id : 삭제하려는 board의 id
### [게시글 좋아요]
* 기능 : 게시글 좋아요
   * url : [hosturl/boards/{board_id}/like](http://localhost:8080/boards/1/like)
   * input param
      * board_id : board의 id
### [게시글 싫어요]
* 기능 : 게시글 10개씩 조회
   * url : [hosturl/boards/{board_id}/dislike](http://localhost:8080/boards/1/dislike)
   * input param
      * board_id : board의 id
### [게시글 댓글 조회]
* 기능 : 특정 게시글의 댓글 조회
   * url : [hosturl/boards/{board_id}/comments](http://localhost:8080/boards/1/comments)
   * input param
      * board_id : board의 id
### [게시글 댓글 작성]
* 기능 : 댓글 작성
   * url : [hosturl/boards/{board_id}/comments/create](http://localhost:8080/boards/1/comments/create)
   * input param
      * board_id : board의 id
      * writer : 작성자
      * password : 댓글 비밀번호
      * content : 댓글 내용
### [게시글 댓글 수정]
* 기능 : 댓글 수정
   * url : [hosturl/comments/{comment_id}/edit](http://localhost:8080/comments/1/edit)
   * input param
      * comment_id : comment의 id
      * password : 댓글 비밀번호
      * content : 댓글 내용
### [게시글 댓글 삭제]
* 기능 : 댓글 삭제 -> 게시글과 마찬가지로 state를 delete로 변경
   * url : [hosturl/comments/{comment_id}/delete](http://localhost:8080/comments/1/delete)
   * input param
      * comment_id : comment의 id

_더 많은 예제와 사용법은 [Wiki][wiki]를 참고하세요._



## 프로젝트 정리
1. [api 정의 문서](https://docs.google.com/spreadsheets/d/1G-QWd5c6JL1anTlZtuoILePIFl0Aj8eBA71GOUsMkqg/edit?usp=sharing)
2. [erd 구조](https://www.erdcloud.com/p/7v6o6H3My5NaATopx)
3. [프로젝트 설계](https://few-fireplace-d66.notion.site/be8d253acb0043e48f38ddaf5cff6d37?v=8431170b236c4c55a8da142c709a2352&pvs=4)


## 업데이트 내역

* 0.0.1
    * 게시글, 댓글 기능 구현
    * exception 처리 구현
    * 작업 진행 중



<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://github.com/yourname/yourproject/wiki
