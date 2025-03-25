package br.com.booknrest.booknrest.infra.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.booknrest.booknrest.infra.rest.ReservaController.RESERVA;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ReservaControllerIT {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class criaReservas {
        @Test
        void criaReserva() {
            given()
                    .contentType(ContentType.JSON)
                    .body(new ReservaDTO(null, 1L, new ReservaDTO.ClienteDto(null, "Ciclano", "10 99999-9999"), LocalDateTime.of(2027, 3, 13, 20, 0), 10, null))
            .when()
                    .post("/booknrest/v1/reservas")
            .then()
                    .statusCode(201)
            .body(matchesJsonSchemaInClasspath("schemas/Reserva.schema.json"));
        }

        @Test
        void naoCriaReservaRestaurateFechado() {
            given()
                    .contentType(ContentType.JSON)
                    .body(RESERVA)
                    .when()
                    .post("/booknrest/v1/reservas")
                    .then()
                    .statusCode(400)
                    .body("message", containsString("O restaurante não está aberto neste horário, 20:00 de segunda-feira"));
        }

        @Test
        void dadosNulosNaoCadastra() {
            given()
                    .contentType(ContentType.JSON)
                    .body("{}")
            .when()
                    .post("/booknrest/v1/reservas")
            .then()
                    .statusCode(400)
                    .body("message", containsString("restauranteId: deve ser maior que 0"))
                    .body("message", containsString("cliente: não deve ser nulo"))
                    .body("message", containsString("quantidadePessoas: deve ser maior que 0"))
            ;
        }
    }

    @Test
    void deveListarReservas() {
        List<ReservaDTO> list =
            given()
            .when()
                .get("/booknrest/v1/reservas")
            .then()
                .statusCode(200)
                .extract().body()
                .jsonPath().getList(".", ReservaDTO.class);

        assertThat(list).hasSize(1);
    }
}