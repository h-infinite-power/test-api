# be 프로젝트 초기 세팅
- swagger 세팅
- logback 세팅
- CORS 오류 회피용 * 설정(차후 보안강화하고 초기에는 전체 browser에서 출처가 달라도 요청 가능하도록 처리) -> WebConfig implements WebMvcConfigurer 사용
- db access
    - db는 url, username, password 를 json형태인 환경변수로 받아 실행할것이기 때문에 자동실행이 아닌 수동실행이 돼야한다.
        ```
        // Database 접속정보 예시
            - dbms : Mysql
            - 스키마 : test_attendance
            - ddl정의서 경로 : ./db-ddl
            - 데이터베이스 접속 정보 :
              - host : localhost
              - port : 3306
              - username : root
              - password : password
            - db-access : JPA
        ```
    - application.yml 파일에 physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    - DataSourceConfig 를 만들어서 직접 직접 커넥션한다.
        ex)
        ```
        // 배포시 export되는 환경변수
        {
          "url": "mysql://host:3306/dbname",
          "username": "db_user",
          "password": "db_password"
        }
        ```

        ```
        // DataSourceConfig.java
            @Value("${DB_CONFIG_JSON}")
            private String dbConfigJson;

            @Bean
            public DataSource dataSource() throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> dbConfig = mapper.readValue(dbConfigJson, Map.class);

                String url = dbConfig.get("url");
                String username = dbConfig.get("username");
                String password = dbConfig.get("password");

                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                dataSource.setUrl(url);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                return dataSource;
            }
        ```
        ```
        // application.yml 명시적 선언 -> 없어도 생략되지만 가독성을 위해 선언
           spring:
             autoconfigure:
               exclude:
                 - org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
        ```
