package courier;

import io.restassured.response.Response;

import static config.AppConfig.*;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class CourierActions {
    @Step("Courier Creation")
    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(APP_URL + COURIER_URL);
    }

    @Step("Login Courier")
    public static Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(APP_URL + LOGIN_URL);
    }
    @Step("Delete Courier")
    public static void deleteCourier(String courierId) {
        if (courierId != null)
            given()
                    .delete(APP_URL + COURIER_URL + "{courierId}", courierId);
    }

}
