import Orders.Orders;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка ")
    public void getOrderList() {
        Orders
                .getOrderList(1, "", 3, 1)
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
