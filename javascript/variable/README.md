# 변수 선언

`var` 변수의 문제와 새로운 변수 `let`과 `const`를 통해 해결방법을 알아보자

`var` 변수의 문제는 [호이스팅](/javascript/hoisting/README.md) 으로 인해 생기는 문제가 대부분일것으로 생각된다.

## var 의 문제

`var` 로 선언한 변수의 스코프는 함수와 동일한 스코프를 가지거나 전역 스코프다

블록 기준으로 스코프가 생기지 않기에 블록 밖에서 접근이 가능하다.

```javascript
if (true) {
  var test = true; // 'let' 대신 'var'를 사용했습니다.
}

console.log(test); // true(if 문이 끝났어도 변수에 여전히 접근할 수 있음)
```

`var`는 코드 블록을 무시하기 때문에 test는 전역 변수가 된다.

전역 스코프에서 이 변수에 접근할 수 있다.

두 번째 행에서 `var test`가 아닌 `let test`를 사용했다면, 변수 `test`는 if문 안에서만 접근할 수 있게 된다.

```javascript
if (true) {
  let test = true; // 'let'으로 변수를 선언함
}

console.log(test); // Error: test is not defined
```

`for` 반복문에서 `var` 변수를 쓸 때도 비슷한 현상이 일어나는데

`var` 변수가 블록이나 루프 수준의 스코프를 형성하지 않기에 일어난다.

```javascript
for (var i = 0; i < 10; i++) {
  // ...
}

console.log(i); // 10, 반복문이 종료되었지만 'i'는 전역 변수이므로 여전히 접근 가능
```

코드 블록이 함수 안에 있다면, var는 함수 레벨 변수가 됩니다.

```javascript
function hello(){
    var text = "Hello";

    console.log(text);
}

hello();
console.log(text); // Error: text is not defined
```

위에서 살펴본 바와 같이, `var`는 `if`, `for` 등의 코드 블록을 관통한다.

이는 아주 오래전의 자바스크립트에선 블록 수준 렉시컬 환경이 만들어 지지 않았기 때문.

### `var`는 재선언이 가능하다

`var`는 또한 `let`과 다르게 재선언을 허용한다.

```javascript
let some;
let some; // SyntaxError: `some` has already been declared
```

```javascript
var some = "Thing";

var some = "Another"; // 여기서 `var` 는 아무것도 안한다 (이미 정의되었기 때문에)
// 어떠한 에러 또한 발생하지 않는다.

console.log(some); // Another
```

### 선언전부터 접근 가능한 `var`

`var` 선언은 함수가 시작될 때 처리된다.

전역 스코프에서 선언했다면 스크립트가 시작될 때 처리된다.

함수 본문 내에서 `var` 선언한 변수는 선언 위치에 상관없이 함수 본문이 시작되는 시점에서 정의된다.  
(해당 변수가 중첩 함수 내에서 정의되지 않아야 해당 규칙이 적용)

```javascript
function hello() {
  text = "Hello";

  console.log(text);

  var text;
}
hello();
```

```javascript
function hello(){
    var text;

    text = "Hello";

    console.log(text);
}
hello();
```

이 두 방식은 모두 동일하게 동작한다.

그 이유는 호이스팅에 의해 `var` 변수가 접근 가능해지기 때문

좀 더 알아보려면 호이스팅에 대해 다시 한번 살펴보자

### 즉시 실행 함수 표현식

`var` 도 블록 레벨 스코프를 가질 수 있게 하는 프로그래밍 방식

`즉시 실행 함수 표현식(Immediately-Invoked Function Expressions)` 

요즘엔 자주 쓰이지 않지만, 오래된 소스에서는 볼 수도 있기에 알아두자.

```javascript
(function() {
    let message = "Hello";

    console.log(message);// Hello
})();
```

함수 표현식이 만들어지고 바로 호출되면서, 해당 함수가 바로 실행된다.

즉시 실행 함수를 만들때는 함수 표현식을 괄호로 둘러쌓는 `(function{...})`형태로 만든다.

`( )`괄호로 둘러싸지 않으면 에러가 발생한다.

자바스크립트는 `function` 키워드를 만나면 함수 선언문이 시작될것이라 예상한다.

그런데 함수 선언문으로 함수를 만들때 이름이 반드시 필요하다.

이름이 없으면 에러가 발생한다.

그렇다면 이름을 넣으면 되는것 아닌가? 

이름을 넣는다고 해도 즉시 실행을 위해 `()` 사용하면 문법에러가 발생한다.

자바스크립트에서는 함수 선언문으로 정의된 함수와 동시에 호출하는 것을 허용하지 않는다.

이를 바로 실행하기 위해 `( )`로 전체 선언문을 둘러싸 표현식처럼 사용하여 즉시 실행하는 것.

사실 이 내용 모두 `var` 사용을 피하면 사용할 필요가 없다.

## 정리

`var` 선언한 변수는 `let`과 `const`로 선언한 변수와 다른 특성이 있다.

* `var` 변수는 블록 스코프가 아닌 함수 수준의 스코프를 갖는다.
* `var` 선언은 함수가 시작되는 시점에서 처리된다.
    * 전역 공간에서는 스크립트가 시작되는 시점에서 처리된다.

`var` 특성은 대부분의 경우 좋지 않은 부작용을 만들어 낸다.

`let`과 `const`를 사용해 해당 부작용이 없는 블록 레벨 변수를 사용하자.
