# 프로미스 API

`Promise`의 5가지 정적 클래스

## Promise.all

여러개의 프로미스를 동시에 실행 시킨후, 모두 완료된후 처리할 경우 사용하는 API


```javascript
let promise = Promise.all([...Promise...]);
```

`Promise.all`은 요소 전체가 프로미스인 배열(이터러블)을 받고 새로운 프로미스를 반환한다.

배열 안 프로미스가 **모두 처리되면** 새로운 프로미스가 이행되며

배열 안 프로미스의 결괏값을 담은 배열이 새로운 프로미스의 `result`가 된다.

```javascript
Promise.all([
    new Promise(resolve => setTimeout(()=>resolve(1), 3000)),
    new Promise(resolve => setTimeout(()=>resolve(2), 2000)),
    new Promise(resolve => setTimeout(()=>resolve(3), 1000))
])
.then(console.log); // 프로미스 전체가 반환되면 1, 2, 3이 반환된다.
```
`result`의 요소 순서는 `Promise.all`에 전달되는 프로미스 순서와 같다.

`Promise.all`의 첫번째 프로미스가 가장 늦게 이행되더라도 처리 결과는 배열의 첫 요소에 저장된다.

`fetch`를 사용한 `Promise.all` 예시

```javascript
let urls = [
  'https://api.github.com/users/iliakan',
  'https://api.github.com/users/remy',
  'https://api.github.com/users/jeresig'
];

// fetch를 사용해 url을 프로미스로 매핑합니다.
let requests = urls.map(url => fetch(url));

// Promise.all은 모든 작업이 이행될 때까지 기다립니다.
Promise.all(requests)
  .then(responses => responses.forEach(
    response => console.log(`${response.url}: ${response.status}`)
  ));
```

`Promise.all`에 전달되는 프로미스 중 하나라도 거부된다면

`Promise.all`이 반환하는 프로미스는 에러와 함께 바로 거부된다.

```javascript
Promise.all([
    new Promise((resolve,reject)=>setTimeout(()=>resolve(1),1000)),
    new Promise((resolve, reject)=>setTimeout(()=>reject(new Error("에러 발생!")),2000)),
    new Promise((resolve, reject) => setTimeout(()=>resolve(3),3000))
]).catch(console.error);
```

2초 후 두 번째 프로미스가 거부되면서 `Promise.all` 전체가 거부되고

`.catch`가 실행됩니다. 거부 에러는 `Promise.all` 전체의 결과가 됩니다.

> 에러가 발생하면 다른 프로미스는 무시된다.
> 
> 프로미스가 하나라도 거부되면 `Promise.all`은 즉시 거부되고 배열에 저장된 다른 프로미스의 결과는 완전히 잊힌다. 
> 
> 이행된 프로미스의 결과도 무시.
>
> `fetch`를 사용해 호출 여러 개를 만들면, 그중 하나가 실패하더라도 호출은 계속 일어난다.  
> 그렇더라도 `Promise.all`은 다른 호출을 더는 신경 쓰지 않는다.  
> 프로미스가 처리되긴 하겠지만 그 결과는 무시된다.
> 프로미스에는 '취소’라는 개념이 없어서 `Promise.all`도 프로미스를 취소하지 않는다.

`Promise.all`에는 이터러블 객체가 아닌 일반 값도 넘길 수 있다.

`Promise.all(...)`에는 대개 프로미스가 요소인 이터러블 객체를 받지만

프로미스가 아닌 객체가 배열을 구성하면, 해당 요소 그대로 결과 배열로 전달된다.

```javascript
Promise.all([
  new Promise((resolve, reject) => {
    setTimeout(() => resolve(1), 1000)
  }),
  2,
  3
]).then(console.log); // 1, 2, 3
```

## Promise.allSettled

`Promise.allSettled`는 모든 프로미스가 처리될 때까지 기다립니다. 

반환되는 배열은 다음과 같은 요소를 갖습니다.

* 응답이 성공할 경우
  * `{status:"fulfilled", value:result}`
* 에러가 발생한 경우
  * `{status:"rejected", reason:error}`

예시를 통해 좀 더 알아보자

```javascript
let urls = [
  'https://api.github.com/users/iliakan',
  'https://api.github.com/users/remy',
  'https://no-such-url'
];

Promise.allSettled(urls.map(url => fetch(url)))
  .then(results => { // (*)
    results.forEach((result, num) => {
      if (result.status == "fulfilled") {
        console.log(`${urls[num]}: ${result.value.status}`);
      }
      if (result.status == "rejected") {
        console.log(`${urls[num]}: ${result.reason}`);
      }
    });
  });
```

`Promise.allSettled`를 사용하면 이처럼 각 프로미스의 상태와 값 또는 에러를 받을 수 있다.

## Promise.race

`Promise.race`는 `Promise.all`과 유사하지만,

가장 먼저 처리되는 프로미스의 결과(혹은 에러)를 반환한다.

```javascript
let promiseRace = Promise.race(iterable);
```
```javascript
Promise.race([
  new Promise((resolve, reject) => setTimeout(() => resolve(1), 1000)),
  new Promise((resolve, reject) => setTimeout(() => reject(new Error("에러 발생!")), 2000)),
  new Promise((resolve, reject) => setTimeout(() => resolve(3), 3000))
]).then(console.log); // 1
```
가장 빨리 처리상태가 되는 프로미스의 `result` 값으로 받기에

이후 처리되는 프로미스의 결과는 무시된다.

## Promise.resolve reject

프로미스 메서드 `Promise.resolve`와 `Promise.reject`는 `async/await` 문법이 생긴 후로 쓸모없어졌기 때문에 근래에는 거의 사용하지 않습니다.

### Promise.resolve

`Promise.resolve(value)`는 결괏값이 `value`인 이행 상태 프로미스를 생성합니다.

```javascript
// 프로미스로 표현한 resolve API
let promise = new Promise(resolve=> resolve(value));
```

`Promise.resolve`는 호환성을 위해 함수가 프로미스를 반드시 반환하도록 할 때 사용할 수 있다.

### Promise.reject

`Promise.reject(error)`는 결과값이 `error`인 거부 상태 프로미스를 생성한다.

```javascript
//프로미스로 표현한 reject API
let promise = new Promise((null, reject)=> reject(error));
```

거의 쓸일 없음.

# 정리

Promise 클래스에는 5가지 정적 메서드가 있다.

1. `Promise.all(promises)`
   * 모든 프로미스가 이행될 때까지 기다렸다가 그 결괏값을 담은 배열을 반환한다.
   * 주어진 프로미스 중 하나라도 실패하면 `Promise.all`는 거부되고, 나머지 프로미스의 결과는 무시된다.
2. `Promise.allSettled(promises)`
   * 최근에 추가된 메서드로 모든 프로미스가 처리될 때까지 기다렸다가 그 결과(객체)를 담은 배열을 반환한다. 
   * 객체엔 다음과 같은 정보가 담긴다.
     * status: "fulfilled" 또는 "rejected"
     * value(프로미스가 성공한 경우) 또는 reason(프로미스가 실패한 경우)
3. `Promise.race(promises)`
   * 가장 먼저 처리된 프로미스의 결과 또는 에러를 담은 프로미스를 반환한다.
4. `Promise.resolve(value)`
   * 주어진 값을 사용해 이행 상태의 프로미스를 만든다.
5. `Promise.reject(error)`
   * 주어진 에러를 사용해 거부 상태의 프로미스를 만든다.

실무에선 다섯 메서드 중 `Promise.all`을 가장 많이 사용한다.