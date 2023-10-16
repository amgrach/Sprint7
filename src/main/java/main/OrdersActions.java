package main;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersActions extends BaseClient {
    public static final String ORDER_URL = "api/v1/orders/";
    public static final String CANCEL_ORDER_URL = "api/v1/orders/cancel/";

    @Step("Create New Order")
    public static Response newOrder(Orders order) {
        return given()
                .spec(getSpec())
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_URL);
    }

    @Step("Cancel Order")
    public static void cancelOrder(String track) {
        given()
                .spec(getSpec())
                .header("Content-type", "application/json")
                .queryParams("track", track)
                .when()
                .put(CANCEL_ORDER_URL);
    }

    @Step("Get List Of Orders")
    public static Response getListOfOrders() {
        return given()
                .spec(getSpec())
                .header("Content-type", "application/json")
                .get(ORDER_URL);
    }

}
