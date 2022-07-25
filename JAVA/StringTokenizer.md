## StringTokenizer
StringTokenizer 클래스는 문자열을 우리가 지정한 구분자로 문자열을 쪼개주는 클래스입니다. 그렇게 쪼개어진 문자열을 우리는 토큰이라고 부릅니다.
StringTokenizer를 사용하기 위해서는 java.util.StringTokenizer를 import 해야 됩니다. 사용법은 매우 간단합니다. 사용하는 메소드도 몇개 없고 자주 사용하는 메소드 설명과 예제를 통하여 이 클래스의 설명서를 살펴봅시다.

---
## 생성자
|생성자 | 설명 |
|------ | --- |
public StringTokenizer(String str); | 전달된 매개변수 str을 기본으로 분리합니다. 기본 delimiter는 공백 문자들인 "\t\n\r\t" 입니다.
public StringTokenizer(String str, String delim); | 특정 delim으로 문자열을 분리합니다.
public StringTokenizer(String str, String delim, boolean returnDelim); | str을 특정 delim으로 분리시키는데 그 delim까지 token으로 포함할지를 결정합니다. 그 매개변수가 returnDelim로 true일시 포함, false 의경우 포함하지 않습니다.
---
<b>int countToken()</b>

남아있는 token의 개수를 반환합니다. 전체 token의 갯수가 아닌 현재 남아있는 token 개수입니다.

<b> boolean hasMoreElements(), boolean hasMoreToken() </b>

다음의 token을 반환합니다. StringTokenizer는 내부적으로 어떤 위치의 토큰을 사용하였는지를 기억하고 그 위치를 다음으로 옮깁니다. 두가지 메소드 모두 같은 값을 반환합니다.

<b> Object nextElement(), String nextToken() </b>

이 두가지 메소드는 다음의 토큰을 반환합니다. 두 가지 메소드는 같은 객체를 반환하는데 반환형은 다릅니다. nextElements는 Object를, nextToken은 String을 반환하고 있습니다.

---

## 1. 예제
```
public static void main(String[] args){
	String str="this string includes default delims";
	System.out.println(str);
	System.out.println();
		
	System.out.println("==========using split method============");
	String []tokens=str.split(" ");
		
	for(int i=0;i<tokens.length;i++){
		System.out.println(tokens[i]);
	}
}
```

String클래스의 메소드인 split 메소드를 사용하여 StringTokenizer를 흉내낼 수 있습니다. split이 반환하는 값은 String 배열입니다.

## 1. 결과
```
실행결과

this string includes default delims
==========using split method============
this
string
includes
default
delims
```

## 2. Default Delim을 이용

```
public static void main(String[] args){
	String str="this string\tincludes\ndefault delims";
	StringTokenizer stk=new StringTokenizer(str);
	System.out.println(str);
	System.out.println();
		
	System.out.println("total tokens:"+stk.countTokens());
	System.out.println("===============tokens===============");
	while(stk.hasMoreTokens()){
		System.out.println(stk.nextToken());
	}
	System.out.println("total tokens:"+stk.countTokens());
}
```
코드의 while문을 살펴보면 토큰이 있는지 확인한 후 있다면 다음 토큰을 가져옵니다. 이렇게 하나씩 토큰을 소비하는게 StringTokenizer의 가장 일반적인 사용방법 입니다.

## 2. 결과
```
실행결과

this string includes
default delims

total tokens:5
===============tokens===============
this
string
includes
default
delims
total tokens:0
```

## 3. 특정 delim 을 이용
```
public static void main(String[] args){
	String str="this-=string-includes=delims";
	StringTokenizer stk=new StringTokenizer(str,"-=");
	System.out.println(str);
	System.out.println();
		
	System.out.println("total tokens:"+stk.countTokens());
	System.out.println("===============tokens===============");
	while(stk.hasMoreTokens()){
		System.out.println(stk.nextToken());
	}
	System.out.println("total tokens:"+stk.countTokens());
}
```
split을 이용하면 조금 다른결과가 나옵니다. split은 정확히 "-=" 으로된 문자를 쪼개기 때문에 "this = String-includes=delims"에서의 기준으로 쪼개는 겁니다.
## 3. 결과
```
실행결과

this-=string-includes=delims

total tokens:2
===============tokens===============
this
string-includes=delims
```

## 4. delim 까지 포함
```
public static void main(String[] args){
	String str="this-string-includes=delims";
	StringTokenizer stk=new StringTokenizer(str,"-=",true);
	System.out.println(str);
	System.out.println();
		
	System.out.println("total tokens:"+stk.countTokens());
	System.out.println("===============tokens===============");
	while(stk.hasMoreTokens()){
		System.out.println(stk.nextToken());
	}
	System.out.println("total tokens:"+stk.countTokens());
}
```
위의 예제의 생성자에서 세번째 인자를 true로 전달했을때의 예제입니다. 이때 ' - ' 와 ' = ' 를 토큰으로 포함하게 됩니다. 이 예제에서는 true를 전달하지 않고 false를 전달한다면 위의 예제와 같은 결과 값이 나옵니다.
## 4. 결과
```
실행결과

this-string-includes=delims

total tokens:7
===============tokens===============
this
-
string
-
includes
=
delims
total tokens:0
```