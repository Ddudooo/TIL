# 인터페이스

타입스크립트의 핵심 원칙 중 하나는 **타입 검사가 값의 형태**에 초점을 맞추고 있다라는 것.

`덕 타이핑` 혹은 `구조적 서브 타이핑` 이라고도 불린다.

이러한 특징으로 인해 타입스크립트에서는 `인터페이스`를 통해 타입들의 이름을 짓는 역할을 하고 코드 안의 계약을 정의하는 것뿐만 아니라 프로젝트 외부에서 사용하는 코드의 계약을 정의하는 강력한 방법을 제공한다.

예제로 살펴보자

```typescript
interface HasNamed {
    name: string;
}

function printName(obj: HasNamed){
    console.log(obj.name);
}

let myObj = { key: `value`, name: `ddudooo`};
printName(myObj);
```

다른 언어처럼 전달한 객체가 이 인터페이스를 구현해야 한다고 명시적으로 얘기할 필요는 없다. 

여기서 중요한 것은 형태뿐, 함수에 전달된 객체가 나열된 요구 조건을 충족하면, 허용.

타입 검사는 프로퍼티들의 순서를 요구하지 않는다. 

단지 인터페이스가 요구하는 프로퍼티들이 존재하는지와 프로퍼티들이 요구하는 타입을 가졌는지만을 확인한다.

## 선택적 프로퍼티 (Optional Properties)

인터페이스의 모든 프로퍼티가 필요한 것은 아니다. 

어떤 조건에서만 존재하거나 아예 없을 수도 있다. 

```typescript
interface SquareConfig {
    color?: string;
    width?: number;
}

function createSquare(config: SquareConfig): {color: string; area: number} {
    let newSquare = {color: "white", area: 100};
    if (config.color) {
        newSquare.color = config.color;
    }
    if (config.width) {
        newSquare.area = config.width * config.width;
    }
    return newSquare;
}

let mySquare = createSquare({color: "black"});
```

선택적 프로퍼티를 가지는 인터페이스는 다른 인터페이스와 비슷하게 작성되고, 선택적 프로퍼티는 선언에서 프로퍼티 이름 끝에 ?를 붙여 표시한다.

## 읽기 전용 프로퍼티

일부 프로퍼티는 객체 생성시에만 수정이 가능해야 한다.

프로퍼티 이름 앞에 `readonly` 키워드를 추가하여 이를 지정할 수 있다.

```typescript
interface Point {
    readonly x: number;
    readonly y: number;
}

let point: Point = { x: 20, y: 20};
// 할당 후에는 프로퍼티 수정시 에러가 발생한다.
point.x = 10; // Cannot assign to 'x' because it is a read-only property.
```

배열의 경우, 다른 언어의 경우 `readonly`와 같은 변경 불가능한 상태로 지정하여도

해당 배열의 주소가 변경만 안되면, 배열의 요소변경은 허용이 가능한데

타입스크립트에서는 불변 요소를 가지게 끔, 배열을 만들 수 있다.

한마디로 각 요소가 `readonly`인 배열.

```typescript
let ary: number[] = [1,2,3,4];
let mutable: ReadonlyArray<number> = [1,2,3,4];

mutable[0] = 12; // Index signature in type 'readonly number[]' only permits reading.
mutable.push(5); // Property 'push' does not exist on type 'readonly number[]'.
mutable.length = 100; // Cannot assign to 'length' because it is a read-only property.
// 'ReadonlyArray를 일반 배열에 재할당 불가능
ary = mutable; // The type 'readonly number[]' is 'readonly' and cannot be assigned to the mutable type 'number[]'.

ary = mutable as number[]; // 타입 단언으로 오버라이드는 가능
```

### `readonly` , `const`

변수는 const를 사용하고 프로퍼티는 readonly를 사용.

## 초과 프로퍼티 검사 `Excess Property Checks`

객체 리터럴은 다른 변수에 할당할 때나 인수로 전달할 때, 특별한 처리를 받고, 초과 프로퍼티 검사 (`excess property checking`)를 받는다. 

만약 객체 리터럴이 `대상 타입 (target type)`이 갖고 있지 않은 프로퍼티를 갖고 있으면, 에러가 발생한다.

```typescript
interface MyInterface {
    name?: string;
    height?: number;
}

function Myfunc(obj: MyInterface): {name:string; height: number}{
    let result = { name: "default", height: 100};
    if(obj.name){
        result.name = obj.name;
    }
    if(obj.height){
        result.height = obj.height;
    }
    return result;
}

console.log(Myfunc({ name: "ddudooo", height: 50})); // (*)
```

`(*)`의 프로퍼티 명을 수정시에 오류가 발생하는데

해당 검사를 피하는 방법으로는 타입 단언을 사용하면 된다.

```typescript
console.log(Myfunc({name:"ddudooo", value: 100} as MyInterface));
```

추가 프로퍼티가 있음을 확신한다면, 문자열 인덱스 서명(`string index signature`)을 추가하는 것이 더 나은 방법이다. 

```typescript
interface MyInterface {
    name?: string;
    height?: string;
    [propName: string]: any;
}
```
이러한 방식으로 인터페이스를 작성하게 되면

여러 프로퍼티를 가질 수 있게 되고, 해당 프로퍼티들이 `name`혹은 `height`가 아니라면 중요하게 보지 않는다.

이러한 방법 외에 검사를 피하는 방법은

객체를 다른 변수에 할당하여, 추가적인 타입 검사를 피하는것.

```typescript
let myInstance = {name:"ddudooo", value: 100}
console.log(Myfunc(myInstance));
```

하지만 인터페이스와 객체간 공통 프로퍼티가 없을 경우 오류가 발생한다.

이 밖에 검사를 피하는 방법이 추가로 있을 수 있으나

대부분의 경우 실제 버그이기에, 해당 오류를 발견시 회피하기 보다는 타입의 정의를 수정해야 한다.

## 함수 타입(`Function types`)

인터페이스는 자바스크립트 객체가 가질 수 있는 넓은 범위의 형태를 기술할 수 있다. 

프로퍼티로 객체를 기술하는 것 외에, 인터페이스는 함수 타입을 설명할 수 있다.

인터페이스로 함수 타입을 기술하기 위해, 인터페이스에 호출 서명 (`call signature`)를 전달한다. 

매개변수 목록과 반환 타입만 주어진 함수 선언과 비슷하며, 각 매개변수는 이름과 타입이 모두 필요하다.

```typescript
interface SomeFunc {
    (target: string, search: string): boolean;
}
```
올바른 함수 타입 검사를 위해, 매개변수의 이름이 같을 필요는 없다.

```typescript
let myFunc: SomeFunc = function(src: string, sub: string): boolean {
    let result = src.search(sub);
    return result > -1;
}
```

함수 매개변수들은 같은 위치에 대응되는 매개변수끼리 한번에 하나씩 검사한다.

예제의 `SomeFunc` 타입의 변수로 직접 함수 값이 할당되었기에 타입스크립트의 문맥상 타이핑으로 인수 타입을 추론할 수 있다.

## 인덱서블 타입 (`Indexable Types`)

인터페이스로 함수 타입을 설명하는 방법과 유사하게,  
`a[10]` 이나 `ageMap["daniel"]` 처럼 타입을 `인덱스로` 기술할 수 있다.

인덱서블 타입은 인덱싱 할때 해당 반환 유형과 함께 객체를 인덱싱하는 데 사용할 수 있는 타입을 기술하는 인덱스 시그니처 (`index signature`)를 가지고 있다. 

```typescript
interface StringArray {
    [index: number]: string;
}

let myArray: StringArray;
myArray = ["홍길동", "갑을병"];

let myStr: string = myArray[0];
```
예제에는 인덱스 서명이 있는 `StringArray` 인터페이스가 있다. 

이 인덱스 서명은 `StringArray`가 `number`로 색인화(`indexed`)되면 `string`을 반환할 것을 나타낸다.

인덱스 서명을 지원하는 타입에는 두 가지가 있습니다: `문자열`과 `숫자`.

두 타입의 인덱서(`indexer`)를 모두 지원하는 것은 가능하지만, 숫자 인덱서에서 반환된 타입은 반드시 문자열 인덱서에서 반환된 타입의 하위 타입(`subtype`)이어야 한다. 

이 이유는 `number`로 인덱싱 할 때, 자바스크립트는 실제로 객체를 인덱싱하기 전에 `string`으로 변환하기 때문.  
즉, 100 (`number`)로 인덱싱하는 것은 "100" (`string`)로 인덱싱하는 것과 같기 때문에, 서로 일관성 있어야 한다.

## 클래스에서 인터페이스 구현

```typescript
interface Flyable{
    fly(): void;
}

class Duck implements Flyable {
    private name:string;
    constructor(name: string) {
        this.name = name;
    }
    fly(){
        console.log(`${this.name} flying!`);
    }
}
```

인터페이스는 `public`한 영역을 기술하기에 `private`에 대해서는 검사할 수 없다.

### 클래스 스태틱과 인스턴스 차이

클래스는 두 가지 타입을 가진다는 것을 기억하는 게 좋다  
`스태틱 타입`과 `인스턴스 타입`.

생성 시그니처 (`construct signature`)로 인터페이스를 생성하고,  
클래스를 생성하려고 한다면, 

인터페이스를 `implements` 할 때, 에러가 발생하는 것을 확인할 수 있다.

클래스가 인터페이스를 `implements` 할 때, 클래스의 인스턴스만 검사하기 때문.

생성자가 스태틱이기에 이 검사에 포함되지 않는다.

## 인터페이스 상속 (`Extending Interfaces`)

인터페이스도 클래스처럼 상속이 가능하다.

이를 통해 인터페이스의 멤버를 다른 인터페이스에 복사하는 것을 가능하게 해주며

인터페이스를 재사용 높은 콤포넌트를 쪼개, 애플리케이션의 유연함을 제공해준다.

```typescript
interface Shape {
    color: string;
}

interface PenStroke {
    penWidth: number;
}

interface Square extends Shape, PenStroke {
    sideLength: number;
}

let square = {} as Square;
square.color = "blue";
square.sideLength = 10;
square.penWidth = 5.0;
```

## 하이브리드 타입 (`Hybrid Types`)

인터페이스는 실제 자바스크립트에 존재하는 다양한 타입들을 기술할 수 있다. 

자바스크립트의 동적이고 유연한 특성 때문에,  
몇몇 타입의 조합으로 동작하는 객체를 가끔 마주할 수 있다.

```typescript
interface Counter {
    (start: number): string;
    interval: number;
    reset(): void;
}

function getCounter(): Counter {
    let counter = (function (start: number) { }) as Counter;
    counter.interval = 123;
    counter.reset = function () { };
    return counter;
}

let c = getCounter();
c(10);
c.reset();
c.interval = 5.0;
```

## 클래스를 상속하는 인터페이스 (`Interface Extending Classes`)

인터페이스 타입이 클래스 타입을 확장하면,  
클래스의 멤버는 상속받지만 구현은 상속받지 않는다. 

이것은 인터페이스가 구현을 제공하지 않고, 클래스의 멤버 모두를 선언한 것과 마찬가지. 

인터페이스는 심지어 기초 클래스의 `private`과 `protected` 멤버도 상속받는다. 

이것은 인터페이스가 `private` 혹은 `protected` 멤버를 포함한 클래스를 확장할 수 있다는 뜻이고, 인터페이스 타입은 그 클래스나 하위클래스에 의해서만 구현될 수 있다.

거대한 상속계층을 가지고 있을 때 유용하며, 특정 프로퍼티를 가진 하위클래스에서만 코드가 동작하도록 지정하는데도 유용하다.