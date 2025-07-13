import Courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private String login;
    private String password;
    private String firstName;

    @After
    public void after(){
//    удаление курьера
        Integer id =Courier.loginCourier(login,password).extract().body().path("id");
        if(id!=null){
            Courier.deleteCourier(String.valueOf(id));
        }
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Успешный логин курьера")
    public void loginCourier_success(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier
                .createCourier(login, password, firstName);

        Courier
                .loginCourier(login, password)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера без логина")
    @Description("Ошибка при логине без указания логина")
    public void loginCourier_noLogin() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier.createCourier(login, password, firstName);

        Courier.loginCourier("", password)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем")
    @Description("Ошибка при логине с неверным паролем")
    public void loginCourier_wrongPassword() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier.createCourier(login, password, firstName);

        Courier.loginCourier(login, "wrongPassword")
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }
}
