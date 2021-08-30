# 자바스크립트의 비동기 프로그래밍

자바스크립트에서 비동기처리하는 방식은 크게 두가지로 나뉜다

1. 콜백 함수를 활용
2. `Promise`를 활용

콜백 패턴 예시

```javascript
function requestData(callback) {
    setTimeout(()=>{
        callback({name:'abc', age: 23});
    },1000);
};

function onSuccess(data) {
    console.log(data);
}

console.log(`call requestData`);
requestData(onSuccess); // { name: 'abc', age: 23}
```

프로미스 예시

```javascript
let requestData = new Promise((resolve, reject) => {
    setTimeout(()=>{
        let data = {name:'abc',age:23}
        resolve(data);
    },1000);
})

requestData()
    .then(data => {
        console.log(data);
    })
```

코드에서 볼 수 있듯, 비동기 프로그래밍 작성시 순차적으로 작성가능