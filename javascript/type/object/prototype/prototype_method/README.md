# 프로토타입 메서드

`__proto__`는 브라우저를 대상으로 개발하고 있다면 다소 구식이기 때문에 더는 사용하지 않는 것이 좋다. 

표준에도 관련 내용이 명시되어 있다.

아래와 같은 모던한 메서드들을 사용하는 것이 좋다

* [Object.create(proto, [descriptors])](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/create)
  * `[[Prototype]]`이 `proto`를 참조하는 빈 객체를 만든다. 
  * 이때 프로퍼티 설명자를 추가로 넘길 수 있다.
* [Object.getPrototypeOf(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/getPrototypeOf)
  * `obj`의 `[[Prototype]]`을 반환한다.
* [Object.setPrototypeOf(obj, proto)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/setPrototypeOf)
  * `obj`의 `[[Prototype]]`이 `proto`가 되도록 설정한다.

앞으론 아래 예시처럼 `__proto__` 대신 이 메서드들을 사용하도록 하자.

```javascript
let animal = {
  eats: true
};

// 프로토타입이 animal인 새로운 객체를 생성합니다.
let rabbit = Object.create(animal);

console.log(rabbit.eats); // true

console.log(Object.getPrototypeOf(rabbit) === animal); // true

Object.setPrototypeOf(rabbit, {}); // rabbit의 프로토타입을 {}으로 바꿉니다.
```

`Object.create`에는 프로퍼티 설명자를 선택적으로 전달할 수 있다.

```javascript
let animal = {
  eats: true
};

let rabbit = Object.create(animal, {
  jumps: {
    value: true
  }
});

console.log(rabbit.jumps); // true
```

`Object.create`를 사용하면 `for..in`을 사용해 프로퍼티를 복사하는 것보다 더 효과적으로 객체를 복제할 수 있다.

```javascript
let clone = Object.create(
        Object.getPrototypeOf(obj), 
        Object.getOwnPropertyDescriptors(obj)
    );
```
`Object.create`를 호출하면 `obj`의 모든 프로퍼티를 포함한 완벽한 사본이 만들어진다, 

사본엔 열거 가능한 프로퍼티와 불가능한 프로퍼티, 데이터 프로퍼티, `getter`, `setter` 등 모든 프로퍼티가 복제된다. `[[Prototype]]`도 복제된다.

## 비하인드 스토리

`[[Prototype]]`을 다룰 수 있는 방법은 다양합니다. 

목표는 하나인데 목표를 이루기 위한 수단은 여러 가지!

왜 그럴까? 역사적인 이유가 있다.

* 생성자 함수의 `"prototype"` 프로퍼티는 아주 오래전부터 그 기능을 수행하고 있었다.
* 그런데 2012년, 표준에 `Object.create`가 추가되고. `Object.create`를 사용하면 주어진 프로토타입을 사용해 객체를 만들 수 있긴 하지만, 프로토타입을 얻거나 설정하는것은 불가능했다. 그래서 브라우저는 비표준 접근자인 `__proto__`를 구현해 언제나 프로토타입을 얻거나 설정할 수 있도록 하였다.
* 이후 2015년에 `Object.setPrototypeOf`와 `Object.getPrototypeOf`가 표준에 추가되면서 `__proto__`와 동일한 기능을 수행할 수 있게 되었다. 그런데 이 시점엔 `__proto__`가 모든 곳에 구현되어 있어서 사실상 표준(de-facto standard)이 되어버렸다. 표준의 부록 B(Annex B)에 추가되기도 하였다.  
이 부록에 추가되면 브라우저가 아닌 환경에선 선택사항이라는것을 의미한다.
  
이런 이유 때문에 지금은 여러 방식을 원하는 대로 쓸 수 있게 된 것.

> 속도가 중요하다면 기존 객체의 `[[Prototype]]`을 변경하지 말아야 한다.
> 
> 원한다면 언제나 `[[Prototype]]`을 얻거나 설정할 수 있다. 
> 
> 기술적인 제약이 있는 건 아니지만.   
> 하지만 대개는 객체를 생성할 때만 `[[Prototype]]`을 설정하고  
> 이후엔 수정하지 않는다. 
> 
> `rabbit`이 `animal`을 상속받도록 설정하고 난 이후엔 이를 변경하지 않는다.
> 
> 자바스크립트 엔진은 이런 시나리오를 토대로 최적화되어 있다. 
> 
> `Object.setPrototypeOf`나 `obj.__proto__=`를 써서  
> 프로토타입을 그때그때 바꾸는 연산은  
> 객체 프로퍼티 접근 관련 최적화를 망치기 때문에 매우 느리다. 
> 
> 그러므로 `[[Prototype]]`을 바꾸는 것이 어떤 결과를 초래할지 확실히 알거나 속도가 전혀 중요하지 않은 경우가 아니라면 `[[Prototype]]`을 바꾸면 안된다.

## 아주 단순한 객체

알다시피 객체는 `키-값`쌍을 저장할 수 있는 연관 배열이다.

그런데 커스텀 사전을 만드는 것과 같이 사용자가 직접 입력한 키를 가지고 객체를 만들다 보면 사소한 결함이 발견된다.

**다른 문자열은 괜찮지만 `__proto__`는 키로 사용할 수 없다는 결함.**

```javascript
let obj = {};

let key = "__proto__";
obj[key] = "...값...";

console.log(obj[key]); // "...값..."이 아닌 [object Object]가 출력됩니다.
```

`__proto__` 프로퍼티는 특별한 프로퍼티라는 것을 이미 알고 있기 때문에 그렇게 놀랄만한 일은 아니긴 하지만. 

`__proto__`는 항상 객체이거나 `null`이어야 한다. 

**문자열은 프로토타입이 될 수 없습니다.**

그런데 사실 이런 결과를 의도하면서 구현한 건 아닐 것이다. 

키가 무엇이 되었든, 키-값 쌍을 저장하려고 하는데 

키가 `__proto__`일 때 값이 제대로 저장되지 않는 건 명백한 버그.

위 예시에선 이 버그가 그리 치명적이진 않습니다. 

그런데 할당 값이 **객체일 때는 프로토타입이 바뀔 수 있다**는 치명적인 버그가 발생할 수 있습니다. 

프로토타입이 바뀌면 예상치 못한 일이 발생할 수 있기 때문입니다.

개발자들은 대개 프로토타입이 중간에 바뀌는 시나리오는 배제한 채 개발을 진행한다.

이런 고정관념 때문에 버그의 원인을 찾는 게 힘들어진다. 

프로토타입이 바뀐 것을 눈치채지 못하기 때문. 

**서버 사이드에서 자바스크립트를 사용 중일 땐 이런 버그가 취약점이 되기도 한다.**

`toString`을 비롯한 내장 메서드에 할당을 할 때도 같은 이유 때문에 예상치 못한 일이 일어날 수 있다.

**그렇다면 이런 문제는 어떻게 예방할 수 있을까?**

객체 대신 맵을 사용하면 모든 것이 해결된다.

그런데 자바스크립트를 만든 사람들이 아주 오래전부터 이런 문제를 고려했기 때문에 객체를 써도 문제를 피할 수 있다. 

어떤 방법이 있는지 알아보자.

아시다시피 `__proto__`는 객체의 프로퍼티가 아니라 `Object.prototype`의 접근자 프로퍼티다.

![object-prototype-2.png](.images/../images/object-prototype-2.png)

그렇기 때문에 `obj.__proto__`를 읽거나 쓸때는 

이에 대응하는 `getter`·`setter`가 프로토타입에서 호출되고 `[[Prototype]]`을 가져오거나 설정한다.

시작할 때 언급한 것처럼 `__proto__`는 `[[Prototype]]`에 접근하기 위한 방법이지 `[[Prototype]]` 그 자체가 아닌 것.

```javascript
let obj = Object.create(null);

let key = prompt("입력하고자 하는 key는 무엇인가요?", "__proto__");
obj[key] = "...값...";

console.log(obj[key]); // "...값..."이 제대로 출력됩니다.
```

`Object.create(null)`로 객체를 만들면 `__proto__` `getter`와 `setter`를 상속받지 않는다. 

이제 `__proto__`는 평범한 데이터 프로퍼티처럼 처리되므로 버그 없이 예시가 잘 동작하게 된다.

이런 객체는 `‘아주 단순한(very plain)’` 혹은 `‘순수 사전식(pure dictionary)’` 객체라고 부른다. 

일반 객체 `{...}` 보다 훨씬 단순하기 때문.

아주 단순한 객체는 내장 메서드가 없다는 단점이 있다. 

`toString`같은 메서드를 사용할 수 없다.

연관 배열로 쓸 때는 이런 단점이 문제가 되진 않습니다.

객체 관련 메서드 대부분은 `Object.keys(obj)` 같이 `Object.something(...)` 형태다. 

이 메서드들은 프로토타입에 있는 게 아니기 때문에 `'아주 단순한 객체’`에도 사용할 수 있다.

# 정리

프로토타입에 직접 접근할 땐 다음과 같은 모던 메서드를 사용할 수 있다.

* [Object.create(proto, [descriptors])](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/create)
  * `[[Prototype]]`이 `proto`를 참조하는 빈 객체를 만든다.
  * 참조값은 `null`일 수 있다. 
  * 이때 프로퍼티 설명자를 추가로 넘길 수 있다.
* [Object.getPrototypeOf(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/getPrototypeOf)
  * `obj`의 `[[Prototype]]`을 반환한다.
  * `__proto__` `getter`와 같다.
* [Object.setPrototypeOf(obj, proto)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/setPrototypeOf)
  * `obj`의 `[[Prototype]]`이 `proto`가 되도록 설정한다.
  * `__proto__` `setter`와 같다.

사용자가 키를 직접 만들 수 있게 허용하면, 

내장 `__proto__` `getter`·`setter`는 안전하지 않다. 

키가 `"__proto__"`일 때 에러가 발생할 수 있다. 

단순한 에러면 좋겠지만 보통 예측 불가능한 결과가 생깁니다.

이를 방지하려면 `Object.create(null)`을 사용해 `__proto__`가 없는 `'아주 단순한 객체’`를 만들거나, 맵을 일관되게 사용하는 것이 좋다.

한편, `Object.create`를 사용하면 객체의 얕은 복사본(`shallow-copy`)을 만들 수 있다.

```javascript
let clone = Object.create(
        Object.getPrototypeOf(obj), 
        Object.getOwnPropertyDescriptors(obj)
    );
```
`__proto__`는 `[[Prototype]]`의 `getter`·`setter`라는 점과 다른 메서드처럼 `Object.prototype`에 정의되어 있다는 것도 확인.

`Object.create(null)`을 사용하면 프로토타입이 없는 객체를 만들 수 있다. 

이런 객체는 `순수 사전`처럼 사용된다. 

`"__proto__"`를 키로 사용해도 문제를 일으키지 않는다.

이런 내용과 더불어 아래 메서드들을 같이 살펴보면 좋다.

* [Object.keys(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/keys) / [Object.values(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/values) / [Object.entries(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/entries)
  * `obj` 내 열거 가능한 프로퍼티 키, 값, 키-값 쌍을 담은 배열을 반환합니다.
* [Object.getOwnPropertySymbols(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertySymbols)
  * `obj` 내 심볼형 키를 담은 배열을 반환합니다.
* [Object.getOwnPropertyNames(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertyNames)
  * `obj` 내 문자형 키를 담은 배열을 반환합니다.
* [Reflect.ownKeys(obj)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Reflect/ownKeys)
  * `obj`내 키 전체를 담은 배열을 반환합니다.
* [obj.hasOwnProperty(key)](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty)
  * 상속받지 않고 `obj` 자체에 구현된 키 중 이름이 `key`인 것이 있으면 `true`를 반환한다.
  
`Object.keys`를 비롯하여 객체의 프로퍼티를 반환하는 메서드들은 

객체가 **‘직접 소유한’** 프로퍼티만 반환한다. 

상속 프로퍼티는 `for..in`을 사용해 얻을 수 있다.