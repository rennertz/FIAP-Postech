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

### Unity tests
```
mvn test
```

### Integration tests
```
mvn test -P integration-test
```

### System tests
```
mvn test -P system-test
```
[Resultado dos testes de sistema](target/cucumber-reports/cucumber.html)

## Use

http://localhost:8080/booknrest/swagger-ui/index.html

http://localhost:8080/booknrest/api-docs