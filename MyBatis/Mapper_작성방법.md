# MyBatis 설정 파일 - SQL Mapper 작성 방법

Mybatis 사용 목적 중 하나는 DAO로부터 SQL문을 분리하는 것이다.
분리된 SQL문은 SQL Mapper 파일에 작성하며, DAO에서는 SQL-Session 객체가 Mapper 파일을 참조한다.

다음은 Mapper 파일의 예시이다.
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.atoz_develop.spms.dao.ProjectDao">
    <resultMap type="project" id="projectResultMap">
        <id column="PNO" property="no" />
        <result column="PNAME" property="title" />
        <result column="CONTENT" property="content" />
        <result column="STA_DATE" property="startDate" javaType="java.sql.Date" />
        <result column="END_DATE" property="endDate" javaType="java.sql.Date" />
        <result column="STATE" property="state" />
        <result column="CRE_DATE" property="createdDate" javaType="java.sql.Date" />
        <result column="TAGS" property="tags" />
    </resultMap>
 
    <select id="selectList" resultMap="projectResultMap">
        select PNO, PNAME, STA_DATE, END_DATE, STATE
        from PROJECTS
        order by PNO desc
    </select>
 
    <insert id="insert" parameterType="project">
        insert into PROJECTS(PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE, TAGS)
        values (#{title}, #{content}, #{startDate}, #{endDate}, 0, NOW(), #{tags})
    </insert>
 
    <select id="selectOne" parameterType="int" resultMap="projectResultMap">
        select PNO, PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE, TAGS
        from PROJECTS
        where PNO = #{no}
    </select>
 
    <update id="update" parameterType="project">
        update PROJECTS set
            PNAME = #{title},
            CONTENT = #{content},
            STA_DATE = #{startDate},
            END_DATE = #{endDate},
            STATE = #{state},
            TAGS = #{tags}
        where PNO = #{no}
    </update>
 
    <delete id="delete" parameterType="int">
        delete from PROJECTS
        where PNO = #{no}
    </delete>
</mapper>

```

## 1. XML과 DTO 선언
SQL Mapper 파일은 XML 이기 때문에 가장 먼저 XML 선언이 온다.
```
<?xml version="1.0" encoding="UTF-8"?>
```
다음으로 태그 규칙을 정의한 DTD 선언이 온다.
```
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0 // EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```

## 2. Root Element 
SQL Mapper 파일은 루트 엘리먼트 ```<configuration> ```
MyBatis 설정의 루트 엘리먼트는 ```<configuration> ``` 이다.

---

다음은 ```<configuration> ``` 의 Child Elements 중 주요 엘리먼트의 용도를 정리한 표이다.

| 엘리먼트 | 용도 |
|---------| ---- |
| properties | 프로퍼티 파일 경로 ```<property> ``` 에 개별 프로퍼티 정의 |
| settings | 프레임워크 실행 환경 설정 |
| typeAliases | 자바 클래스 이름에 대한 alias 설정 |
| typeHnandlers | 컬럼 값을 자바 객체로, 자바 객체를 컬럼 값으로 변환해주는 클라스 설정 | 
| environments | 프레임워크에서 사용할 데이터베이스 정보(트랜잭션 관리자, 데이터소스) 설정
| mappers | SQL Mapper 파일 경로


## 3. Root Element ```<mapper>```
SQL Mapper 파일은 루트 엘리먼트 ```<mapper>```를 작성하는 것으로 시작한다.
```<mapper>```의 namespace 속성은 자바의 패키지처럼 여러개의 SQL문을 묶는 용도로 사용한다.
mapper 파일에 작성하는 모든 SQL 문은 ```<mapper>``` 하위에 놓여야 한다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWCTF8%2FbtqCcVWaGzm%2FAXZXKEpT0kowLW6iJSqV5k%2Fimg.png)

## 4. SELECT, INSERT, UPDATE, DELETE

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbmxmcc%2FbtqCaSM5Fb4%2FBlThAGvatOdAvnMovzk5K1%2Fimg.png)

SQL 명령어데 따라 SELECT 구문은 ```<SELECT>```, INSERT문은 ```<INSERT>```, UPDATE문은 ```<UPDATE>```, DELETE문은 ```<DELETE>```에 작성한다.

| 속성 | 설명 
|---- | ----
|id |	각 SQL문을 구분 
| resultType | SELECT문 실행 결과를 담을 객체 패키지 이름을 포함한 클래스 이름 또는 객체 alias를 지정
| resultMap | SELECT문 실행 결과를 담을 객체를 resultMap 으로 지정 ```<resultMap>```을 따로 선언해줘야 한다. resultType과 resultMap 중 하나를 택해서 설정한다.
| parameterType | 이 속성에 지정한 객체의 프로퍼티값이 SQL문의 입력 파라미터에 지정된다.

---

#### resultType 속성
SELECT문을 실행하면 결과가 생성되는데 이 결과를 담을 객체를 resultType 속성에 지정한다. resultType에는 패키지 이름을 포함한 전체 클래스명을 지정하던지 객체의 alias를 지정할 수 있다.

### 패키지 이름을 포함한 전체 클래스명 지정
----
```
<select id="selectList" resultMap="com.atoz_develop.spms.vo.Project">
    select PNO, PNAME, STA_DATE, END_DATE, STATE
    from PROJECTS
    order by PNO desc
</select>
```
----
### 객체의 alias 지정
```
<typeAliases>
    <typeAliase type="com.atoz_develop.spms.vo.Project" alias="project"/>
</typeAliases>
 
<!-- SQL Mapper 파일 -->
<select id="selectList" resultMap="project">
    select PNO, PNAME, STA_DATE, END_DATE, STATE
    from PROJECTS
    order by PNO desc
</select>
```
----
Mybatis는 SELECT 결과를 저장하기 위해 resultType에 지정된 클래스의 인스턴스를 생성한다. 그리고 각 컬럼에 대응하는 setter를 호출한다. 컬럼에 맞는 setter가 없는 경우 그 컬럼의 값은 객체에 저장되지 않는다. 컬럼명과 setter 이름이 달라서 값이 저장되지 않는 문제를 해결하려면 다음과 같이 SELECT문에 각 컬럼에 as로 alias를 지정하면 된다.
```
SELECT PNO as NO ...
```

### resultMap 속성과 ```<resultMap>```Elements
resultType 속성을 사용하면 setter의 매칭되지 않는 경우 각 컬럼마다 alias를 붙여야하는 번거러움이 있다. resultMap 속성을 사용하면 이 문제를 해결할 수 있다.
다음과 같이```<resultMap>```에 컬럼과 매칭되는 setter 메소드를 지원한다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbYI8pc%2FbtqCcVIGkbM%2FadZ1RueVUSG83WAWH6B0bK%2Fimg.png)

---

- ```<resultMap>```.type : SELECT 결과를 저장할 클래스 이름 또는 MyBatis 설정파일에 설정된 alias
- ```<resultMap>```.id : resultMap의 id
- ```<id>``` : 객체 식별자로 사용되는 프로퍼티
- ```<id>```.column : 컬럼명
- ```<id>```.property : 객체 프로퍼티명(setter 메소드 이름에서 set을 빼고 첫 알파벳을 소문자로 만든 이름)
- ```<result>``` : 컬럼-setter 연결 정의
- ```<result>```.column : 컬럼명
- ```<result>```.property : 객체 프로퍼티명(setter 메소드 이름에서 set을 빼고 첫 알파벳을 소문자로 만든 이름)
- ```<result>```.javaType : 컬럼 값을 특정 자바 객체로 변환할때 사용
---
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F5QMtM%2FbtqCd1hn9eM%2FitTNgea8pYBxNzKecxtaXk%2Fimg.png)

정의한 ```<resultMap>```은 ```<select>```의```<resultMap>```id를 지정해서 사용할 수 있다.

```<id>``` 엘리먼트와 MyBatis의 SELECT 결과 캐싱
특히 id 엘리먼트는 설정 방법은 ``` <result> ``` 엘리먼트와 동일하지만 특별한 의미를 가진다. MyBatis는 ``` <id> ``` 를 이용해서 한 번 생성된 객체를 버리지 않고 보관해 두었다가 재사용한다.

SELECT문을 실행하면 레코드 값을 저장하기 위해 결과 객체가 생성도니다. 이 때 SELECT문을 실행할 때마다 매번 결과 객체를 생성하는게 아니라, 결과 객체들을 객체 POOL에 CACHING해두고 다음 SELECT를 실행할 때 재사용한다. 이렇게 객체 POOL에 저장된 객체를 구분하는 값으로 ``` <id> ``` 에 지정한 프로퍼티를 사용한다.

<b>parameterType 속성과 SQL문의 입력 파라미터 처리 </b>

JDBC에서 PreparedStatement 객체를 사용해서 SQL문을 실행할때 '?'로 파라미터를 표시하고 setXXX() 메소드를 호출해서 파라미터에 값을 지정한다.

```
pstmt = connection.prepareStatement(
        "INSERT INTO STUDENT(STUDENT_NO, DEPARTMENT, STUDENT_NAME, GRADE, GENDER, AGE, PHONE_NUMBER, ADDRESS, PASSWORD)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
);
 
pstmt.setString(1, student.getStudentNo());
pstmt.setString(2, student.getDepartment());
pstmt.setString(3, student.getStudentName());
pstmt.setInt(4, student.getGrade());
pstmt.setString(5, student.getGender());
pstmt.setInt(6, student.getAge());
pstmt.setString(7, student.getPhoneNumber());
pstmt.setString(8, student.getAddress());
pstmt.setString(9, student.getPassword());
```

MyBatis에서는 입력 파라미터를 '#{프로퍼티}' 로표시한다.
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcAt0BX%2FbtqCcU38TBC%2Fduowm9LJWcKrjPEnBZdt1K%2Fimg.png)

#{프로퍼티} 에 지정되는 값은 ```<select>, <insert>, <update>,  <delete>``` 의 parameterType에 지정된 객체의 프로퍼티 값이다.

즉#{title} 에는 객체의 getTitle() 반환값이 지정된다.

## <b> 입력 파라미터에 값 공급 </b>

DAO 에서 SQLSession의 메소드를 호출할때 VO를 전달해서 입력 파라미터에 값을 공급한다.
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbv4Ns7%2FbtqCbTZaXAx%2FdaXKpStMRaQInYUu16KCC0%2Fimg.png)

위와 같이 insert()를 호출하면 SQL Mapper 파일에서 'com.atoz_develop.spms.dao.ProjectDao.insert' id를 가진 SQL문을 찾아 실행한다.
project는 해당 SQL문을 실행할 때 입력 파라미터에 값을 공급할 객체이다.

값을 공급하는 객체가 기본 타입인 경우
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FRHxiY%2FbtqCbUDNcVy%2F67BIJFyQWzklqI2hhlXwK0%2Fimg.png)

위와 같이 기본 타입을 전달하면 auto-boxing으로 wrapper 객체를 생성해서 전달된다. wrapper 타입은 getter가 존재하지 않으므로 프로퍼티명도 존재하지 않는다. 따라서 SQL Mapper에서 어떤 이름을 사용해도 무방하다.

```
<!-- 이렇게 해도 되고 -->
<select id="selectOne" parameterType="int" resultMap="projectResultMap">
    select PNO, PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE, TAGS
    from PROJECTS
    where PNO = #{no}
</select>
 
<!-- 이렇게 해도 된다 -->
<select id="selectOne" parameterType="int" resultMap="projectResultMap">
    select PNO, PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE, TAGS
    from PROJECTS
    where PNO = #{value}
</select>
```

## 5. MyBatis 설정 파일에 등록
작성한 SQL Mapper 파일은 MyBatis가 인식할 수 있도록 파일 경로를 MyBatis 설정 파일에 등록해주어야 한다.
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcqhP7t%2FbtqCgRzifEX%2FZKzSEbmWPbFEMgntBCrMxK%2Fimg.png)

MyBatis 설정 파일의 ``` <mapper> ``` 태그 하위에 위와 같이 작성해주면 된다.

MyBatis 설정 파일 작성 가이드 문서는 라이브러리 압축 파일 내에 mybatis-x.x.x.pdf와 같이 파일로도 제공된다. 해당 문서를 함께 참조하면 더 자세한 정보를 얻을 수 있다.

### 관련 글

- <a href> https://atoz-develop.tistory.com/entry/JAVAWEB-%EC%9B%B9-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%97%90-MyBatis-%EC%84%B8%ED%8C%85-%EB%B0%8F-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0