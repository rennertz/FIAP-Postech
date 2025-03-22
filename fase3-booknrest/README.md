## Run

Para iniciar a aplicação, execute

```
./mvnw spring-boot:run
```

alternativamente, execute

```
docker compose -f run/compose.yaml up
```

## Test

Unity tests
```
mvn test
```

Integration tests
```
mvn test -P integration-test
```

## Use

http://localhost:8080/booknrest/swagger-ui/index.html

http://localhost:8080/booknrest/api-docs