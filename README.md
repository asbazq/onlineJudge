# onlineJudge

## commit message
|Type 키워드|	사용 시점|
|---|---|
|feat|	새로운 기능 추가|
|fix|	버그 수정|
|docs|	문서 수정|
|style|	코드 스타일 변경 (코드 포매팅, 세미콜론 누락 등)기능 수정이 없는 경우|
|design|	사용자 UI 디자인 변경 (CSS 등)|
|test|	테스트 코드, 리팩토링 테스트 코드 추가|
|refactor|	코드 리팩토링|
|build|	빌드 파일 수정|
|ci|	CI 설정 파일 수정|
|perf|	성능 개선|
|chore|	빌드 업무 수정, 패키지 매니저 수정 (gitignore 수정 등)|
|rename|	파일 혹은 폴더명을 수정만 한 경우|
|remove|	파일을 삭제만 한 경우|

## 관련 이슈
|사용 시점|	사용 키워드|
|---|---|
|해결|	Closes(종료), Fixes(수정), Resolves(해결)|
|참고|	Ref(참고), Related to(관련), See also(참고)|

[참고](https://jane-aeiou.tistory.com/93)


## 👉 coding site 소개
프로그래머스, 백준 같은 코딩 테스트 사이트입니다.

## 프로젝트 개요
java, c, python 여러 언어들의 코딩테스트를 할 수있는
<br>
코딩 사이트입니다.

## 🛠 Architecture


#### 🗓 2023.00.00 - 2023.00.00 (0주)
#### 🙋‍♂️ 팀원

<table>
  <tr>
    <td colspan="1">Front-End</td>
    <td colspan="2">Back-End</td>
  </tr>
  <tr>
    <td>함형우</td>
    <td>장정훈</td>
  </tr>
  <tr>
    <td><img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=white"/></td>
    <td><img src="https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=Springboot&logoColor=white"/></td>
  </tr>
</table>


#### 🔗 link

- [시연영상]()
- [Team notion]()
- [Github Back-End repo](https://github.com/asbazq/onlineJudge)
- [Github Front-End repo](https://github.com/asbazq/reactapp)
<br>

## 🚀 주요 작업 및 기능
#### 😃 안전하고 간편한 로그인과 회원가입
- JWT를 이용한 로그인으로 간단하고 안전하게 로그인할 수 있어요!
#### 😃 내가 원하는 언어로 
- 내가 원하는 다양한 언어로 코딩 테스트를 볼 수 있어요!
#### 😃 원하는 키워드를 검색 기능으로 간편하게
- 제목과 내용으로 검색할 수 있어 편리해요!
#### 😃 코딩 테스트를 성공한 코드
- 역대 해당 문제를 성공한 코드를 볼 수 있어요!
#### 😃 이전에 실행한 코드 
- 이전에 실행한 코드라면 코드를 다시 실행하지 않고 이전의 결과값을 출력해요!
#### 😃 테스트 코드 작성
- mock과 Junit을 통한 테스트 코드로 코드의 추가 변경 시 미치는 영향을 빠르게 파악할 수 있어요 !


<br>

## 📌 Tools
<div align=center>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<img src="https://img.shields.io/badge/Sourcetree-0052CC?style=for-the-badge&logo=Sourcetree&logoColor=white">
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B7?style=for-the-badge&logo=Slack&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">
<img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white">
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">

</div>

## ⚙ 트러블 슈팅
- 공격 행위
<br>코드 입력필드를 통해 서버의 대한 무단 액세스, 특히 파일 생성, 수정 또는 삭제와 같은 공격행위
<br>👉 이러한 공격을 방지하기 위해 Linux서버에 **더미 계정**을 생성하여 읽기 쓰기 권한이 없는 더미 계정으로 실행하여 입력 필드에 입력된 코드를 실행만 할 수 있도록 하여 공격 방지
<br>👉 useradd를 사용하여 홈 디렉토리를 생성하지 않아 **더미 계정**의 홈 디렉토리에서 파일 생성, 수정 또는 삭제와 같은 작업을 방지


## 📃 ERD
![image](https://github.com/asbazq/onlineJudge/assets/107836678/652c171a-ee38-4afa-b8e9-1063cdc83512)


## CI/CD
![image](https://github.com/asbazq/onlineJudge/assets/107836678/35936fe3-46bb-4123-a530-25c36f2d41c0)
- CI/CD를 통해 빌드/테스트 및 배포를 자동화하여 관련 시간을 줄일 수 있었습니다.
  - GitHub Actions를 사용하여 CI/CD 워크플로를 만들고 AWS CodeDeploy를 사용하여 Amazon EC2 인스턴스에 Java SpringBoot 애플리케이션을 배포하였습니다.
  - GitHub Actions는 클라우드가 있으므로, 별도 설치 필요없어 사용에 용이하기도 하고 기존 사용하고 있던 GitHub와 하나로 통일된 환경에서 CI 수행이 가능하기에 github Actions를 사용하였습니다.

