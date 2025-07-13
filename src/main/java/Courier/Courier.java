package Courier;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class Courier {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String CREATE_COURIER_URL_PATH = "/api/v1/courier";
    private static final String LOGIN_COURIER_URL_PATH = "/api/v1/courier/login";
    private static final RequestSpecification REQUEST_SPECIFICATION_TEMPLATE =
            new RequestSpecBuilder().setBaseUri(BASE_URL).addHeader("Content-Type", "application/json").log(LogDetail.ALL).build();
    private static final ResponseSpecification RESPONSE_SPECIFICATION_TEMLATE =
            new ResponseSpecBuilder().log(LogDetail.ALL).build();
    private int id;

    @Step("Создание курьера")
    public static ValidatableResponse createCourier(String login, String password, String firstName) {
        return given()
                .spec(REQUEST_SPECIFICATION_TEMPLATE)
                .body("{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\",\n" +
                        "    \"firstName\": \"" + firstName + "\"\n" +
                        "}")
                .post(CREATE_COURIER_URL_PATH)
                .then()
                .spec(RESPONSE_SPECIFICATION_TEMLATE);
    }

    @Step("Логинимся курьером")
    public static ValidatableResponse loginCourier(String login, String password) {
        return given()
                .spec(REQUEST_SPECIFICATION_TEMPLATE)
                .body("{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        "}")
                .post(LOGIN_COURIER_URL_PATH)
                .then()
                .spec(RESPONSE_SPECIFICATION_TEMLATE);
    }

    @Step("Удаление курьера")
    public static ValidatableResponse deleteCourier(String courierId) {
        return given()
                .spec(REQUEST_SPECIFICATION_TEMPLATE)
                .delete("/api/v1/courier/" + courierId)
                .then()
                .spec(RESPONSE_SPECIFICATION_TEMLATE);
    }
}
