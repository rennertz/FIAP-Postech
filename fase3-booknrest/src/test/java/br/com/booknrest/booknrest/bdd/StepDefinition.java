package br.com.booknrest.booknrest.bdd;

import br.com.booknrest.booknrest.infra.rest.RestauranteDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static br.com.booknrest.booknrest.util.RestauranteHelperFactory.getRestauranteDtoNomeAleatorio;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class StepDefinition {
    private static final String ENDPOINT_API = "http://localhost:8080/booknrest/v1";

    private Response response;

    @Quando("cadastrar novo restaurante")
    public void cadastrar_novo_restaurante() {
        RestauranteDTO restauranteDtoNomeAleatorio = getRestauranteDtoNomeAleatorio();
        response =
            given()
                .contentType(ContentType.JSON)
                .body(restauranteDtoNomeAleatorio)
            .when()
                .post(ENDPOINT_API+"/restaurantes");
    }

    @Entao("deve responder que foi criado corretamente")
    public void deve_responder_que_foi_criado_corretamente() {
            response.then()
                .statusCode(201);
    }

    @Dado("que existe ao menos um restaurante cadastrado")
    public void queExisteAoMenosUmRestauranteCadastrado() {
        cadastrar_novo_restaurante();
    }

    @Quando("o usuario listar os restaurantes")
    public void oUsuarioListarOsRestaurantes() {
        response = given()
            .when().get(ENDPOINT_API+"/restaurantes");
    }

    @Entao("deve retornar a lista com os restaurantes existentes")
    public void deveRetornarAListaComOsRestaurantesExistentes() {
        List<RestauranteDTO> list = response.then()
                .statusCode(200)
                .extract().body()
                .jsonPath().getList(".", RestauranteDTO.class);

        assertThat(list).isNotEmpty();
    }
}
