# async await

`async`와 `await`라는 특별한 문법을 사용하면 프로미스를 좀 더 편하게 사용할 수 있다.

## async

`async` 키워드는 `function` 앞에 위치한다.

```javascript
async function getOne(){
    return 1;
}
```

`function` 앞에 `async` 키워드를 붙이면 해당 함수는 항상 프로미스를 반환한다.

프로미스가 아닌 값을 반환하더라도 이행 상태(`resolved`) 프로미스로 감싸 반환한다.

```javascript
async function getOne(){
    return 1;
}

getOne()
    .then(console.log);
```

물론, 명시적으로 프로미스를 반환하는 것도 가능하다.

```javascript
async function getOne(){
    return Promise.resolve(1);
}

getOne()
    .then(console.log);
```

`async`가 붙은 함수는 반드시 프로미스를 반환하고, 프로미스가 아닌 것은 프로미스로 감싸 반환한다.

## await

`await` 키워드는 `async` 함수 안에서만 동작한다.

`await` 문법은 다음과 같다.

```javascript
let value = await promise;
```
자바스크립트는 `await` 키워드를 만나면 프로미스가 처리(`settled`)될 때까지 기다린다. 결과는 그 이후 반환된다.

```javascript
async function example() {

  let promise = new Promise((resolve, reject) => {
    setTimeout(() => console.log("완료!"), 1000)
  });

  let result = await promise; // 프로미스가 이행될 때까지 기다림 (*)

  console.log(result); // "완료!"
}

example();
```

함수를 호출하고, 함수 본문이 실행되는 도중에 `(*)`로 표시한 줄에서 실행이 잠시 `중단`되었다가 프로미스가 처리되면 실행이 재개된다. 

이때 프로미스 객체의 `result` 값이 변수 `result`에 할당된다. 

따라서 위 예시를 실행하면 1초 뒤에 `완료!`가 출력된다.

`await`는 말 그대로 프로미스가 처리될 때까지 함수 실행을 기다리게 만든다.

프로미스가 처리되면 그 결과와 함께 실행이 재개된다. 

프로미스가 처리되길 기다리는 동안엔 엔진이 다른 일(다른 스크립트를 실행, 이벤트 처리 등)을 할 수 있기 때문에, CPU 리소스가 낭비되지 않는다.

`await`는 `promise.then`보다 좀 더 세련되게 프로미스의 `result` 값을 얻을 수 있도록 해주는 문법. 

`promise.then`보다 가독성 좋고 쓰기도 쉽다.

### `await`는 최상위 레벨 코드에서 작동하지 않는다.

`await`는 최상위 레벨 코드에서 작동하지 않는다.

```javascript
// 최상위 레벨 코드에선 문법 에러가 발생함
let response = await fetch('https://api.github.com/users/ddudooo');
let user = await response.json();
```

위와 같은 코드를 정상적으로 실행하기 위해서는

익명 `async` 함수로 감싸서 사용하자

```javascript
(async () =>{
    let response = await fetch('https://api.github.com/users/ddudooo');
    let user = await response.json();
    ...
}){};
```
### `await`는 `thenable` 객체를 받는다.

`promise.then`처럼 `await`에도 `thenable` 객체`(then 메서드가 있는 호출 가능한 객체)`를 사용할 수 있다. 

`thenable` 객체는 서드파티 객체가 프로미스가 아니지만 프로미스와 호환 가능한 객체를 제공할 수 있다는 점에서 생긴 기능. 

서드파티에서 받은 객체가 `.then`을 지원하면 이 객체를 `await`와 함께 사용할 수 있다.

```javascript
class Thenable {
  constructor(num) {
    this.num = num;
  }
  then(resolve, reject) {
    alert(resolve);
    // 1000밀리초 후에 이행됨(result는 this.num*2)
    setTimeout(() => resolve(this.num * 2), 1000); // (*)
  }
};

async function example() {
  // 1초 후, 변수 result는 2가 됨
  let result = await new Thenable(1);
  console.log(result);
}

example();
```

`await`는 `.then`이 구현되어있으면서 프로미스가 아닌 객체를 받으면, 내장 함수 `resolve`와 `reject`를 인수로 제공하는 메서드인 `.then`을 호출한다.  
(일반 Promise executor가 하는 일과 동일) 

그리고 나서 `await`는 `resolve`와 `reject` 중 하나가 호출되길 기다렸다가(`(*)`로 표시한 줄) 호출 결과를 가지고 다음 일을 진행한다.

### async 클래스 메서드

클래스 메서드 앞에 `async` 키워드를 추가하면 async 메서드를 선언할 수 있다.

```javascript
class Waiter {
  async wait() {
    return await Promise.resolve(1);
  }
}

new Waiter()
  .wait()
  .then(console.log); // 1
```
`async` 메서드와 `async` 함수는 프로미스를 반환하고 `await`를 사용할 수 있다는 점에서 동일.

## 에러 핸들링

프로미스가 정상적으로 이행되면 `await promise`는 프로미스 객체의 `result`에 저장된 값을 반환한다. 

반면 프로미스가 거부되면 마치 `throw`문을 작성한 것처럼 에러가 던져진다.

```javascript
async function example() {
  await Promise.reject(new Error("에러 발생!"));
}
```
위, 아래 코드는 동일한 동작.
```javascript
async function example() {
  throw new Error("에러 발생!");
}
```

실제 상황에서는 프로미스가 거부되기 전에 약간의 시간이 지체되는 경우가 있다.

이런 경우엔 `await`가 에러를 던지기 전에 지연이 발생한다.

`await`가 던진 에러는 `throw`가 던진 에러를 잡을 때 처럼 `try...catch`를 통해 잡을 수 있다.

```javascript
async function example(){
    try{
        let response = await fetch('http://wrong-url-for-exception.throw');
    } catch (error){
        console.error(error);
    }
}

example();
```

`try..catch`가 없으면 아래 예시의 `async` 함수 `example()`를 호출해 만든 프로미스가 거부 상태가 된다. 

`example()`에 `.catch`를 추가하면 거부된 프로미스를 처리할 수 있다.

```javascript
async function example() {
    let response = await fetch('http://wrong-url-for-exceptoin.throw');
}

example().catch(console.error);
```

`.catch`를 추가하는 걸 잊으면, 처리되지 않은 프로미스 에러가 발생한다.

### async/await와 promise.then/catch

`async/await`을 사용하면 `await`가 대기를 처리해주기 때문에 `.then`이 거의 필요하지 않다. 

여기에 더하여 `.catch` 대신 일반 `try..catch`를 사용할 수 있다는 장점도 생긴다.

항상 그러한 것은 아니지만, `promise.then`을 사용하는 것보다 `async/await`를 사용하는 것이 대개는 더 편리하다.

그런데 문법 제약 때문에 `async`함수 바깥의 최상위 레벨 코드에선 `await`를 사용할 수 없다. 

그렇기 때문에 관행처럼 `.then/catch`를 추가해 최종 결과나 처리되지 못한 에러를 다룬다. 

### async/await와 Promise.all

여러 개의 프로미스가 모두 처리되길 기다려야 하는 상황이라면 이 프로미스들을 `Promise.all`로 감싸고 여기에 await를 붙여 사용할 수 있다.

```javascript
let result = await Promise.all([
    fetch(url),
    fetch(url2),
    ...
]);
```

실패한 프로미스에서 발생한 에러는 보통 에러와 마찬가지로 `Promise.all`로 전파된다. 
에러 때문에 생긴 예외는 `try..catch`로 감싸 잡을 수 있다.

# 정리

`function` 앞에 `async` 키워드를 추가하면 두 가지 효과가 있다.

1. 함수는 언제나 프로미스를 반환.
2. 함수 안에서 `await`를 사용할 수 있다.

프로미스 앞에 `await` 키워드를 붙이면 자바스크립트는 프로미스가 처리될 때까지 대기한다. 

처리가 완료되면 조건에 따라 아래와 같은 동작이 이어집니다.

1. 에러 발생
   * 예외가 생성됨  
   (에러가 발생한 장소에서 `throw error`를 호출한 것과 동일함)
2. 에러 미발생
   * 프로미스 객체의 `result` 값을 반환

`async/await`를 함께 사용하면 읽고, 쓰기 쉬운 비동기 코드를 작성할 수 있다.

`async/await`를 사용하면 `promise.then/catch`가 거의 필요 없습니다.

하지만 가끔 가장 바깥 스코프에서 비동기 처리가 필요할 때같이 `promise.then/catch`를 써야만 하는 경우가 생기기 때문에 `async/await`가 프로미스를 기반으로 한다는 사실을 알고 있어야 한다. 

여러 작업이 있고, 이 작업들이 모두 완료될 때까지 기다리려면 `Promise.all`을 활용할 수 있다.