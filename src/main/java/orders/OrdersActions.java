package orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static config.AppConfig.*;
import static io.restassured.RestAssured.given;

public class OrdersActions {
    @Step("Create New Order")
    public static Response newOrder(Orders order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(APP_URL + ORDER_URL);
    }
    @Step("Cancel Order")
    public static void cancelOrder(String track) {
        given()
                .header("Content-type", "application/json")
                .queryParams("track", track)
                .when()
                .put(APP_URL + CANCEL_ORDER_URL);
    }

    @Step("Get List Of Orders")
    public static Response getListOfOrders() {
        return given()
                .header("Content-type", "application/json")
                .get(APP_URL + ORDER_URL);
    }

}
