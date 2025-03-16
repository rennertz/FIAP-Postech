package br.com.booknrest.booknrest.infra.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static br.com.booknrest.booknrest.infra.rest.RestauranteController.RESTAURANTE;
import static io.restassured.RestAssured.given;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RestauranteControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void cadastraRestaurante() {
        given()
            .contentType(ContentType.JSON)
            .body(RESTAURANTE)
            .when().post("/booknrest/v1/restaurantes")
            .then()
            .statusCode(201);
    }
}