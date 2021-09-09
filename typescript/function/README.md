# 타입스크립트의 함수

타입스크립트에는 클래스, 네임스페이스, 모듈이 있지만, 함수는 여전히 이 일을 어떻게 할 것인지를 설명하는 데 있어 핵심 역할을 수행한다. 

타입스크립트에서는 표준 자바스크립트 함수가 작업을 수월하게 하도록 몇 가지 새로운 기능을 추가한다.

## 함수

타입스크립트 함수는 자바스크립트와 마찬가지로 기명 함수(`named function`)과 익명 함수(`anonymous function`)로 만들 수 있다. 

이를 통해 `API`에서 함수 목록을 작성하든 일회성 함수를 써서 다른 함수로 전달하든 애플리케이션에 가장 적합한 방법을 선택할 수 있다.

```javascript
// named function
function add(x, y) {
    return x+y;
}

// anonymous function
let addFunc = function(x, y){return x+y};
```

자바스크립트에서의 기명 함수와 익명함수 예시

## 함수 타입

```typescript
function add(x: number, y: number): number{
    return x+y;
}

let addFunc = function(x: number, y: number): number { return x+ y;};
```
타입스크립트에서의 기명 함수와 익명함수

함수의 타입은 매개변수의 타입과 반환 타입이 있다.

전체 함수 타입을 작성할때는 이 두가지 타입이 필요하다.

매개변수 목록처럼 각 매개변수에 이름과 타입 명을 작성.

작성 명은 가독성을 위한것.

```typescript
let addFunc: (x: number, y:number) => number = function(x: number, y: number):number { return x+y;};
```

매개변수의 타입들이 올바르게 나열되어 있다면 함수 타입에 이름을 붙이더라도 유효한 타입으로 간주한다.

두 번째로 반환 타입. 

매개변수 타입들과 반환 타입 사이에 `화살표 표기( => )`를 써서 반환 타입을 분명히 할 수 있다. 

만약 함수가 값을 반환하지 않는다면 비워두는 대신 `void`를 써서 표시한다.

## 타입 추론

```typescript
// addFunc는 전체 함수 타입을 가진다
let addFunc = function(x: number, y: number): number { return  x + y; };

// 매개변수 x 와 y는 number 타입을 가진다
let addFunc: (baseValue: number, increment: number) => number =
    function(x, y) { return x + y; };
```

타입스크립트 컴파일러는 표현식의 한쪽에만 타입이 있더라도 타입을 알아낼 수 있다.

이러한 타입 추론 형태를 `contextual typing`이라 부르며, 이를 통해 프로그램에서 타입을 유지하기 위한 노력을 줄일 수 있다.

## 선택적 매개변수와 기본 매개변수

자바스크립트에서는 모든 매개변수가 선택적이고, 사용자는 적합하다고 생각하면 그대로 둘 수 있다. 그렇게 둔다면 그 값은 `undefined`가 된다. 

타입스크립트에서도 선택적 매개변수를 원한다면 매개변수 이름 끝에 `?` 를 붙임으로써 해결할 수 있다.

```typescript 
function buildName(firstName: string, lastName?: string){
    if(lastName){
        return firstName+" "+lastName;
    } else {
        return firstName;
    }
}

console.log(buildName("홍"));
// console.log(buildName("홍", "길동", "28대손")); // 에러
console.log(buildName("홍", "길동"));
```

타입스크립트에서는 함수 매개변수에 기본값을 부여할 수도 있다.

```typescript
function buildName(firstName: string, lastName = "이름") {
    return firstName + " " + lastName;
}
```
모든 필수 매개변수 뒤에 오는 `기본-초기화 매개변수`는 선택적으로 처리되며, 선택적 매개변수와 마찬가지로 해당 함수를 호출할 때 생략할 수 있다.

## Rest Parameters

필수, 선택적, 기본 매개변수는 한 번에 하나의 매개변수만을 가지고 이야기 한다.

때로는 다수의 매개변수를 그룹 지어 작업하기를 원하거나, 함수가 최종적으로 얼마나 많은 매개변수를 취할지 모를 때도 있다. 

자바스크립트에서는 모든 함수 내부에 위치한 `arguments`라는 변수를 사용해 직접 인자를 가지고 작업할 수 있다.

타입스크립트에서는 이 인자들을 하나의 변수로 모을 수 있다

```typescript
function buildName(firstName: string, ...restOfName: string[]){
    return firstName + " "+ restOfName.join(" ");
}
```

나머지 매개변수(`rest parameters`)는 선택적 매개변수들의 수를 무한으로 취급한다. 

나머지 매개변수로 인자들을 넘겨줄 때는 당신이 원하는 만큼 넘겨 줄 수도 있다.  
아무것도 넘겨주지 않을 수도 있다. 

컴파일러는 생략 부호 (`...`) 뒤의 이름으로 전달된 인자 배열을 빌드하여 함수에서 사용할 수 있도록 한다.

## `this`

`this`가 어떻게 쓰이는지 또는 `this`가 잘못 쓰일 때를 발견하는 방법을 배울 필요가 있다. 

다행히도 타입스크립트는 몇 가지 기술들로 잘못된 `this` 사용을 잡아낼 수 있다. 

### 화살표 함수

함수를 반환하거나 인자로 넘길때, `this`는 매우 혼란스러우며 해당 인자가 무엇을 가리키는지 판단하기 어려울때가 있다.

```javascript
let deck = {
    suits: ["hearts", "spades", "clubs", "diamonds"],
    cards: Array(52),
    createCardPicker: function() {
        return function() {
            let pickedCard = Math.floor(Math.random() * 52);
            let pickedSuit = Math.floor(pickedCard / 13);

            return {suit: this.suits[pickedSuit], card: pickedCard % 13};
        }
    }
}

let cardPicker = deck.createCardPicker();
let pickedCard = cardPicker();

console.log("card: " + pickedCard.card + " of " + pickedCard.suit);
```

예시로 든 스크립트를 실행하면 오류가 발생한다.

`createCardPicker`에 의해 생성된 함수에서 사용중인 `this`가 글로벌 `this`로 설정되었기 때문.

해당 문제는  아래와 같이 화살표 함수를 사용하면, 생성된 쪽의 `this`를 캡처하여 사용할 수 있게 된다.

```javascript
let deck = {
    suits: ["hearts", "spades", "clubs", "diamonds"],
    cards: Array(52),
    createCardPicker: function() {       
        return () => {
            let pickedCard = Math.floor(Math.random() * 52);
            let pickedSuit = Math.floor(pickedCard / 13);

            return {suit: this.suits[pickedSuit], card: pickedCard % 13};
        }
    }
}
```

### this parameter

`this.suits[pickedSuit]`의 타입은 `any`다. 

`this`가 객체 리터럴 내부의 함수에서 왔기 때문. 

이것을 고치기 위해 명시적으로 `this` 매개변수를 줄 수 있다. 

`this` 매개변수는 함수의 매개변수 목록에서 가장 먼저 나오는 가짜 매개변수.

```typescript
function f(this: void){
    // 독립적인 함수 에서 this를 사용할 수 없음.
}
```

```typescript
interface Card {
    suit: string;
    card: number;
}
interface Deck {
    suits: string[];
    cards: number[];
    createCardPicker(this: Deck): () => Card;
}
let deck: Deck = {
    suits: ["hearts", "spades", "clubs", "diamonds"],
    cards: Array(52),
    // 반드시 Deck 타입이어야 함을 명시적으로 지정.
    createCardPicker: function(this: Deck) {
        return () => {
            let pickedCard = Math.floor(Math.random() * 52);
            let pickedSuit = Math.floor(pickedCard / 13);

            return {suit: this.suits[pickedSuit], card: pickedCard % 13};
        }
    }
}

```

### 콜백함수에서 `this`

나중에 호출할 콜백 함수를 라이브러리에 전달할 때 `this` 때문에 오류가 발생할 수 있다. 

라이브러리는 콜백을 일반 함수처럼 호출하므로 `this`는 `undefined`가 된다. 

일부 작업에서는 `this` 매개변수를 콜백 오류를 막는데 사용할 수 있다. 

먼저 라이브러리 작성자는 콜백 타입을 `this`로 표시를 해주어야 한다.

```typescript
interface CallbackFunc {
    executor(callback: (this: void, e: Event) => void): void;
}
```

`this: void`는 `executor`가 `callback`이 `this` 타입을 요구하지 않는 함수가 될것으로 예상하는 걸 의미.

`this`로 표시를 한 상태에서는 반드시 인스턴스로써 호출되어야 함을 명시해 주어야 한다. 

그러면 타입스크립트는 `this: void`를 갖는 함수를 필요로 한다는 것을 감지한다. (?)

