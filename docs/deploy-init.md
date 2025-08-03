# 해당 문서 기능
- 해당 문서는 단지 초기 배포 파이프라인, 배포환경을 위해 존재하는 문서입니다.
- 다른 문서를 참고할 일이 없으며 단독으로 실행되는 문서입니다.

# be 배포 파이프라인
- application.yml파일 환경별 분리 ex) application-dev.yml, application-local.yml
- github actions 에서 jar파일 빌드 후 서버로 전송 후 실행명령
```
    name: Deploy Spring Boot

    on:
      push:
        branches:
          - main

    jobs:
      build:
        runs-on: ubuntu-latest # github action의 빌드머신 환경을 지정

        steps:
          # 1. Checkout
          - name: Checkout code
            uses: actions/checkout@v4

          # 2. Set up JDK
          - name: Set up JDK 17
            uses: actions/setup-java@v4
            with:
              java-version: '17'
              distribution: 'temurin'

          # 3. Grant execute permission for gradlew
          - name: Grant execute permission for gradlew
            run: chmod +x gradlew

          # 4. Build Jar
          - name: Build with Gradle
            run: ./gradlew clean build

          # 5. Copy Jar to server
          - name: Copy Jar to server
            uses: appleboy/scp-action@v0.1.7
            with:
              host: ${{ secrets.SERVER_HOST }}
              username: ${{ secrets.SERVER_USER }}
              key: ${{ secrets.SERVER_SSH_KEY }}
              source: "build/libs/test-api-0.0.1-SNAPSHOT.jar"
              target: "/home/${{ secrets.SERVER_USER }}/app/test-api.jar"

          # 6. SSH into server and deploy
          - name: Deploy on server
            uses: appleboy/ssh-action@v0.1.7
            with:
              host: ${{ secrets.SERVER_HOST }}
              username: ${{ secrets.SERVER_USER }}
              key: ${{ secrets.SERVER_SSH_KEY }}
              script: |
                # DB JSON을 환경변수에 넣음
                export DB_CONFIG_JSON='${{ secrets.DB_CONFIG_JSON }}'

                # 기존 프로세스 종료
                pkill -f "test-api.jar" || true

                # 잠시 대기 (기존 프로세스 완전 종료 대기)
                sleep 5

                # nohup 실행
                nohup java -jar /home/${{ secrets.SERVER_USER }}/app/test-api.jar --spring.profiles.active=prod > app.log 2>&1 &

                # 애플리케이션 시작 대기
                sleep 15

                # 헬스체크
                echo "헬스체크 시작..."
                for i in {1..10}; do
                  if curl -f https://h-infinite-power.store/test-api/health; then
                    echo "애플리케이션이 정상적으로 시작되었습니다."
                    exit 0
                  fi
                  echo "헬스체크 실패, 재시도 중... ($i/10)"
                  sleep 10
                done
                echo "애플리케이션 시작 실패"
                exit 1

```

