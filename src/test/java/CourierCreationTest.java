import courier.Courier;
import courier.CourierActions;
import org.junit.Test;
import org.junit.After;
import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreationTest {
    private static final String login = "Courier_TEST1";
    private static final String password = "qwerty123";
    private static final String firstName = "Courier_TEST1";

    String courierId = null;
    @Test
    // Cоздание курьера:
    // курьера можно создать;
    // чтобы создать курьера, нужно передать в ручку все обязательные поля;
    // запрос возвращает правильный код ответа;
    // успешный запрос возвращает ok: true;
    public void createCourier() {
        Courier courier = new Courier(login, password, firstName);
        Response response = CourierActions.createCourier(courier);
        courierId = CourierActions.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));

    }

    @Test
    //Cоздание курьера:
    //нельзя создать двух одинаковых курьеров;
    //если создать пользователя с логином, который уже есть, возвращается ошибка.
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
    //Cоздание курьера:
    //если одного из полей нет, запрос возвращает ошибку;
    public void createCourierWithoutLogin() {
        Courier courier = new Courier("", password, firstName);
        Response response = CourierActions.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    //Cоздание курьера:
    //если одного из полей нет, запрос возвращает ошибку;
    public void createCourierWithoutPassword() {
        Courier courier = new Courier( login, "", firstName);
        Response response = CourierActions.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void delCourier() {
        CourierActions.deleteCourier(courierId);
    }


}
