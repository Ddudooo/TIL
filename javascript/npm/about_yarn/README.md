# yarn

`yarn`은 프로젝트의 의존성을 관리하는 자바스크립트의 패키지 매니저.

`npm`과 유사하지만 `facebook`에서 만든 패키지 매니저

## npm vs yarn

`npm`과 `yarn` 모두 프로젝트 의존성 관리해주는 패키지 매니저인데

이미 `npm`이 있을 때 `Yarn`이 개발되었는데, 해당 이유는

1. 패키지 설치 속도가 느려지고 
2. npm에 몇 가지 보안 문제
3. 기타

`npm`이 직면한 주요 문제를 해결하기 위해 `Facebook`에서 구축했다. 

`Yarn`에서 제공하는 기능 혹은 `npm`과 다르게 제공하는 기능에 대해 알아보자

### Package 병렬 설치

`npm`은 여러 `package`를 설치할 때, 순차적으로 각각의 `package`가 완전히 설치된 후 다음이 설치된다.

`Yarn`의 경우 병렬로 설치되어, `npm`에 비해 빠르게 설치가 가능하다고 한다.

### 자동 Lock file 

`npm`과 `yarn` 둘 다 `package.json`에 명시되어 의존성을 관리한다.

버전에 `^`를 붙이게 되면 `package manager`는 새로운 버전이 배포 되었는지 체크한다. 

새로운 버전이 있으면 명시된 버전이 아닌 최신 버전이 설치된다. 

자동으로 설치되는 것을 원치 않는다면 2가지 방법이 있습니다. 

1. `lock file`을 생성. 
2. 다른 하나는 `^`를 제거하는 것.

`yarn`은 자동은 `yarn.lock` 파일을 생성하여 해결한다.

### 보안

`npm`은 다른 `package`를 즉시 포함 시킬 수 있는 코드를 자동으로 실행하므로 보안에 취약하다. 

반면에 `Yarn`은 `yarn.lock` 또는 `package.json`에 있는 파일만 설치한다. 

## 명령어 차이

`npm`과 `yarn`은 둘 다 프로젝트의 의존성을 관리해주는 패키지 매니저이지만

`CLI` 상에서 사용될 때, 다음과 같은 명령어 차이가 있다.

| 커맨드               |                `npm`                 |             `yarn`             |
| :------------------- | :----------------------------------: | :----------------------------: |
| 패키지 설치          |       `npm install [package]`        |      `yarn add [package]       |
| 개발용 패키지 설치   |  `npm install --save-dev [package]`  |   `yarn add --dev [package]`   |
| 패키지 제거          |      `npm uninstall [package]`       |     `yarn remove [package]     |
| 개발용 패키지 제거   | `npm uninstall --save-dev [package]` |    `yarn remove [package]`     |
| 전체 패키지 업데이트 |             `npm update`             |         `yarn upgrade`         |
| 특정 패키지 업데이트 |        `npm update [package]`        |    `yarn upgrade [package]`    |
| 글로벌 패키지 설치   |   `npm install --global [package]`   |  `yarn global add [package]`   |
| 글로벌 패키지 제거   |  `npm uninstall --global [package]`  | `yarn global remove [package]` |

아래는 차이가 없는 명령어들이다.

|        `npm`        |        `yarn`        |
| :-----------------: | :------------------: |
|     `npm init`      |     `yarn init`      |
|      `npm run`      |      `yarn run`      |
|     `npm test`      |     `yarn test`      |
| `npm login(logout)` | `yarn login(logout)` |
|     `npm link`      |     `yarn link`      |
|    `npm publish`    |    `yarn publish`    |
|  `npm cache clean`  |  `yarn cache clean`  |


# 결론 ?

`npm` 또한 업데이트를 거듭하여, 실제 성능차이가 매우 줄어들었다고 한다.

`yarn`은 `npm`에서 확장된 기능을 제공하지만 그만큼 공간을 많이 차지 하는 단점이 있기도 하다.

`npm` `yarn` 모두 좋은 패키지 매니저이므로 잘 비교해서 프로젝트에 맞는 매니저를 선택하자.

