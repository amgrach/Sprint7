import courier.Courier;
import courier.CourierActions;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CourierLoginTest {
    private static final String login = "Courier_TEST2";
    private static final String password = "qwerty123";
    private static final String firstName = "Courier_TEST2";
    private static final String loginNotValid = "Courier_TESTNOTVALID";
    private static final String passwordNotValid = "qwerty321";
    String courierId = null;

    @Before
    public void createCourier() {
        Courier courierNew = new Courier(login, password, firstName);
        CourierActions.createCourier(courierNew);
        courierId = CourierActions.loginCourier(courierNew).then().extract().path("id").toString();
    }


    @Test
    // Логин курьера:
    // курьер может авторизоваться;
    // для авторизации нужно передать все обязательные поля;
    // успешный запрос возвращает id.
    public void loginCourier() {
        Courier courier = new Courier(login, password);
        Response response = CourierActions.loginCourier(courier);
        response.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());

    }

    @Test
    // Логин курьера:
    // система вернёт ошибку, если неправильно указать логин или пароль;
    // если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    public void loginCourierLoginNotValid() {
        Courier courier = new Courier(loginNotValid, password);
        Response response = CourierActions.loginCourier(courier);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    // Логин курьера:
    // система вернёт ошибку, если неправильно указать логин или пароль;
    public void loginCourierPasswordNotValid() {
        Courier courier = new Courier(login, passwordNotValid);
        Response response = CourierActions.loginCourier(courier);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    // Логин курьера:
    // для авторизации нужно передать все обязательные поля;
    public void loginCourierWithoutLogin() {
        Courier courier = new Courier("", password);
        Response response = CourierActions.loginCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    // Логин курьера:
    // для авторизации нужно передать все обязательные поля;
    public void loginCourierWithoutPassword() {
        Courier courier = new Courier(login, "");
        Response response = CourierActions.loginCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @After
    public void deleteCourier() {
        CourierActions.deleteCourier(courierId);
    }
}
