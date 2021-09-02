# 내장 클래스 확장

배열, 맵과 같은 내장 클래스도 상속이 가능하다.

아래와 같이 기존 클래스를 상속하는 것처럼 `extends` 키워드를 통해 내장 클래스를 상속받아서 사용한다.

```javascript
class ExtendsArray extends Array {
    isEmpty() {
        return this.length === 0;
    }
}

let arr = new ExtendsArray(1,2,5,10,20);
console.log(arr.isEmpty());

let filterAry = arr.filter(item => item >=10);
console.log(filterAry);
console.log(filterAry.isEmpty());
```

`filter`, `map`등 내장 클래스의 메서드도 상속된 클래스를 반환한다.

이러한 객체가 구현될때는 객체의 `constructor` 프로퍼티를 사용한다.

```javascript
arr.constructor === ExtendsArray
```

`arr.filter()`가 호출될 때, 내부에선 기본 `Array`가 아닌 `arr.constructor`를 기반으로 새로운 배열이 만들어지고 여기에 필터 후 결과가 담깁니다. 

이렇게 되면 `ExtendsArray`에 구현된 메서드를 사용할 수 있다는 장점이 생깁니다.

물론 동작 방식을 변경할 수 있다.

특수 정적 `getter`인 `Symbol.species`를 클래스에 추가할 수 있는데,  
`Symbol.species`가 있으면 `map`, `filter` 등의 메서드를 호출할 때 만들어지는 개체의 생성자를 지정할 수 있다.  원하는 생성자를 반환하기만 하면 된다.

`map`이나 `filter`같은 내장 메서드가 일반 배열을 반환하도록 하려면 `Symbol.species`가 `Array`를 반환하도록 해주면 된다.

```javascript
class ExtendsArray extends Array {
    isEmpty() {
        return this.length === 0;
    }
    // 내장 메서드는 반환 값에 명시된 클래스를 생성자로 사용한다.
    static get [Symbol.species]() {
        return Array;
    }
}

let arr = new ExtendsArray(1,2,5,10,20);
console.log(arr.isEmpty());

let filteredAry = arr.filter(item => item >= 10);

console.log(filteredAry.isEmpty());
```

## 내장 객체와 정적 메서드 상속

내장 객체는 `Object.keys`, `Array.isArray` 등의 자체 정적 메서드를 갖는다.

앞서 학습한 바와 같이 네이티브 클래스들은 서로 상속 관계를 맺는다.  
`Array`는 `Object`를 상속받는 것과 같이.

일반적으론 한 클래스가 다른 클래스를 상속받으면 정적 메서드와 그렇지 않은 메서드 모두를 상속받는다.

예로 `Array`와 `Date` 모두 `Object`를 상속받아

두 클래스의 인스턴스에선 `Object.prototype`에 구현된 메서드를 사용할 수 있다.

하지만 `Array.[[Prototype]]`, `Date.[[Prototype]]`은 `Object`를 참조하지 않기에

`Array.keys()`나 `Date.keys()`와 같은 정적 메서드를 인스턴스에서 사용할 수 없다.