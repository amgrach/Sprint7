import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import orders.OrdersActions;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {


    @Test
    @DisplayName("Список заказов")
    public void getOrder() {
        Response response = OrdersActions.getListOfOrders();
        response.then()
                .statusCode(200)
                .assertThat()
                .body("orders.id", notNullValue());
    }
}
