
import io.restassured.response.Response;
import orders.Orders;
import orders.OrdersActions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;
@RunWith(Parameterized.class)
public class OrderCreationTest {

    private final String colour;
    String track = null;

    public OrderCreationTest(String colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters(name = "Colour for test is: {0}")
    public static Object[] getData() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK GREY"},
                {""}
        };
    }

    @Test
    @DisplayName("Создание заказов с разными цветами")
    public void testColour() {
        Orders order = new Orders("USER_TEST", "USER_TEST", "ADDRESS_TEST", "1", "+79998887766", 2, "2023-12-12", "test_comment", new String[]{colour});
        Response response = OrdersActions.newOrder(order);
        track = response.then().extract().path("track").toString();
        response.then()
                .assertThat().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }


    @After
    public void cancelOrder() {
        OrdersActions.cancelOrder(track);
    }
}
