<h1><span style="color:yellow">오라클 조인</span></h1>

#### 오라클에서 9i버전까지는 오라클 조인만 가능하고 10g 부터는 안시조인이 추가되었다.
#### 최근 구축되어있는 시스템들은 대부분 안시 조인을 사용하지만, 과거에 구축되어 있는 시스템은 오라클 조인을 많이 사용하기 때문에 공부해야된다.

----
<h3><span style="color:green">안시 조인(ANSI JOIN)</span></h3>

```
SELECT a.empno
    ,  a.ename
    ,  a.deptno
    ,  b.dname
    From emp a
    INNER JOIN dept b
        ON a.deptno = b.deptno
    WHERE a.job = 'MANAGER'
```

<h3><span style="color:green">오라클 조인(ORACLE JOIN)</span></h3>

```
SELECT a.empno
    ,  a.ename
    ,  a.deptno
    ,  b.dname
    FROM emp a
    ,   dept b
    WHERE a.job = 'MANAGER'
    AND a.deptno = b.deptno
```
<h3><span style="color:green">조인 5가지</span></h3>

```
- 조인(INNER JOIN)
- 아우터 조인(LEFT OUTER JOIN)
- 아우터 조인(RIGHT OUTER JOIN)
- 크로스 조인(CROSS JOIN)
- 풀 아우터 조인(FULL OUTER JOIN)
```

 #### 조인은 크게 위의 5가지 정도로 분류 할 수 있다. 조인(INNER JOIN) 과 아우터 조인(LEFT OUTER JOIN) 은 아주 많이 사용된다. 따라서 ANSI JOIN 과 ORACLE JOIN을 비교하여 두 가지 방식 모두 익혀 두어야 한다.
