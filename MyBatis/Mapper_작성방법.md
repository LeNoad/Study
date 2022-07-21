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

- ```<resultMap>```.type : SELECT 결과를 저장할 클래스 이름 또는 MyBatis 설정파일에 설정된 alias
- ```<resultMap>```.id : resultMap의 id
- ```<id>``` : 객체 식별자로 사용되는 프로퍼티
- ```<id>```.column : 컬럼명
- ```<id>```.property : 객체 프로퍼티명(setter 메소드 이름에서 set을 빼고 첫 알파벳을 소문자로 만든 이름)
- ```<result>``` : 컬럼-setter 연결 정의
- ```<result>```.column : 컬럼명
- ```<result>```.property : 객체 프로퍼티명(setter 메소드 이름에서 set을 빼고 첫 알파벳을 소문자로 만든 이름)
- ```<result>```.javaType : 컬럼 값을 특정 자바 객체로 변환할때 사용
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F5QMtM%2FbtqCd1hn9eM%2FitTNgea8pYBxNzKecxtaXk%2Fimg.png)

정의한 ```<resultMap>```은 ```<select>```의```<resultMap>```id를 지정해서 사용할 수 있다.