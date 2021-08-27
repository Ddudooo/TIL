# 오브젝트의 원시형 형변환

오브젝트끼리 수학 연산을 하거나

오브젝트를 출력할때 발생하는 오브젝트의 자동 형변환

오브젝트는 논리평가, `boolean` 으로 변환시 항상 `true`를 반환

이 때문에 객체는 숫자형이나 문자형으로만 형변환이 일어난다고 봐도 된다.

## ToPrimitve

특수 객체 메서드를 사용하면  

숫자형이나 문자형으로의 형 변환을 원하는 대로 조절할 수 있다.

객체 형 변환은 세 종류로 구분되는데, 

`hint`라 불리는 값이 구분 기준이 된다. 

`hint`가 무엇인지는 명세서에 자세히 설명되어 있는데, 

‘목표로 하는 자료형’ 정도로 이해하자

`string`

`console.log()` 함수와 같이 문자열을 기대하는 연산을 수행시

`hint`가 `string`이 된다.

`number`  

수학 연산을 적용하려 할 때,  

`hint`는 `number`가 됩니다.

`default`

연산자가 기대하는 자료형이 **확실치 않을때** 

`hint`는 `default`가 된다. 매우 드물게 적용됨.

주된 예시는 이항 연산자로 `+` 쓸때와 동등 연산자 `==` 으로 비교할 경우

```javascript
// 문자열의 경우 문자열 합치기가 되며
// 숫자의 경우 더하기 연산을 반환해야하기에 `default`가 `hint`가 된다.
let plusResult = obj1 + obj2;

// obj == number 연산의 경우 `hint`로 `default`가 사용된다.
if(obj == 1) { ... };
```

크기를 비교하는 `<` `>` 도 피 연산자로 문자형과 숫자형을 둘다 허용하지만,

해당 연산자의 경우 `hint` 를 `number` 로 고정된다.

## 자바스크립트의 오브젝트의 형변환 알고리즘

1. 객체에 `obj[Symbol.toPrimitive](hint)`메서드가 있는지 찾고, 있다면 메서드를 호출합니다.  
`Symbol.toPrimitive`는 시스템 심볼로, 심볼형 키로 사용됩니다.
2. 1에 해당하지 않고 `hint`가 `string`이라면,
  * `obj.toString()`이나 `obj.valueOf()`를 호출합니다(존재하는 메서드만 실행됨).
3. 1과 2에 해당하지 않고, `hint`가 `number`나 `default`라면
  * `obj.valueOf()`나 `obj.toString()`을 호출합니다(존재하는 메서드만 실행됨).

## Symbol.toPrimitive

자바스크립트엔 `Symbol.toPrimitive`라는 내장 심볼이 존재

이 심볼은 아래와 같이 목표로 하는 자료형(`hint`)을 명명하는 데 사용.

```javascript
obj[Symbol.toPrimitive] = function(hint) {
  // 반드시 원시값을 반환.
  // hint는 `string`, `number`, `default` 중 하나
};
```

## toString, valueOf 메소드

`toString`과 `valueOf`는 심볼이 생기기 이전부터 존재해 왔던 **평범한** 메서드다. 

이 메서드를 이용하면 **구식**이긴 하지만 형 변환을 직접 구현할 수 있다.

객체에 `Symbol.toPrimitive`가 없으면 

자바스크립트는 아래 규칙에 따라 `toString`이나 `valueOf`를 호출합니다.

* `hint`가 `string`인 경우
  * `toString` -> `valueOf` 순  
  (`toString`이 있다면 `toString`을 호출, `toString`이 없다면 `valueOf`를 호출함)
* 그 외
  * `valueOf` -> `toString` 순
  
이 메서드들은 반드시 원시값을 반환해야한다. 

`toString`이나 `valueOf`가 객체를 반환하면 그 결과는 무시된다. 

마치 메서드가 처음부터 없었던 것처럼.

일반 객체는 기본적으로 `toString`과 `valueOf`에 적용되는 다음 규칙을 따른다.

`toString`은 문자열 `[object Object]`을 반환.  
`valueOf`는 객체 자신을 반환.

```javascript
let person = { name : "홍길동" };

console.log(person); // [object Object]
console.log(person.valueOf() === person); // true
```

`toString` 과 `valueOf()`를 조합한 예시

```javascript
let person = {
  name: "홍길동",
  money: 1000,

  // hint가 "string"인 경우
  toString() {
    return `{name: "${this.name}"}`;
  },

  // hint가 "number"나 "default"인 경우
  valueOf() {
    return this.money;
  }

};

console.log(person); // toString -> {name: "홍길동"}
console.log(+person); // valueOf -> 1000
console.log(person + 500); // valueOf -> 1500
```

모든 형변환을 한곳에서 처리해야 하는 경우엔 `toString`만 구현하자

# 정리

원시값을 기대하는 내장 함수나 연산자를 사용할 때 객체-원시형으로의 형 변환이 자동으로 일어난다.

객체-원시형으로의 형 변환은 `hint`를 기준으로 세 종류로 구분된다.

* `string` (문자열을 필요로 하는 연산)
* `number` (수학 연산)
* `default` (드물게 발생함)

연산자별로 어떤 `hint`가 적용되는지는 명세서에서 찾아볼 수 있다. 

연산자가 기대하는 피연산자를 **확신할 수 없을 때**에는 

`hint`가 `default`가 됩니다. 이런 경우는 아주 드물게 발생. 

내장 객체는 대개 `hint`가 `default`일 때와 `number`일 때를 동일하게 처리합니다. 

따라서 실무에선 `hint`가 `default`인 경우와 `number`인 경우를 합쳐서 처리하는 경우가 많습니다.

객체-원시형 변환엔 다음 알고리즘이 적용.

1. 객체에 `obj[Symbol.toPrimitive](hint)`메서드가 있는지 찾고, 있다면 호출합니다.
2. 1에 해당하지 않고 `hint`가 `string`이라면,
  * `obj.toString()`이나 `obj.valueOf()`를 호출합니다.
3. 1과 2에 해당하지 않고, `hint`가 `number`나 `default`라면
  * `obj.valueOf()`나 `obj.toString()`을 호출합니다.

`obj.toString()`만 사용해도 **모든 변환**을 다 다룰 수 있기 때문에, 

실무에선 `obj.toString()`만 구현해도 충분한 경우가 많다. 

반환 값도 `사람이 읽고 이해할 수 있는` 형식이기 때문에 

실용성 측면에서 다른 메서드에 뒤처지지 않는다. 

`obj.toString()`은 로깅이나 디버깅 목적으로도 자주 사용된다.