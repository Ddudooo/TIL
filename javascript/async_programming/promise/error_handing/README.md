# 프로미스 에러 핸들링

프로미스가 거부되면 제어 흐름이 제일 가까운 `rejection` 핸들러로 넘어가기 때문에 프로미스 체인을 사용하면 에러를 쉽게 처리할 수 있다.

존재하지 않는 주소를 `fetch`로 넘겨주는 예시

```javascript
fetch('https://no-such-server.blabla')
  .then(response => response.json())
  .catch(err => console.log(err))
```

예시 코드로 볼 수 있듯, `.catch`는 첫번째 핸들러로 올 필요 없고

하나 혹은 여러개의 `.then`뒤에 올 수 있다.


```javascript
let user = {
    name : "Ddudooo"
};

fetch(`https://api.github.com/users/${user.name}`)
  .then(response => response.json())
  .then(githubUser => new Promise((resolve, reject) => {
    console.log(githubUser);
    let img = document.createElement('img');
    img.src = githubUser.avatar_url;
    img.className = "promise-avatar-example";
    document.body.append(img);

    setTimeout(() => {
      img.remove();
      resolve(githubUser);
    }, 3000);
  }))
  .catch(error => console.log(error.message));
```

정상적인 경우라면 `.catch`는 절대 트리거 되지 않는다. 

그런데 네트워크 문제, 잘못된 형식의 JSON 등으로 인해 위쪽 프로미스 중 하나라도 거부되면 `.catch`에서 에러를 잡게 된다.

## 암시적 try...catch

프로미스 executor와 프로미스 핸들러 코드 주위엔 '보이지 않는(암시적) `try..catch`가 있다. 

예외가 발생하면 암시적 `try..catch`에서 예외를 잡고 이를 `reject`처럼 다룬다.

```javascript
new Promise((resolve, reject) => {
  throw new Error("에러 발생!");
}).catch(console.error); // Error: 에러 발생!
```
위 예시는 아래 예시와 똑같이 동작합니다.

```javascript
new Promise((resolve, reject) => {
    reject(new Error("에러 발생!"));
}).catch(console.error);
```
executor 주위의 `암시적 try..catch`는 스스로 에러를 잡고, 에러를 거부상태의 프로미스로 변경시킵니다.

이런 일은 executor 함수뿐만 아니라 핸들러에서도 발생한다.

`.then` 핸들러 안에서 `throw`를 사용해 에러를 던지면, 이 자체가 거부된 프로미스를 의미하게 된다. 

따라서 제어 흐름이 가장 가까운 에러 핸들러로 넘어갑니다.

```javascript
new Promise((resolve, reject) => {
  resolve("ok");
}).then((result) => {
  throw new Error("에러 발생!"); // 프로미스가 거부됨
}).catch(console.error); // Error: 에러 발생!
```

`throw` 문으로 생성된 에러뿐만 아니라 모든 종류의 에러가 암시적 `try...catch`에서 처리된다.

## 다시 던지기

체인 마지막의 `.catch`는 `try..catch`와 유사한 역할을 한다. 

`.then` 핸들러를 원하는 만큼 사용하다 마지막에 `.catch` 하나만 붙이면 `.then` 핸들러에서 발생한 모든 에러를 처리할 수 있다.

일반 `try..catch`에선 에러를 분석하고, 처리할 수 없는 에러라 판단되면 에러를 다시 던질 때가 있다.  
프로미스에도 유사한 일을 할 수 있다.

`.catch` 안에서 `throw`를 사용하면 제어 흐름이 가장 가까운 곳에 있는 에러 핸들러로 넘어간다. 

여기서 에러가 성공적으로 처리되면 가장 가까운 곳에 있는 `.then` 핸들러로 제어 흐름이 넘어가 실행이 이어진다.

```javascript
new Promise((resolve, reject)=>{
    throw new Error("에러 발생!");
}).catch((error)=> console.log("에러가 정상적으로 처리됨!"))
.then(()=> console.log("다음 핸들러가 호출됨!"));
```
`.catch`블록이 정상적으로 종료되어, 다음 핸들러인 `.then`이 호출된다.

`.catch`에서 모든 에러를 잡지 못해 다시 에러를 던지는 경우

```javascript
// 실행 순서: catch -> catch
new Promise((resolve, reject) => {

  throw new Error("에러 발생!");

}).catch(function(error) { // (*)

  if (error instanceof URIError) {
    // 에러 처리
  } else {
    console.warn("처리할 수 없는 에러!");    
    throw error; // 에러 다시 던지기
  }

}).then(function() {
  /* 여기는 실행되지 않습니다. */
}).catch(error => { // (**)

  console.error(`알 수 없는 에러가 발생함: ${error}`);
  // 반환값이 없음 => 실행이 계속됨

});
```

실행 흐름이 첫 번째 `.catch` `(*)`로 넘어갔다가 

다음 `.catch` `(**)`로 이어지는 것을 확인할 수 있다.

## 처리되지 못한 거부

체인 끝에 `.catch`를 추가하지 못하는 경우나, `.catch`로 에러를 모두 처리하지 못했을 경우엔 어떻게 처리 될까?

```javascript
new Promise(function() {
  noSuchFunction(); // 에러 (존재하지 않는 함수)
})
  .then(() => {
    // 성공상태의 프로미스를 처리하는 핸들러. 한 개 혹은 여러 개가 있을 수 있음
  }); // 끝에 .catch가 없음!
```

에러가 발생하면 프로미스는 거부상태가 되고, 실행 흐름은 가장 가까운 `rejection` 핸들러로 넘어간다. 

그런데 위 예시엔 예외를 처리해 줄 핸들러가 없어서 에러가 `갇혀버립니다`. 

에러를 처리할 코드가 없기 때문.

이런 식으로 코드에 처리하지 못한 에러가 남게 되면 실무에선 끔찍한 일이 발생한다.

일반적인 에러가 발생하고 이를 `try..catch`에서 처리하지 못하는 경우를 생각해보자. 

스크립트가 죽고 콘솔 창에 메시지가 출력된다. 거부된 프로미스를 처리하지 못했을 때도 유사한 일이 발생한다.

자바스크립트 엔진은 프로미스 거부를 추적하다가 위와 같은 상황이 발생하면 전역 에러를 생성한다.

대개 이런 에러는 회복할 수 없기 때문에 개발자로서 할 수 있는 최선의 방법은 사용자에게 문제 상황을 알리고 가능하다면 서버에 에러 정보를 보내는 것이다.

`Node.js`같은 기타 호스트 환경에도 처리하지 못한 에러를 다루는 방법을 여러 가지 제공한다.

# 정리

* `.catch` 는 프로미스에서 발생한 모든 에러를 다룬다. 
  * `reject()`가 호출되거나 에러가 던져지면 `.catch`에서 이를 처리한다.
* `.catch`는 에러를 처리하고 싶은 지점에 정확히 위치시켜야 한다. 
  * 물론 어떻게 에러를 처리할지 알고 있어야 한다. 
  * 핸들러에선 에러를 분석하고(커스텀 에러 클래스가 이때 도움이 된다) 
  * 알 수 없는 에러(프로그래밍 실수로 발생한 에러일 확률이 높다)는 다시 던질 수 있다.
* 에러 발생 시, 회복할 방법이 없다면 `.catch`를 사용하지 않아도 괜찮다.