package orders;

import io.restassured.response.Response;

import static config.AppConfig.*;
import static io.restassured.RestAssured.given;

public class OrdersActions {

    public static Response newOrder(Orders order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(APP_URL + ORDER_URL);
    }

    public static Response cancelOrder(String track) {
        given()
                .header("Content-type", "application/json")
                .queryParams("track", track)
                .when()
                .put(APP_URL + CANCEL_ORDER_URL);
    }

    public static Response getListOfOrders() {
        return given()
                .header("Content-type", "application/json")
                .get(APP_URL + ORDER_URL);
    }

}
