# 접근 제어자

객체 지향 프로그래밍에서 프로퍼티와 메소드는 두 그룹으로 분류.

* 내부 인터페이스(internal interface)
  * 동일한 클래스 내의 다른 메소드에선 접근할 수 있음
  * 클래스 밖에선 접근할 수 없는 프로퍼티와 메소드
* 외부 인터페이스(external interface)
  * 클래스 밖에서도 접근 가능한 프로퍼티와 메소드

자바스크립트에는 아래와 같은 두 가지 타입의 객체 필드가 있다.

* `public`
  * 어디서든지 접근할 수 있으며 외부 인터페이스를 구성
* `private`
  * 클래스 내부에서만 접근할 수 있으며 내부 인터페이스를 구성할 때 쓰인다.

자바스크립트 이외의 다수 언어에서 클래스 자신과 자손 클래스에서만 접근을 허용하는 `protected` 필드를 지원한다.

`protected` 필드도 내부 인터페이스를 만들 때 유용하다. 

자손 클래스의 필드에 접근해야 하는 경우가 많기 때문에,  
`protected` 필드는 `private` 필드보다 조금 더 광범위하게 사용된다.

자바스크립트는 `protected` 필드를 지원하지 않지만, `protected를` 사용하면 편리한 점이 많기 때문에 이를 모방해서 사용하는 경우가 많다.

## `protected` 프로퍼티

`protected` 프로퍼티 명 앞엔 밑줄 `_`이 붙는다.

자바스크립트에서 강제한 사항은 아니지만, 밑줄은 프로그래머들 사이에서 외부 접근이 불가능한 프로퍼티나 메서드를 나타낼 때 쓴다.

## 읽기 전용 프로퍼티

읽기 전용 프로퍼티를 만들려면 `setter`는 만들지 않고 `getter`만 만든다.

### `getter`/`setter`

```javascript
class SomeClass {
    _someProperty = 0;

    set property(value) {
        this._someProperty = value;
    }

    get property() {
        return this._someProperty;
    }
}
```
위 아래 모두 같은 동작하는 코드
```javascript
class SomeClass {
    _someProperty = 0;

    setProperty(value) {
        this._someProperty = value;
    }

    getProperty() {
        return this._someProperty;
    }
}
```

`get...` /`set...` 스타일로 작성시에 `get` / `set` 키워드를 통한 작성보다 길어지나

다수의 인자를 받도록 변경할 수 있기에 좀 더 유연하게 작성할 수 있다.

따로 규칙은 없으니, 원하는 스타일로 작성.

## `private`

`private` 프로퍼티와 메서드는 `#`으로 시작. 

`#`이 붙으면 클래스 안에서만 접근할 수 있다.

`#`은 자바스크립트에서 지원하는 문법으로, `private` 필드를 의미. 

`private` 필드는 클래스 외부나 자손 클래스에서 접근할 수 없다.

`private` 필드는 `public` 필드와 상충하지 않는다. 

`private` 프로퍼티 `#someProperty`와 `public` 프로퍼티 `someProperty` 동시에 가질 수 있다.

`protected` 필드와 달리, `private` 필드는 언어 자체에 의해 강제된다는 점이 장점.

접근하기 위해서는 `getter`/`setter`를 통해 접근해야 한다.

### `private`의 `this[name]`

`private` 필드는 특별하여, 보통은 `this[name]`을 사용해 필드에 접근할 수 있지만

`private` 필드는 `this[name]`으로 접근할 수 없다. 

이런 문법적 제약은 필드의 보안을 강화하기 위해 만들어졌다.
