package br.com.vetvision.supervisor.interfaces.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void listaPlanosTest() {
        given()
            .when()
            .get("/admin/planos")
            .then()
            .statusCode(200)
            .body("[0].cnpj", equalTo("17.048.004/0001-42"))
            .body("[0].nome", equalTo("Particular"));
    }
}
