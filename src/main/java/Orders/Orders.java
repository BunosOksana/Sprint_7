package Orders;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Orders {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String ORDERS_URL_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public static ValidatableResponse createOrder(String firstName, String lastName, String address, String metroStation,
                                                  String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log()
                .all()
                .body("{\n" +
                        "    \"firstName\": \"" + firstName + "\",\n" +
                        "    \"lastName\": \"" + lastName + "\",\n" +
                        "    \"address\": \"" + address + "\",\n" +
                        "    \"metroStation\": \"" + metroStation + "\",\n" +
                        "    \"phone\": \"" + phone + "\",\n" +
                        "    \"rentTime\": \"" + rentTime + "\",\n" +
                        "    \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                        "    \"comment\": \"" + comment + "\",\n" +
                        "    \"color\": [\"" + String.join(",", color) + "\"]\n" +
                        "}")
                .when()
                .post(ORDERS_URL_PATH)
                .then()
                .log()
                .all();

    }

    @Step("Получение списка заказов")
    public static ValidatableResponse getOrderList(Integer courierId, String nearestStation, Integer limit, Integer page) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log()
                .all()
                .body("{\n" +
                        "    \"courierId\": \"" + courierId + "\",\n" +
                        "    \"nearestStation\": \"" + nearestStation + "\",\n" +
                        "    \"limit\": \"" + limit + "\",\n" +
                        "    \"page\": \"" + page + "\"\n" +
                        "}")
                .when()
                .get(ORDERS_URL_PATH)
                .then()
                .log()
                .all();
    }
}
