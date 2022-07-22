- 관련 글
1. https://atoz-develop.tistory.com/entry/JAVAWEB-%EC%9B%B9-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%97%90-MyBatis-%EC%84%B8%ED%8C%85-%EB%B0%8F-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
2. https://atoz-develop.tistory.com/entry/MyBatis-%EC%84%A4%EC%A0%95-%ED%8C%8C%EC%9D%BC-SQL-Mapper-%EC%9E%91%EC%84%B1-%EB%B0%A9%EB%B2%95
3. https://atoz-develop.tistory.com/entry/MyBatis-%EC%84%A4%EC%A0%95-%ED%8C%8C%EC%9D%BC-%EC%9E%91%EC%84%B1-%EB%B0%A9%EB%B2%95
4. https://atoz-develop.tistory.com/entry/MyBatis%EC%99%80-Log4J-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0

---

MyBatis의 동적 SQL 기능을 사용하면 하나의 SQL문으로 여러 케이스를 처리할 수 있다. 예를 들어 정렬 조건에 따라 ORDER BY 절을 바꿔야 하거나 검색 조건에 따라 WHERE 절을 변경해야 할 경우 동적 SQL 기능을 이용하면 자동으로 변경되는 SQL문을 만들 수 있다.

#### 표-MyBatis 동적 sql