package br.com.booknrest.booknrest.infra.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.util.List;

import static br.com.booknrest.booknrest.infra.rest.RestauranteController.RESTAURANTE;
import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RestauranteControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class cadastroTests {
        @Test
        void cadastraRestaurante() {
            given()
                    .contentType(ContentType.JSON)
                    .body(RESTAURANTE)
            .when()
                    .post("/booknrest/v1/restaurantes")
            .then()
                    .statusCode(201);
        }

        @Test
        void dadosNulosNaoCadastra() {
            given()
                    .contentType(ContentType.JSON)
                    .body("{}")
            .when()
                    .post("/booknrest/v1/restaurantes")
            .then()
                    .statusCode(400)
                    .body("message", containsString("nome: não deve estar em branco"))
                    .body("message", containsString("localizacao: não deve estar em branco"))
                    .body("message", containsString("tipoCozinha: não deve estar em branco"))
                    .body("message", containsString("horariosDeFuncionamento: não deve ser nulo"))
                    .body("message", containsString("capacidade: deve ser maior que 0"))
            ;
        }

    }


    @Test
    void deveListarRestaurantes() {
        List<RestauranteDTO> list =
            given()
            .when()
                .get("/booknrest/v1/restaurantes")
            .then()
                .statusCode(200)
                .body("[0].horariosDeFuncionamento.diaDaSemana", contains("sabado"))
                .extract().body()
                .jsonPath().getList(".", RestauranteDTO.class);

        assertThat(list).hasSize(1);

        RestauranteDTO restaurante = list.getFirst();
        assertThat(restaurante.id()).isEqualTo(1);
        assertThat(restaurante.nome()).isEqualTo("O Melhor");

        var horarioDeFuncionamentoDTOS = restaurante.horariosDeFuncionamento();

        assertThat(horarioDeFuncionamentoDTOS)
                .isNotNull()
                .hasSize(1);
        var first = horarioDeFuncionamentoDTOS.getFirst();
        assertThat(first.diaDaSemana()).isEqualTo(DayOfWeek.SATURDAY);

    }
}