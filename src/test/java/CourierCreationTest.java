import courier.Courier;
import courier.CourierActions;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class CourierCreationTest {
    private static final String login = "Courier_TEST1";
    private static final String password = "qwerty123";
    private static final String firstName = "Courier_TEST1";

    String courierId = null;

    @Test
    @DisplayName("Cоздание курьера")
    @Description("Cоздание курьера:\n " +
            "курьера можно создать;\n" +
            "чтобы создать курьера, нужно передать в ручку все обязательные поля;\n" +
            "запрос возвращает правильный код ответа;\n" +
            "успешный запрос возвращает ok: true;")
    public void createCourier() {
        Courier courier = new Courier(login, password, firstName);
        Response response = CourierActions.createCourier(courier);
        courierId = CourierActions.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Cоздание существующего курьера")
    @Description("Cоздание курьера:\n " +
            "нельзя создать двух одинаковых курьеров;\n" +
            "если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void createSameCourier() {
        Courier courier = new Courier(login, password, firstName);
        CourierActions.createCourier(courier);
        Response response = CourierActions.createCourier(courier);
        courierId = CourierActions.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Cоздание курьера без логина.")
    @Description("Cоздание курьера без логина:\n " +
            "если одного из полей нет, запрос возвращает ошибку;")
    public void createCourierWithoutLogin() {
        Courier courier = new Courier("", password, firstName);
        Response response = CourierActions.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Cоздание курьера без пароля.")
    @Description("Cоздание курьера без пароля:\n " +
            "если одного из полей нет, запрос возвращает ошибку;")
    public void createCourierWithoutPassword() {
        Courier courier = new Courier(login, "", firstName);
        Response response = CourierActions.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        CourierActions.deleteCourier(courierId);
    }


}
