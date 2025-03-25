## Run

Para iniciar a aplicação, execute

```shell
./mvnw spring-boot:run
```

alternativamente, execute

```shell
docker compose -f run/compose.yaml up
```

## Test

### Unity tests
```shell
./mvnw test
```

### Integration tests
```shell
./mvnw test -P integration-test
```

### System tests
```shell
./mvnw test -P system-test
```
[Resultado dos testes de sistema](target/cucumber-reports/cucumber.html)

### Performance tests
```shell
./mvnw gatling:test -P performance-test
```

## Use

http://localhost:8080/booknrest/swagger-ui/index.html

http://localhost:8080/booknrest/api-docs