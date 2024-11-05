package br.com.vetvision.supervisor.interfaces.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static br.com.vetvision.supervisor.interfaces.controller.AtendimentoController.SOLICITACAO_EXEMPLO;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class AtendimentoControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void criaSolicitacaoTest() {
        given()
                .contentType(ContentType.JSON)
                .body(SOLICITACAO_EXEMPLO)
                .when().post("/solicitacao")
                .then()
                .statusCode(200);
    }

    @Test
    void criaSolicitacaoVaziaValidationTest() {
        given()
                .contentType(ContentType.JSON)
                .body("")
                .when().post("/solicitacao")
                .then()
                .statusCode(400);
//                .body(equalTo(""));
    }
}