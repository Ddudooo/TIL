# 기본 타입

타입스크립트의 기본적인 타입에 대해 알아보자

## 자바스크립트 원시 타입 

자바스크립트의 원시타입인 `string`, `number`, `boolean`

이 타입들은 타입스크립트에서 각각 대응되는 타입이 존재하며 동일한 이름을 가진다.

* `string`은 `"Hello, world"`와 같은 문자열 값을 나타낸다.
* `number`은 숫자를 나타낸다. 
  * `int` 또는 `float`과 같은 것은 존재하지 않는다. 
  * 모든 수는 단순히 `number`.
* `boolean`은 `true`와 `false`라는 두 가지 값만을 가진다.

> `String`, `Number`, `Boolean`와 같은 (대문자로 시작하는) 타입은 유효한 타입이지만  
> 코드상에서 이러한 특수 내장 타입을 사용하는 경우는 극히 드물다. 
> 
> 항상 string, number, boolean 타입을 사용하자.

### 배열

`[...]` 배열의 타입 지정할 때, 배열의 요소에 따른 타입을 지정할 수 있다.

`number[]`, `string[]`, `boolean[]`과 같은 방식으로 사용한다.

또는 `Array<number>`와 같은 형태로 적을 수 있다.

`[number]`와는 다르다.

## 튜플

튜플은 배열의 길이가 고정되고, 각 요소의 타입이 지정된 배열 형식을 의미한다.

```typescript
let tuple: [number, string, boolean] = [1,'string', false];
```

## Enum

`Enum`은 특정 값들의 집합

```typescript
enum Attribute { 
    TEXT, // index - 0
    HTML, // index - 1
    XML, // index - 2
    JSON // index - 3
}

let file: Attribute = Attribute.TEXT;
```
각 요소들은 인덱스가 존재하며 해당 인덱스로도 접근할 수 있다.

인덱스는 개발자가 변경할 수 있으며, 인덱스를 변경할 경우 해당 인덱스 번호 이후 부터 다시 입력된다 

```typescript
enum SomeEnum {
    SOME = 2,
    THING, // 3
    MORE, // 4
}
```

## `any`

타입스크립트는 `any`라는 특별한 타입이 있다.

특정 값으로 인해 타입 검사 오류가 발생하길 원하지 않을 때 사용한다.

어떤 값의 타입이 `any`이면, 해당 값에 대하여 임의의 속성에 접근할 수 있고(이때 반환되는 값의 타입도 `any`), 함수인 것처럼 호출할 수 있고, 다른 임의 타입의 값에 할당하거나(받거나), 그 밖에도 구문적으로 유효한 것이라면 무엇이든 할 수 있다.

### `noImplicitAny`

`any`를 사용하는 상황은 선호되지 않기에 컴파일러 플래그에 `noImplicitAny`를 사용하면 암묵적으로 `any`로 간주되는 모든 경우에 대해 오류가 발생한다.

## `Void`

변수에 설정시에는 `undefined` 혹은 `null`만 할당가능하다

함수에 반환값 사용시에는 반환값을 설정할 수 없는 타입이다.

```typescript
let voidValue: void = undefined;

function consumerFunction(): void {
    // 반환값 설정할 수 없다.
}
```

## `Never`

함수의 끝에는 도달하지 않는 다는 의미의 타입

```typescript
function infintieFunc(): never {
    while(true) {
        // something;
    }
}
```

