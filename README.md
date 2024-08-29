# Dev a Nice Day 백엔드

## 도메인

### User

- 유저 정보
- 유저 접근 관리

### Content

- 컨텐츠를 제공

### Collection

- 컨텐츠를 저장, 가공

### Subscription

- 외부 컨텐츠를 수집

### Image

- 이미지 처리

## 모듈

### app

- 실행 가능한 모듈

#### app:api-app-core

- API 앱 들의 공통 모듈

#### app:api-app

- API 앱

#### app:batch-app

- 배치 앱

#### app:admin-app

- 관리, 운영용 앱

### domain

- 도메인 모듈

#### domain:domain-core

- 프로젝트 전반적으로 사용되는 도메인 클래스를 갖는 모듈
- 외부 의존성 사용 X

#### domain:domain-application

- 도메인 로직을 실행시키는 모듈

### infrastructure

- 외부 의존성을 다룬 모듈

### storage

- DB 모듈

### support

- 앱 실행에 필요한 설정을 담은 모듈

## 실행

### 로컬 환경

루트 디렉토리에 있는 docker-compose.yml 를 통해 로컬 환경을 세팅할 수 있음.

```bash
docker compose up -d
```

### 프로필

- `local` : 로컬 환경
- `local-dev` : 개발 환경
- `dev` : 개발 서버 환경
- `prod` : 운영 서버 환경

### 테스트(gradle task)

- `test`: CI 에서 실행할 테스트들
- `unitTest`: 단위 테스트. Mock 이외 다른 의존성을 갖지 않음.
- `contextTest`: Spring Context 내에서 실행되거나 DB, 외부 API, 멀티스레드 등을 사용하는 테스트
- `restDocsTest`: Spring Rest Docs 를 이용한 테스트
- `developTest`: 개발용 테스트. CI 에서 실행하지 않음. 실험용 실행 등 자유롭게 사용.
