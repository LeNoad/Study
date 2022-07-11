<h1><span style="color:yellow">Oracle Mybatis 연동</span></h1>

### -JDBC : 자바에서 데이터베이스에 접속할 수 있도록 하는 자바API, JDBC는 데이터베이스에서 자료를 쿼리하거나 업데이트 하는 방법을 제공한다.
#### 참고 : https://ko.wikipedia.org/wiki/JDBC

### - Mybatis : 자바 퍼시스턴스 프레임워크의 하나로 XML 서술자나 어노테이션을 사용하여 저장 프로시저나 SQL 문으로 객체들을 연결

---

### 아래의 참고 MAVEN 레포지토리 사이트를 통해 프로젝트 POM.XML 파일에 DBCP 관련 <depencency> 설정을 추가해야 합니다.
- 참고 : https://mvnrepository.com/

````
<properties>
   <java-version>1.8</java-version>
   <org.springframework-version>4.3.2.RELEASE</org.springframework-version>
   <org.aspectj-version>1.6.10</org.aspectj-version>
   <org.slf4j-version>1.6.6</org.slf4j-version>
</properties>
<!-- ojdbc 다운받기위해 추가 -->
<repository>
   <id>oracle</id>
   <name>ORACLE JDBC Repository</name>
   <url>http://mesir.googlecode.com/svn/trunk/mavenrepo</url>
</repository>


<dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-jdbc</artifactId>
   <version>${org.springframework-version}</version>
</dependency>

<!-- MyBatis -->
<dependency>
   <groupId>org.mybatis</groupId>
   <artifactId>mybatis</artifactId>
   <version>3.4.5</version>
</dependency>

<dependency>
   <groupId>org.mybatis</groupId>
   <artifactId>mybatis-spring</artifactId>
   <version>1.3.2</version>
</dependency>

<dependency>
   <groupId>commons-dbcp</groupId>
   <artifactId>commons-dbcp</artifactId>
   <version>1.4</version>
</dependency>

<!-- Oracle -->
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc6</artifactId>
    <version>11.2.0.4</version>
</dependency>
````

### 오라클과 연결하기 위해서는 ojdbc와 커넥션풀 라이브러리를 추가해야 합니다.
- 커넥션풀 : db커넥션을 미리 만들어놓고 요청이 들어오면 커넥션을 하나씩 배정해 DB에 접속하는 시간을 줄여주는 역할.

----

## MyBatis 설정
### MyBatis 설정은 경로에 보듯이 mybatis-config.xml 파일을 생성한 후 아래와 같이 xml을 작성합니다.
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    ...
</configuration>

```

## Oracle DB 연동
### Oracle DB와 연동하기 위해서는 경로와 같이 root-context.xml 파일에 아래와 같이 xml을 작성합니다.

```
<!-- Root Context: defines shared resources visible to all other web components -->
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
   <!-- 드라이버 클래스 이름이 변경됨 --><!-- oracle.jdbc.driver.OracleDriver -->
   <property name="driverClassName" value="net.sf.log4jdbc.DriverSpy" />
   <!-- 연결문자열에 log4jdbc가 추가됨 -->
   <property name="url" value="jdbc:log4jdbc:oracle:thin:@localhost:1521:orcl" />
   <property name="username" value="오라클사용자이름" />
   <property name="password" value="오라클사용자비번" />
   </bean>
<!-- SqlSessionFactory 객체 주입 -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
   <property name="dataSource" ref="dataSource" />
   <property name="configLocation" value="classpath:/mybatis-config.xml"></property>
   <property name="mapperLocations" value="classpath:mapper/**/*Mapper.xml"></property>
</bean>
```

