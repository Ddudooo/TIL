# instanceof

`instanceof` 연사자를 사용하면 객체가 특정 클래스인지 아닌지 확인할 수 있다.

`instanceof`는 객체간의 상속관계도 확인할 수 있따.

## `instanceof` 연산자

```javascript
obj instanceof Class
```

`obj`가 `Class`에 속하거나 `Class`를 상속받는 클래스에 속할시 `true`가 반환된다.

```javascript
class MyClass {};

let instance = new MyClass();

console.log(instance instanceof MyClass); // true
```

`instanceof`는 클래스 뿐만 아니라 생성자 함수에도 사용할 수 있다.

```javascript
function Myfunc(){};

console.log(new Myfunc instanceof Myfunc); // true
```

당연하게도 내장 객체에도 사용할 수 있다.

```javascript
let ary = [1,2,3];
console.log(ary instanceof Array); // true
console.log(ary instanceof Object); // true
```

`instanceof` 연산자는 보통, 프로토타입 체인을 거슬러 올라가며 인스턴스 여부나 상속 여부를 확인한다. 

그런데 정적 메서드 `Symbol.hasInstance`을 사용하면 직접 확인 로직을 설정할 수도 있다.

`obj instanceof Class`은 대략 아래와 같은 알고리즘으로 동작한다.

1. 클래스에 정적 메서드 `Symbol.hasInstance`가 구현되어 있으면
   * `obj instanceof Class`문이 실행될 때, `Class[Symbol.hasInstance](obj)`가 호출. 
     * 이때, 호출 결과는 true나 false이어야 한다. 
     * 이런 규칙을 기반으로 `instanceof`의 동작을 커스터마이징 할 수 있다.
     * ```javascript
       class MyClass {
           static [Symbol.hasInstance](obj) {
               if(obj.someProperty) return true;
           }
       }
       let obj = { someProperty : true };
       console.log(obj instanceof MyClass); // true
       ```
2. 대부분의 클래스에는 `Symbol.hasInstance`가 구현되지 않음
   * 이때는 일반적이 로직이 사용
     * `obj instanceof Class`는 `Class.prototype`이 `obj`프로토타입 체인상의 프로토타입과 일치하는지 확인. 
     * ```javascript
       obj.__proto__ === Class.prototype?
       obj.__proto__.__proto__ === Class.prototype?
       obj.__proto__.__proto__ === Class.prototype?
       // ...
       ``` 

`objA`가 `objB`의 프로토타입 체인 상 어딘가에 있으면 `true`를 반환해주는 메서드  
` objA.isPrototypeOf(objB)`도 있다. 

`obj instanceof Class`는 `Class.prototype.isPrototypeOf(obj)`와 동일

`isPrototypeOf`는 `Class` 생성자를 제외하고 포함 여부를 검사한다. 

검사 시, 프로토타입 체인과 `Class.prototype`만 고려한다.

`isPrototypeOf`의 이런 특징은 객체 생성 후 `prototype` 프로퍼티가 변경되는 경우 특이한 결과를 초래하기도 한다.

## Object.prototype.toString

일반 객체를 문자열로 변환시 `[object Object]`가 된다.

```javascript
let obj

console.log(obj);
console.log(obj.toString());
```

`toString`의 숨겨진 기능을 사용하면 확장 `typeof`, `instanceof`의 대안을 만들 수 있다.

객체에서 내장 `toString`을 추출하는 게 가능하다. 

이렇게 추출한 메서드는 모든 값을 대상으로 실행할 수 있다. 

호출 결과는 값에 따라 달라진다.

* 숫자형 – `[object Number]`
* 불린형 – `[object Boolean]`
* null – `[object Null]`
* undefined – `[object Undefined]`
* 배열 – `[object Array]`
* 그외 – `커스터마이징 가능`

`toString` 알고리즘은 내부적으로 `this`를 검사하고 상응하는 결과를 반환

### `Symbol.toStringTag`

특수 객체 프로퍼티 `Symbol.toStringTag`를 사용하면 `toString`의 동작을 커스터마이징 할 수 있다.

```javascript
let user = {
  [Symbol.toStringTag]: "User"
};

console.log( {}.toString.call(user) ); // [object User]
```

`typeof` 연산자의 강력한 변형들(`toString`과 `toStringTag`)은 원시 자료형뿐만 아니라 내장 객체에도 사용할 수 있다.

내장 객체의 타입 확인을 넘어서 타입을 문자열 형태로 받고 싶다면 `instanceof` 대신, `{}.toString.call`을 사용할 수 있다.

