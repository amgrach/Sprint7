package main;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierActions extends BaseClient {
    public static final String COURIER_URL = "api/v1/courier/";
    public static final String LOGIN_URL = "api/v1/courier/login/";

    @Step("Courier Creation")
    public static Response createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_URL);
    }

    @Step("Login Courier")
    public static Response loginCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_URL);
    }

    @Step("Delete Courier")
    public static void deleteCourier(String courierId) {
        if (courierId != null)
            given()
                    .spec(getSpec())
                    .delete(COURIER_URL + "{courierId}", courierId);
    }

}
