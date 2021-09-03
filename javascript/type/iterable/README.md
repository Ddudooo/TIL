# iterable 

반복 가능한(`iterable`, 이터러블) 객체는 배열을 일반화한 객체. 

이터러블 이라는 개념을 사용하면 어떤 객체에든 `for..of` 반복문을 적용할 수 있다.

배열은 대표적인 이터러블입니다.  
배열 외에도 다수의 내장 객체가 반복 가능, 문자열 역시 이터러블.

배열이 아닌 객체가 있는데, 이 객체가 어떤 것들의 컬렉션(목록, 집합 등)을 나타내고 있는 경우, `for..of` 문법을 적용할 수만 있다면 컬렉션을 순회하는데 유용하다.

##  Symbol.iterator

이터러블로 만들려면 객체에 `Symbol.iterator`(특수 내장 심볼)라는 메서드를 추가해 아래와 같은 일이 벌어지도록 해야 한다.

1. `for..of`가 시작되자마자 `for..of`는 `Symbol.iterator`를 호출  
  * `Symbol.iterator`가 없으면 에러가 발생. 
  * `Symbol.iterator`는 반드시 이터레이터(`iterator`, 메서드 `next`가 있는 객체) 를 반환해야 한다.
2. 이후 `for..of`는 반환된 객체(이터레이터)만을 대상으로 동작한다.
3. `for..of`에 다음 값이 필요하면, `for..of`는 이터레이터의 `next()`메서드를 호출한다.
4. `next()`의 반환 값은 `{done: Boolean, value: any}`와 같은 형태이어야 한다. 
   * `done=true`는 반복이 종료되었음을 의미. 
   * `done=false`일땐 `value`에 다음 값이 저장됩니다.

```javascript
let range = {
  from: 1,
  to: 5
};

// 1. for..of 최초 호출 시, Symbol.iterator가 호출.
range[Symbol.iterator] = function() {

  // Symbol.iterator는 이터레이터 객체 반환.
  // 2. 이후 for..of는 반환된 이터레이터 객체만을 대상으로 동작, 다음 값도 정해짐.
  return {
    current: this.from,
    last: this.to,

    // 3. for..of 반복문에 의해 반복마다 next()가 호출.
    next() {
      // 4. next()는 값을 객체 {done:.., value :...}형태로 반환.
      if (this.current <= this.last) {
        return { done: false, value: this.current++ };
      } else {
        return { done: true };
      }
    }
  };
};

for (let num of range) {
  console.log(num); // 1, then 2, 3, 4, 5
}
```

이터러블 객체의 핵심은 `관심사의 분리(Separation of concern, SoC)`에 있다.

* `range`엔 메서드 `next()`가 없다
* 대신 `range[Symbol.iterator]()`를 호출해서 만든 `이터레이터` 객체와 이 객체의 메서드 `next()`에서 반복에 사용될 값을 만들어 낸다.

### 제한 없는 이터레이터

무수히 많은 이터레이터도 가능하다. 

`range`에서 `range.to`에 `Infinity`를 할당하면 `range`가 무한대가 된다. 

`next`엔 제약사항이 없습니다. `next`가 값을 계속 반환하는 것은 정상적인 동작.

물론 위와 같은 이터러블에 `for..of` 반복문을 사용하면 끝이 없지만, 그렇다 하더라도 `break`를 사용하면 언제든지 반복을 멈출 수 있다.

## 문자열

배열과 문자열은 가장 광범위하게 쓰이는 내장 이터러블이다.

`for..of`는 문자열의 각 글자를 순회한다.

```javascript
for(let char of "string"){
    console.log(char);
}
```

## 이터레이터 호출

```javascript
let str = "javascript";

let iterator = str[Symbol.iterator]();

while(true) {
    let result = iterator.next();
    if(result.done) break;
    console.log(result.value);
}
```

이터레이터를 명시적으로 호출하는 경우는 거의 없지만

이 방법을 사용하면 `for..of`를 사용하는 것보다 반복 과정을 더 잘 통제할 수 있다는 장점. 

반복을 시작했다가 잠시 멈춰 다른 작업을 하다가 다시 반복을 시작하는 것과 같이 반복 과정을 여러 개로 쪼개는 것이 가능하다.

## 이터러블, 유사 배열

* 이터러블(`iterable`)
  * 메서드 Symbol.iterator가 구현된 객체.
* 유사 배열(`array-like`)
  * 인덱스와 `length` 프로퍼티가 있어서 배열처럼 보이는 객체.

이터러블과 유사 배열은 대개 배열이 아니기 때문에 `push`, `pop` 등의 메서드를 지원하지 않는다. 

이터러블과 유사 배열을 배열처럼 다루고 싶을 때 이런 특징은 불편함을 초래한다.

## Array.from

범용 메서드 [Array.from](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/from)는 이터러블이나 유사 배열을 받아 ‘진짜’ Array를 만들어준다.

```javascript
let aryLike = {
    0: "javascript",
    1: "something",
    length: 2
}

let ary = Array.from(aryLike);
console.log(ary.pop());
```

`Array.from`은 객체를 받아 이터러블이나 유사 배열인지 조사한다. 

넘겨 받은 인수가 `이터러블`이나 `유사 배열`인 경우  
새로운 배열을 만들고 객체의 모든 요소를 새롭게 만든 배열로 복사한다.

`Array.from`엔 `매핑(mapping)` 함수를 선택적으로 넘겨줄 수 있다.

```javascript
Array.from(obj[, mapFn, thisArg])
```

`mapFn`을 두 번째 인수로 넘겨주면   
새로운 배열에 obj의 요소를 추가하기 전에 각 요소를 대상으로 `mapFn`을 적용할 수 있다. 

새로운 배열엔 `mapFn`을 적용하고 반환된 값이 추가된다. 

세 번째 인수 `thisArg`는 각 요소의 `this`를 지정할 수 있도록 해준다.

# 정리

`for..of`을 사용할 수 있는 객체를 이터러블이라고 부른다.

* 이터러블엔 메서드 `Symbol.iterator`가 반드시 구현되어 있다.
  * `obj[Symbol.iterator]`의 결과는 이터레이터라고 부른다.  
    이터레이터는 이어지는 반복 과정을 처리한다.
  * 이터레이터엔 객체 `{done: Boolean, value: any}`을 반환하는 메서드 `next()`가 반드시 구현되어 있어야 한다. 
    * `done:true`은 반복이 끝났음을 의미하고  
      그렇지 않은 경우엔 `value`가 다음 값이 된다.
* 메서드 `Symbol.iterator`는 `for..of`에 의해 자동으로 호출  
  개발자가 명시적으로 호출하는 것도 가능.
* 문자열이나 배열 같은 내장 이터러블에도 `Symbol.iterator`가 구현되어 있다.
* 문자열 이터레이터는 서로게이트 쌍을 지원.

인덱스와 `length` 프로퍼티가 있는 객체는 유사 배열이라 불린다. 

유사 배열 객체엔 다양한 프로퍼티와 메서드가 있을 수 있는데 배열 내장 메서드는 없다.

명세서를 보면 대부분의 메서드는 `진짜` 배열이 아닌 이터러블이나 유사 배열을 대상으로 동작한다고 쓰여 있는걸 볼 수 있다.

`Array.from(obj[, mapFn, thisArg])`을 사용하면 이터러블이나 유사 배열인 `obj`를 진짜 `Array`로 만들 수 있다. 

이렇게 하면 `obj`에도 배열 메서드를 사용할 수 있다.  
선택 인수 `mapFn`와 `thisArg`는 각 요소에 함수를 적용할 수 있게 해준다.