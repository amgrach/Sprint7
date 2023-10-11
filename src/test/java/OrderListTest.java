import orders.*;
import org.junit.Test;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {


        @Test

        public void getOrder() {
            Response response = OrdersActions.getListOfOrders();
            response.then()
                    .statusCode(200)
                    .assertThat()
                    .body("orders.id",notNullValue());
        }
}
