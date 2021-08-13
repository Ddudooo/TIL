# HTTP2

HTTP2 에 대한 몇가지 문서를 먼저 보자

* [HTTP/2 소개](https://developers.google.com/web/fundamentals/performance/http2)
* [Wikipedia::HTTP/2](https://developer.mozilla.org/ko/docs/Glossary/HTTP_2)

## HTTP/1.X 의 문제

기존 HTTP 프로토콜의 경우 메시지 포맷의 단순성과 접근성을 두고 최적화를 하였는데

이로 인해 성능을 희생할 수 밖에 없는 구조가 되었다.

한 커넥션, HTTP 트랜잭션당 하나의 응답 객체만 받는 HTTP 메시지 교환 방식으로 인해

다음 요청까지의 지연을 피할 수 없기 때문

이를 회피하기
위해, [병렬 커넥션이나 파이프라인 커넥션](https://developer.mozilla.org/ko/docs/Web/HTTP/Connection_management_in_HTTP_1.x)
이 도입되었으나 해결책이 되지는 못했다.

이를 해결하기 위해 여러 노력이 이루어졌는데, 그 해결책으로 나온 프로젝트중 하나인

구글의 SPDY 프로토콜이 있다.

SPDY 프로토콜은 ghlwjs wldusdmf wnfdlrl dnlgo

HTTP에 여러 기능을 추가한 것으로, 헤더를 압축하여 대역폭을 줄이고

하나의 커넥션에 여러요청을 동시에 보내 지연율을 줄였으며

클라이언트 요청없이 서버가 능동적으로 리소스를 푸쉬하는 기능을 갖추었다.

이러한 SPDY 프로토콜을 바탕으로 HTTP/2가 나오게되었다.

SPDY 프로토콜에 대해서는 아래 문서를 통해 알아보자

* [NAVERD2::SPDY는 무엇인가?](https://d2.naver.com/helloworld/140351)
* [The Chromium Projects::SPDY](https://www.chromium.org/spdy)

## 그래서 HTTP/2는 뭘까?

HTTP/2, HTTP2에 알아보기전에 HTTP/2가 버저닝으로 `2.X`가 되었지만

기존 HTTP 표준을 대체하는 것이 아니라 확장하는 것으로 다시 한번 알아두자

이는 HTTP의 핵심 개념. HTTP 메소드, 상태코드, URI 및 헤더 필드 등은 변경되지 않는다는 점이다.

한마디로 로우한 레벨에서의 변경으로 이전 프로토콜, `HTTP/1.X`의 성능을 어떻게 해결했을까?

를 중점을 두고 살펴보자

...

---

추가 내용 자체가 많고 머리속으로 정리가 안되서

본문에 참조된 문서를 통해 알아보자

---

# HTTP/1.X 와 차이

하나의 연결에 대해 요청과 응답으로 이루어진 기존의 HTTP 트랜잭션에서

하나의 트랜잭션으로 가상의 쌍방향, 멀티 스트림을 생성하여 처리하였음

HTTP/1.X 의 경우 같은 내용의 헤더라도 다른 요청의 경우 다시 보내야 하였으나,

HTTP/2의 경우, 해당 부분을 해결을 위해 Header Table, Huffman Encoding 기법을 통해 처리하는

HPACK 압축 방식을 사용하여 처리하였다.

또한 HTTP/2 를 사용할 경우,

서버측에서 클라이언트 요청하지않은 리소스를 사전에 푸쉬를 통해 전송할 수 있다.