
# Description
### 스프링 부트 + mysql + Spring Data JPA 활용한 회원관련 CRUD 기본 기능

#### 실행환경
MacBook Pro 14 (m2) Ventura13.5 (Mysql 관련 설치문제로 인해 기입)
MySQL 8.0
intelliJ

#### Stack
- SpringBoot3
- java 17
- gradle
  
[setting설정법](https://github.com/firsthandcraft/SpringBoot_Toy_Project/wiki/Setting)
[build.gradle 에서 dependencies 추가한것]()
```
implementation 'org.springframework.boot:spring-boot-starter-data-jpa' #jpa
implementation group: 'org.javassist', name: 'javassist', version: '3.15.0-GA' #클래스를 동적으로
implementation 'org.junit.jupiter:junit-jupiter:5.8.1' #지금은 필요 없지만 테스트 주고 개발에서 사용하지만, 코드의 유지 보수 및 운영 환경에서의 에러를 미리 방지하기 위해서 단위 별로 검증 하는 테스트 프레임워크
runtimeOnly 'mysql:mysql-connector-java:8.0.32'
```

#### Context
##### ■ 문제 상황
- Member
회원가입, 로그인, 회원목록, 회원조회, 회원정보 수정, 회원삭제, 로그아웃 기능 구현
로그인을 하면 해당 id를 가진 유저만 자신의 모든 정보 조회, 수정 접근 가능

- Tech
REST API(기본 get과 post)로 구현
회원가입을 할 때 이메일 값 검사


##### ■ 구체화 및 해결방안
Controller에서 REST API 제작
JPA를 이용하여 반복되던 CRUD SQL 코드가 줄이고 sql 에서 테이블을 따로 만드는 과정을 간소화함
Mysql이용하여 DB와 연결
Session을 이용하여 해당 유저만 접근 기능 제작

[ERD파일]()

<img width="228" alt="스크린샷 2024-04-17 오후 4 23 18" src="https://github.com/firsthandcraft/SpringBoot_Toy_Project/assets/97497153/f6ae7a3f-d23c-44a2-9db0-4cd8e9857e33">

