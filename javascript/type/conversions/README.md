# 형변환

형변환이란? 

함수와 연산자에 전달되는 값은 대부분 적절한 자료현으로 자동 변환된다.

전달받은 값을 의도를 가지고 명시적으로 변환하는 경우에도 형변환으로 부른다.

## 문자형으로 변환

문자형의 값이 필요할 경우 일어난다.

```javascript
let value = true;
console.log(typeof value); // boolean

value = String(value); // 명시적 형변환
console.log(typeof value); // string
```

`false` 의 경우 문자열 형변환이 일어날 경우 `"false"`로

`null`의 경우 `"null"`로 변환되는 것과 같이 예측 가능한 방식으로 변경된다.

## 숫자형으로 변환

숫자로 변환되는 경우는 수학과 관련된 함수와 표현식에서 자동으로 일어남

```javascript
console.log("6" / "2");

let str = "123";

console.log(typeof str);

let num = Number(str);

console.log(typeof num);
```

숫자 이외의 글자가 들어가 있는 문자열을 숫자형으로 변환하려고 하면 `NaN`이 된다.

|    전달받은 값     | 형 변환 후                              |
| :----------------: | --------------------------------------- |
|    `undefined`     | `NaN`                                   |
|       `null`       | `0`                                     |
| `true` and `false` | `1` and `0`                             |
|      `string`      | 1. 문자열의 처음과 끝의 공백 제거       |
|                    | 2. 공백 제거후 남은 문자열이 없다면 `0` |
|                    | 그렇지 않다면 문자열에서 숫자를 읽음    |
|                    | 3. 변환 실패시 `NaN`                    |

```javascript
console.log(Number("     123     "));
console.log(Number("123z"));
console.log(Number(true));
console.log(Number(false));
```

`null` 과 `undefined` 은 숫자형 변환시 결과가 다르다.

`null` -> `0` , `undefined` -> `NaN`

대부분의 수학 연산은 형변환이 이루어진다.

## 불린형으로 변환

이 형 변환은 논리 연산을 수행할 때 발생한다.

`Boolean(value)`를 호출하면 명시적으로 불리언으로의 형 변환을 수행

* 숫자 `0`, 빈 문자열, `null`, `undefined`, `NaN`
  * 직관적으로도 “비어있다고” 느껴지는 값들은 `false`가 됩니다.
* 그 외의 값은 `true`로 변환됩니다.

```javascript
console.log(Boolean(1)); // true
console.log(Boolean(0)); // false

console.log(Boolean("Hello")); // true
console.log(Boolean("")); // false
```
