
---

## SqlSession 사용

마이바티스에서는 SqlSession을 생성하기 위해 SqlSessionFactory 를 사용한다. 세션을 한번 생성하면 매핑구문을 실행하거나 커밋 또는 롤백을 하기 위해 세션을 사용할 수 있다. 따라서 마지막으로 더 이상 필요하지 않은 상태가 되면 세션을 닫게된다. 마이바티스 스프링 연동모듈을 사용하면 SqlSessionFactory 를 직접 사용할 필요가 없다. 왜냐하면, 스프링 트랜잭션 설정에 따라 자동으로 커밋 혹은 롤백을 수행하고 닫혀지는, 쓰레드에 안전한 SqlSession 개체가 스프링 빈에 주입될 수 있기 때문이다.

---

## SqlSessionTemplate
SqlSessionTemplate은 마이바티스 스프링 연동모듈의 핵심이다. SqlSessionTemplate은 SqlSession을 구현하고 코드에서 SqlSession을 대체하는 역할을 한다. SqlSessionTemplate은 쓰레드에 안전하고 여러개의 DAO나 매퍼에서 공유할수 있다. getMapper() 에 의해 리턴된 매퍼가 가진 메서드를 포함해서 SQL을 처리하는 마이바티스 메서드를 호출할때 SqlSessionTemplate은 SqlSession이 현재의 스프링 트랜잭션에서 사용될 수 있도록 보장한다. 추가적으로 SqlSessionTemplate은 필요한 시점에서 세션을 닫고, 커밋하거나 롤백하는 것을 포함한 세션의 생명주기를 관리한다. 또한 마이바티스 예외를 스프링의 DataAccessException으로 변환하고 작업또한 처리한다. SqlSessionTemplate은 마이바티스의 디폴트 구현체인 DefaultSqlSession 대신 항상 사용한다. 왜냐하면 템플릿은 스프링 트랜잭션의 일부처럼 사용될 수 있고, 여러개 주입된 매퍼 클래스에 의해 사용되도록 쓰레드에 안전하다. 동일한 애플리케이션에서 두개의 클래스간의 전환은 데이터 무결성 이슈를 야기할 수 있다.

---

SqlSessionTemplate은 생성자 인자로 SqlSessionFactory를 사용해서 생성할 수 있다.

```
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
  <constructor-arg index="0" ref="sqlSessionFactory" />
</bean>
```

```
@Configuration
public class MyBatisConfig {
  @Bean
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }
}
```

이 Bean은 DAO Bean에 직접 주입할 수 있다. 다음처럼 Bean 설정에서 SqlSession Property 를 설정하면 된다.

```
public class UserDaoImpl implements UserDao {

  private SqlSession sqlSession;

  public void setSqlSession(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public User getUser(String userId) {
    return sqlSession.selectOne("org.mybatis.spring.sample.mapper.UserMapper.getUser", userId);
  }
}
```

그리고 다음처럼 SqlSessionTeamplate 를 주입하자.

```
<bean id="userDao" class="org.mybatis.spring.sample.dao.UserDaoImpl">
  <property name="sqlSession" ref="sqlSession" />
</bean>
```

SqlSessionTeamplate은 인자로 ExecutorType을 가지는 생성자를 가지고 있다. 이 인자는 예를들어서 스프링 설정 XML을 다음처럼 설정해서 배치형태의 SqlSession을 만들수도 있다.
```
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
  <constructor-arg index="0" ref="sqlSessionFactory" />
  <constructor-arg index="1" value="BATCH" />
</bean>
```
```
@Configuration
public class MyBatisConfig {
  @Bean
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
  }
}
```
DAO의 코드를 다음처럼 작성했다면 모든 구문은 배치형태로 실행이 될 것이다.
```
public class UserService {
  private final SqlSession sqlSession;
  public UserService(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  public void insertUsers(List<User> users) {
    for (User user : users) {
      sqlSession.insert("org.mybatis.spring.sample.mapper.UserMapper.insertUser", user);
    }
  }
}
```
이러한 설정형태를 SqlSessionFactory의 디폴트 형태가 아닌 다른형태로 메서드를 실행해야 할때만 사용할 필요가 있다. 따라서 이러한 형태에 대해 굳이 경로를 하자면 메서드를 호출할때 ExecutorType이 다르면 이미 시작된 트랜잭션을 사용하지 못할것이다. 다른 실행자 타입을 사욯할때는 SqlSessionTeamplate의 메서드를 구분된 트랜잭션이나 트랜잭션 외부에서 호출하는지 확실히해야 한다.

---

## SqlSessionDaoSupport
SqlSessionDaoSupport는 SqlSession을 제공하는 추상클래스이다. getSqlSession() 메서드를 호출해서 다음처럼 SQL을 처리하는 메서드를 호출하기 위해 사용할 SqlSessionTemplate 을 얻을 수 있다.

```
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
  public User getUser(String userId) {
    return getSqlSession().selectOne("org.mybatis.spring.sample.mapper.UserMapper.getUser", userId);
  }
}
```
대개 MapperFactoryBean은 추가적인 코드가 필요없기 때문에 이 클래스를 선호한다. 하지만 DAO에서 마이바티스가 필요하지 않고 구현된 클래스가 필요하지 않을때만 유용하다.
SqlSessionDaoSupport는 SqlSessionFactory와 SqlSessionTemplate property를 설정할 필요가 있다. 두개의 Property를 세팅하게되면 SqlSessionFactory는 무시된다.
SqlSessionDaoSupport의 하위 클래스인 UserDaoImpl이 있다고 하면 스프링에서는 다음처럼 설정 될 수 있다.

```
<bean id="userDao" class="org.mybatis.spring.sample.dao.UserDaoImpl">
  <property name="sqlSessionFactory" ref="sqlSessionFactory" />
</bean>
```