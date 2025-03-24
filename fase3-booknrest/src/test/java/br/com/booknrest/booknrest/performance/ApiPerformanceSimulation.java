package br.com.booknrest.booknrest.performance;

import br.com.booknrest.booknrest.util.RestauranteHelperFactory;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ApiPerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080/booknrest/v1")
            .header("Content-Type", "application/json");

    ActionBuilder cadastrarRestaurante = http("cadastrar restaurante")
            .post("/restaurantes")
            .body(StringBody(session -> RestauranteHelperFactory.getRestauranteDtoNomeAleatorioJson()))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("mensagemId"));

    ActionBuilder listarRestaurantes = http("listar restaurantes")
            .get("/restaurantes")
            .check(status().is(200));


    ScenarioBuilder cenarioCadastrarRestaurante = scenario("Cadastrar restaurante")
            .exec(cadastrarRestaurante);

    ScenarioBuilder cenarioListarRestaurantes = scenario("Listar restaurantes")
            .exec(listarRestaurantes);

    {
        setUp(
                cenarioCadastrarRestaurante.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioListarRestaurantes.injectOpen(
                        rampUsersPerSec(1)
                                .to(30)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(30)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(30)
                                .to(1)
                                .during(Duration.ofSeconds(10))))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(50),
                        global().failedRequests().count().is(0L));
    }
}
