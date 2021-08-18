# 메시지 큐

메시지 지향 미들웨어(`Message Oriented Middleware: MOM`)

비동기 메시지를 사용하는 다른 응용프로그램 사이의 데이터 송수신을 의미하는데   
`MOM`을 구현한 시스템을 메시지큐(`Message Queue:MQ`)라 한다.

`MOM(Message Oriented Middleware)` 를 구현한 솔루션으로  
비동기 메시지를 사용하는 서비스들 사이에서 데이터를 교환해주는 역할을 한다.

`Producer(sender)` 가 메시지를 큐에 전송하면 `Consumer(receiver)` 가 처리하는 방식으로,  
`producer` 와 `consumer` 에 `message` 프로세스가 추가되는 것이 특징이다.

메시지 큐는 별도의 공정 작업을 연기 할 수 있는 유연성을 제공하여  
`SOA (service-oriented architecture)`의 개발에 도움을 줄 수 있다.

프로그래밍에서 MQ는 프로세스 또는 프로그램 인스턴스가 데이터를 서로 교환할때 사용하는 방법이다.  
이때 데이터를 교환할때 시스템이 관리하는 메세지 큐를 이용하는 것이 특징이다.  
이렇게 서로 다른 프로세스나 프로그램 사이에 메시지를 교환할때  
`AMQP(Advanced Message Queueing Protocol)`을 이용한다.   
`AMQP`는 메세지 지향 미들웨어를 위한 __open standard application layer protocol__ 이다.  
`AMQP`를 이용하면 다른 벤더 사이에 메세지를 전송하는 것이 가능한데  
JMS (Java Message Service)가 API를 제공하는것과 달리 AMQP는 wire-protocol을 제공하는데  
이는 octet stream을 이용해서 다른 네트워크 사이에 데이터를 전송할 수 있는 포맷인데 이를 사용한다.

## 장점

* 비 동기(`Asynchronous`)
    * `Queue`에 넣기 때문에 나중에 처리 할 수 있다.
* 비 동조(`Decoupling`)
    * 애플리케이션과 분리 할 수 있다.
* 탄력성(`Resilience`)
    * 일부가 실패 시 전체에 영향을 받지 않는다.
* 과잉(`Redundancy`)
    * 실패 할 경우 재실행 가능
* 보증(`Guarantees`)
    * 작업이 처리된 걸 확인 할 수 있다.
* 확장성(`Scalable`)
    * 다수의 프로세스들이 큐에 메시지를 보낼 수 있다.

`Message Queueing`은

* 대용량 데이터를 처리하기 위해 배치 작업
* 채팅 서비스
* 비동기 데이터
* 기타...

같은 내용을 처리할때 사용한다.

프로세스 단위로 처리하는 웹 요청이나 일반적인 프로그램을 만들어서 사용하는데

사용자가 많아지거나 데이터가 많아지면 요청에 대한 응답을 기다리는 수가 증가하다가  
나중에는 대기 시간이 지연되어서 서비스가 정상적으로 되지 못하는 상황이 오기 때문에

기존에 분산되어 있던 데이터 처리를 한곳으로 집중하면서 메세지 브로커를 두어서  
필요한 프로그램에 작업을 분산 시키는 방법을 하고 싶었기 때문이다.

## JMS 와 비교

### JMS란?

> Java Message Service
> JavaEE에 기반한 애플리케이션 구성요소에서
> 메시지를 작성, 전송, 수신하고 읽을 수 있도록 하는 API

* `AMQP`는 ISO 응용계층의 `MOM` 표준이다.
* `JMS`는 `MOM`를 자바 에서 지원하는 표준 API 이다. (`JMS` ≠ `AMQP`)
* `JMS`는 다른 자바 애플리케이션들끼리 통신이 가능하지만 다른 `MOM`의 통신은 불가능하다. (`AMQP`, `SMTP`등)
* `ActiveMQ`의 `JMS`라이브러리를 사용한 자바 애플리케이션들 끼리 통신이 가능하다
    * 그러나 다른 자바 애플리케이션(`ActiveMQ`를 사용 안함)의 `JMS`와는 통신 할 수 없다.
* `AMQP`는 프로토콜만 맞다면 다른 `AMQP`를 사용한 애플리케이션 끼리 통신이 가능하다
    * 같은 라인인 SMTP 하고도 가능.
* `JMS` 라이브러리엔 `AMQP`를 지원하지 않는다.

---

# 참고

* [종인의 기술 블로그::최신 메시지 큐(Messgae Queue) MQ 기술](https://kji6252.github.io/2015/12/18/message-quere/)