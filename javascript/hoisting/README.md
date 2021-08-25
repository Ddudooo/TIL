# 호이스팅

`Hoisting` 은 ECMAScript2015 및 이전 표준 명세에서는 사용된적이 없는 용어이지만...

일반적으로 알려진 호이스팅은 함수안에 있는 선언들을 모두 끌어올려 해당 함수 유효 범위 최상단에 선언하는 것.

마치 최상단으로 끌어올려 선언한것처럼 된다.

라고 알려져있는데, 실제로 해당 선언부가 실제로(물리적) 이동되지는 않고

최상단부에 선언한것 마냥 접근할 수 있게 해준다.

물리적으로 이동하지 않고 어떻게 자바스크립트에서 해당 기능이 가능할까?

## 렉시컬 환경

자바스크립트 엔진은 작성된 소스를 바로 실행하지 않고

컴파일 단계에서 해당 소스가 필요한 메모리 확보를 위해 소스를 스캔하며

함수와 변수 선언을 확인한다.

이때, 메모리상의 key-value 구조인 렉시컬 환경에 추가된다.

## 함수 표현식의 호이스팅

함수 선언부만이 자바스크립트 내부에서 호이스트되며,

함수 표현식은 호이스트 되지 않는다.

```javascript
// 함수 선언부 예시
helloworld();

function helloworld() {
	console.log("Hello World!");
}
```

```javascript
helloworld(); // helloworld is not defined

let helloworld = function () {
	console.log("Hello World");
}
```

## 변수호이스팅

예시 코드로 살펴보자

### var 변수

```javascript
console.log(some); // undefined

var some = 3000;
```

결과값이 `undefined`가 나오는데,

자바스크립트의 호이스트는 초기화가 아닌 오직 선언부만을 호이스트한다.

컴파일동안 자바스크립트는 할당값 대신, 오직 함수와 변수 선언부만을 저장한다.

`var` 변수의 경우 컴파일 단계에서 해당 변수를 발견하면

해당 변수를 렉시컬 환경에 추가하고, 실제 할당이 이루어지는 라인에 와서야

해당 변수에 그값을 추가하며 초기화한다.

### let, const 변수

```javascript
console.log(thing); // thing is not defined

let thing = 3000; 
```

`var` 변수와 다르게 `let`, `const` 변수의 경우 다른 결과를 보인다.

모든 선언은 자바스크립트에서 호이스트 되지만

`var`변수의 경우 호이스트 될때 `undefined`로 초기화가 되며

`let`, `const` 선언은 초기화가 되지 않는 상태로 유지된다.

이들은 오직 렉시컬 바인딩 (할당)이 자바스크립트 엔진 런타임 도중 평가 될때만 초기화 된다.

이 뜻은 엔진이 소스코드에서 선언된 위치에 있는 변수를 평가기 전까지 접근할 수 없다는 것이다.

이는 `TDZ(Temporal Dead Zone)`이라 불리며, 변수 생성과 접근불가한 곳이 초기화되기 까지의 시간 간격을 의미한다.

만약 자바스크립트 엔진이 `let`, `const` 값을 선언된 라인에서 찾지 못하게되면,

`undefined` 값을 할당하거나 `error`(`const`일 경우)를 반환한다.

## Class 호이스팅

`let`과 `const` 선언같이, 자바스크립트의 `class`도 또한 호이스팅이 되고,

평가 도중 초기화하지 않은 상태로 유지된다.

```javascript
let peter = new Person('Peter', 25);
// ReferenceError: Person is not defined

console.log(peter);

class Person {
	constructor(name, age) {
		this.name = name;
		this.age = age;
	}
}
```

Class 표현식 호이스팅 함수 표현식처럼, class 표현식은 호이스팅되지 않는다.

예를 들어, 이 코드는 동작하지 않는다.

```javascript
let peter = new Person('Peter', 25); // ReferenceError: Person is not defined

console.log(peter);
let Person = class {
	constructor(name, age) {
		this.name = name;
		this.age = age;
	}
}
```

올바른 방법은 다음과 같다:

```javascript
let Person = class {
	constructor(name, age) {
		this.name = name;
		this.age = age;
	}
}
let peter = new
Person('Peter', 25);
console.log(peter); // Person { name: 'Peter', age: 25 }
```

## 마무리

함수와 변수는 코드를 실행하기 전에 실행 컨텍스트를 위해 메모리에 저장되며

__이것을 호이스팅이라고 한다.__

* 함수는 전체 함수에 대한 참조와 함께 저장되고
* 값이 인 키워드가 있는 변수 및 및 키워드가 있는 변수는 초기화되지 않은 상태로 저장됩니다.
    * `var``undefined``let``const`