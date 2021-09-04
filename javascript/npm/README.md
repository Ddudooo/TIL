# NPM

`npm`이란 노드 패키지 매니저(`Node Package Manager`)를 의미한다.

`npm`은 자바스크립트 런타임 환경인 `Node.js`의 기본 패키지 관리자 역할을 한다.  

`npm`을 통해 패키지 또는 모듈를 자신의 프로젝트에 설치할 수 있다.

`npm`을 통해 자신의 프로젝트에서 사용 중인 패키지들의 버전 업데이트도 관리할 수 있다.


[npm공식 홈페이지](https://www.npmjs.com/)

## package.json

노드로 확장 모듈을 작성하면 `npm`을 통해 중앙 저장소로 배포할 수 있다. 

`package.json` 파일은 배포한 모듈 정보를 담고자 만들어졌지만, 노드로 작성하는 애플리케이션도 `package.json` 파일을 사용하여 관리할 수 있다. 

꼭 확장 모듈 형태로 배포하기 위한 것이 아니더라도 애플리케이션을 개발할 때 `package.json` 파일을 이용하면 사용하는 확장 모듈에 대한 의존성 관리가 가능하기 때문에 편리하다. 

`package.json` 파일은 기본적으로 `CommonJS`의 명세를 충실히 따르고 있으며 `JSON` 형식의 파일이다.

직접 `package.json`을 작성할 수도 있지만, `npm init` 명령을 통해 자동으로 생성할 수도 있다.

그리고 해당 애플리케이션을 위해 사용한 확장 모듈에 대한 정보는 `npm install -save`를 통해 자동으로 모듈에 대한 정보를 추가할 수 있다.

```json
{
  "name": "demo",
  "version": "1.0.0",
  "description": "npm 데모",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [
    "demo"
  ],
  "author": "igoa.work@gmail.com",
  "license": "ISC"
}
```

### name

프로젝트 이름으로 **가장 중요**. 

중앙 저장소에 배포할 때 `version`과 함께 필수 항목.

`url`로 사용되고, 설치할 때 디렉토리 이름이 되기 때문에 `url`이나 디렉터리에서 쓸 수 없는 이름을 사용하면 안 된다. 또한, 이름에 node나 js가 들어가면 안 됩니다.  

`name`은 214자보다 짧아야 하며, 점(.)이나 밑줄(_)로 시작할 수 없다.  
대문자를 포함해서는 안 되며, `require()` 함수의 인수로 사용되며 짧고 알기 쉬운 것으로 짓는 것이 좋다.

### version

프로젝트의 버전 정의.

3단계 버전을 사용한다.

`-`을 통해 태그 이름을 적을 수 있다.

### description

프로젝트 설명으로 문자열로 기술.

`npm search`로 검색될때 리스트에 표기되는 정보

### keywords

프로젝트 검색시 참조되는 키워드

### homepage

프로젝트 홈페이지 주소.

### author

프로젝트 작성자 정보, 한사람만을 지정가능

`JSON` 형식으로 `name`, `email`, `url` 옵션을 포함

### contributors

프로젝트에 참여한 공헌자 정보

배열로 여러 사람 지정 가능

### repository

프로젝트의 원격 저장소 정보

홈페이지 url과 다른 정보

### script

프로젝트에 자주 실행되는 명령어.

`script` 형태로 작성되어 `npm` 명령어를 통해 실행 가능

### config

소스 코드에서 `config` 필드에 있는 값을 환경변수 처럼 사용할 수 있다.

### private

이 값이 `true`일 경우 중앙 저장소로 저장하지 않음

### dependencies

프로젝트 의존성 관리를 위한 부분. 

이 프로젝트가 어떤 확장 모듈을 요구하는지 정리할 수 있다.  
일반적으로 `package.json`에서 가장 많은 정보가 입력되는 곳.

애플리케이션을 설치할 때 이 내용을 참조하여 필요한 확장 모듈을 자동으로 설치한다.

개발한 애플리케이션이 특정한 확장 모듈을 사용한다면 여기에 꼭 명시를 해주어야 한다.  
또한, `npm install` 명령은 여기에 포함된 모든 확장 모듈들을 설치하게 되어 있다.

### devDependencies

개발시에만 의존하는 확장 모듈 관리


기타 사용되는 옵션은 [공식 홈페이지](https://docs.npmjs.com/cli/v7/configuring-npm/package-json)에서 확인하자.