# 함수의 `prototype` 프로퍼티

`new Func()` 과 같은 생성자 함수를 사용하면 새로운 오브젝트를 생성할 수 있다.

이 경우에서 `Func.prototype`이 오브젝트일 경우

`new` 연산자는 `Func.prototype`을 사용해 

새롭게 생성된 오브젝트의 `[[Prototype]]`을 설정한다.

> 자바스크립트가 만들어졌을 때는 프로토타입 기반 상속이 주요 기능 중 하나였다.
>
> 그런데 과거엔 프로토타입에 직접 접근할 방법이 없었다. 
> 
> 그나마 믿고 사용할 수 있었던 방법은 
> 
> 설명할 생성자 함수의 `prototype` 프로퍼티를 이용하는 방법뿐
> 
> 이것이 바로 많은 스크립트가 아직 이 방법을 사용하는 이유.

```javascript
let animal = {
    eats = true
};

function Rabbit(name){
    this.name = name;
}

Rabbit.prototype = animal;

let rabbit = new Rabbit("White Rabbit"); // rabbit.__proto__ == animal

console.log(rabbit.eats); // true;
```

`Rabbit.prototype = animal` 은 `new Rabbit`을 호출해 만든 새로운 오브젝트의 

`[[Prototype]]`을 `animal`로 설정하라는 것을 의미

> `Func.prototype` 은 `new Func`을 호출할 경우에만 사용된다.
>
> 새로운 오브젝트가 만들어진 후에 `Func.prototype` 프로퍼티가 바뀌면 
>
> `new Func`로 만들어지는 새로운 오브젝트는 또 다른 오브젝트를 `[[Prototype]]`으로 갖게됨.
> 
> 다만, 기존에 있던 오브젝트의 `[[Prototype]]`은 그대로 유지.

## 함수의 prototype 프로퍼티와 constructor 프로퍼티

개발자가 특별히 할당하지 않더라도 모든 함수는 `prototype` 프로퍼티를 갖습니다.

기본 프로퍼티인 `prototype`은 `constructor` 프로퍼티 하나만 있는 오브젝트를 가리키는데, 이 `constructor` 프로퍼티는 함수 자신을 가리킵니다.

```javascript
function Rabbit(){}

/*
기본 prototype
Rabbit.prototype = { constructor: Rabbit };
*/
```

`constructor` 프로퍼티를 사용하면 

기존에 있던 오브젝트의 `constructor`를 사용해 새로운 오브젝트를 만들 수 있습니다.

오브젝트가 있는데 이 오브젝트를 만들 때 어떤 생성자가 사용되었는지 알 수 없는 경우

(예: 오브젝트가 서드 파티 라이브러리에서 온 경우), 이 방법을 유용하게 쓸 수 있습니다.

어느 방식을 사용해 오브젝트를 만들든 `constructor`에서 가장 중요한 점은 다음과 같습니다.

**자바스크립트는 알맞은 "constructor" 값을 보장하지 않습니다.**

함수에 기본으로 `prototype` 값이 설정되긴 하지만 그게 전부입니다. 

`constructor`에 벌어지는 모든 일은 전적으로 개발자에게 달려있습니다.

함수의 기본 `prototype` 값을 다른 오브젝트로 바꾸면 

이 오브젝트엔 `constructor`가 없을 겁니다.

이런 상황을 방지하고 알맞은 `constructor`를 유지하려면 

`prototype` 전체를 덮어쓰지 말고 기본 `prototype`에 원하는 프로퍼티를 추가/제거해야 합니다.

또는 `constructor` 프로퍼티를 수동으로 다시 만들어주는 것도 대안이 될 수 있다.

# 정리 

`F.prototype` 프로퍼티는 `[[Prototype]]`과는 다르다. 

`F.prototype`은 `new F()`를 호출할 때 만들어지는 

새로운 오브젝트의 `[[Prototype]]`을 설정합니다.

`F.prototype`의 값은 오브젝트나 `null`만 가능합니다. 다른 값은 무시됩니다.

생성자 함수에 `prototype`를 설정하고, 이 생성자 함수를 `new`를 사용해 호출할 때만 적용됩니다.

일반 오브젝트에 `prototype` 프로퍼티를 사용하면 아무런 일이 일어나지 않습니다.

```javascript
let user = {
  name: "John",
  prototype: "Bla-bla" // 마술은 일어나지 않습니다.
};
```

모든 함수는 기본적으로 `F.prototype = { constructor : F }`를 가지고 있으므로 함수의 `constructor` 프로퍼티를 사용하면 오브젝트의 생성자를 얻을 수 있습니다.

