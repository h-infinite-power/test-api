# Test API

Test 용 REST API 서버 - 출석체크 애플리케이션

## 프로젝트 개요

출석체크를 관리하는 REST API 서버입니다. 회원 등록, 출석체크, 좋아요, 댓글 기능을 제공합니다.

## 기술 스택

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- MySQL
- Swagger/OpenAPI 3
- Docker (배포 시)

## 로컬 개발 환경 설정

### 1. 데이터베이스 설정

MySQL을 설치하고 다음과 같이 데이터베이스를 생성합니다:

```sql
CREATE DATABASE test_attendance;
```

### 2. 애플리케이션 실행

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

### 3. API 문서 확인

애플리케이션 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다:
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 배포

### GitHub Secrets 설정

배포를 위해 다음 GitHub Secrets를 설정해야 합니다:

1. `SERVER_HOST`: 배포 대상 서버의 IP 주소
2. `SERVER_USER`: 서버 사용자명
3. `SERVER_SSH_KEY`: 서버 접속용 SSH 개인키
4. `DB_CONFIG_JSON`: 데이터베이스 설정 JSON

#### DB_CONFIG_JSON 예시:
```json
{
  "url": "mysql://localhost:3306/test_attendance",
  "username": "db_user",
  "password": "db_password"
}
```

### 자동 배포

`main` 브랜치에 push하면 GitHub Actions를 통해 자동으로 배포됩니다.

### 수동 배포

```bash
# 1. 빌드
./gradlew clean build -x test

# 2. 서버로 전송
scp build/libs/test-api-0.0.1-SNAPSHOT.jar user@server:/home/user/app/test-api.jar

# 3. 서버에서 실행
ssh user@server
export DB_CONFIG_JSON='{"url":"mysql://localhost:3306/test_attendance","username":"user","password":"pass"}'
nohup java -jar /home/user/app/test-api.jar --spring.profiles.active=prod > app.log 2>&1 &
```

## API 엔드포인트

### 회원 관리
- `POST /test-api/test-members` - 회원 등록
- `GET /test-api/test-members` - 회원 목록 조회
- `GET /test-api/test-members/{id}` - 회원 상세 조회
- `PUT /test-api/test-members/{id}` - 회원 정보 수정
- `DELETE /test-api/test-members/{id}` - 회원 삭제

### 출석 관리
- `POST /test-api/test-attendances` - 출석 등록
- `GET /test-api/test-attendances` - 출석 목록 조회
- `GET /test-api/test-attendances/{id}` - 출석 상세 조회
- `DELETE /test-api/test-attendances/{id}` - 출석 삭제

### 좋아요 관리
- `POST /test-api/test-likes` - 좋아요 등록
- `DELETE /test-api/test-likes/{id}` - 좋아요 삭제

### 댓글 관리
- `POST /test-api/test-comments` - 댓글 등록
- `PUT /test-api/test-comments/{id}` - 댓글 수정
- `DELETE /test-api/test-comments/{id}` - 댓글 삭제

## 헬스체크

애플리케이션 상태를 확인할 수 있는 헬스체크 엔드포인트:
- `GET /actuator/health`

## 개발 가이드

프로젝트의 상세한 개발 가이드는 [docs/project-detail.txt](docs/deploy-init.md)를 참고하세요.