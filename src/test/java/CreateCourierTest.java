import Courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest {
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
    @DisplayName("Создание курьера")
    @Description("Успешное создание курьера")
    public void createCourier_success(){
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier
                .createCourier(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера без логина")
    public void createCourierNoLogin_unsuccess() {
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier
                .createCourier("", password, firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера без пароля")
    public void createCourierNoPassword_unsuccess() {
        login = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier
                .createCourier(login, "", firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание одинаковых курьеров")
    public void createCourierDublicate_unsuccess() {
        login = "login";
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        Courier
                .createCourier(login, password, firstName);
        Courier
                .createCourier(login, password, firstName)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }
}
