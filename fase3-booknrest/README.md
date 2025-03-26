## Run

Para iniciar a aplicação, execute

```shell
./mvnw spring-boot:run
```

alternativamente, execute

```shell
docker compose -f run/compose.yaml up
```
ou ainda, execute cada linha sequencialmente
```
docker image build . -t fiap-booknrest:latest 
docker network create my-network 
docker run -d --name postgres --network my-network -e POSTGRES_DB=mydatabase -e POSTGRES_PASSWORD=secret -e POSTGRES_USER=myuser -p 5432:5432 postgres:latest
docker run -d --name booknrest --network my-network -p 8080:8080 -e DB_URL=jdbc:postgresql://postgres:5432/mydatabase fiap-booknrest:latest
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