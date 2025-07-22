import com.codeborne.selenide.Configuration;
import helpers.ConfigReader;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import helpers.MainPageHelper;

import static pages.MainPageEl.*;

/*
    Пример тестов интерфейса PetStore при помощи Selenide
    Проперти для открытия браузера загружаются из config.properties
    В проекте применяется принцип PageObject - элементы разных страниц описаны в отдельных классах, действия с каждым элементом определяются отдельными методами.

    Проверяется три сценария:
    1. Поиск несуществующего Пользователя по имени GetUser (проверяется возможность пройти сценарий до конца с проверкой кода и тела ответа на запрос)
    2. Создание Пользователя PostUser (проверяется возможность пройти сценарий до конца с проверкой кода и тела ответа на запрос)
    3. Поиск ранее созданного Пользователя по имени GetUser (проверяется возможность пройти сценарий до конца с проверкой кода и тела ответа на запрос)

    Тестовые данные для простоты инициализируются непосредственно в классе.

    Сценарии 2 и 3 связаны зависимостью dependsOnMethods, чтобы всегда сначада пересоздавался Пользователь, а потом проверялось его существование
    Порядок выполнения сценариев в случае увеличения цепочки зависимостью можно реализовать через TestNG.xml указав порядок классов и методов в цепочках,
    в данном случае это не требуется.

*/

public class PetStoreSwaggerUiTests {

    ConfigReader configReader = new ConfigReader();
    MainPageHelper mainPageHelper = new MainPageHelper();

    String userName200;
    String userName404;
    String etalonResponseBodyGet200;
    String etalonResponceBodyGet404;
    String etalonResponceBodyPost;

    @BeforeSuite
    public void sutUpSuit() throws InterruptedException {

        //загрузка пропертей браузера
        Configuration.browser = configReader.getBrowser();
        Configuration.browserSize = configReader.getResolution();

        //инициализация тестовых данных
        userName200 = "skv1974";

        etalonResponseBodyGet200 = "{\n" +
                "  \"id\": 1974,\n" +
                "  \"username\": \"skv1974\",\n" +
                "  \"firstName\": \"skv\",\n" +
                "  \"lastName\": \"1974\",\n" +
                "  \"email\": \"email@email.com\",\n" +
                "  \"password\": \"Qwe123\",\n" +
                "  \"phone\": \"+7 000 121-45-67\",\n" +
                "  \"userStatus\": 4791\n" +
                "}";

        userName404 = "not_skv1974";

        etalonResponceBodyGet404 = "{\n" +
                "  \"code\": 1,\n" +
                "  \"type\": \"error\",\n" +
                "  \"message\": \"User not found\"\n" +
                "}";

        etalonResponceBodyPost = "{\n" +
                "  \"code\": 200,\n" +
                "  \"type\": \"unknown\",\n" +
                "  \"message\": \"1974\"\n" +
                "}";

        //открытие главной страницы
        mainPageHelper.open();
        Thread.sleep(3000);

    }


    @Story("Проверка запроса GetUser, поиск несуществующего пользователя, код 404")
    @Test (enabled = true)
    public void GetUserNotFoundUserTest() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();

        //открытие аккордиона GetUserName если не открыто и нажатие на кнопку Try it Out
        if (!mainPageHelper.checkAccordionOpen(GET_USER_BY_NAME_ACC)) {
        mainPageHelper.accordionClick(GET_USER_BY_NAME_ACC);
        mainPageHelper.accordionButtonsClick(GET_USER_BY_NAME_ACC,TRY_IT_OUT_BUTTON);
        }

        //заполнение поля userName
        mainPageHelper.sendTextToField(GET_USER_BY_NAME_ACC,USERNAME_FIELD,userName404);

        //нажатие кнопки Execute + небольшое гарантированное ожидание ответа
        mainPageHelper.accordionButtonsClick(GET_USER_BY_NAME_ACC,EXECUTE_BUTTON);
        Thread.sleep(3000);

        //получение кода ответа (200)
        String code = mainPageHelper.getCodeResponse(GET_USER_BY_NAME_ACC);

        //нажатие на кнопку Скопировать ответ
        mainPageHelper.accordionButtonsClick(GET_USER_BY_NAME_ACC,COPY_RESPONSE_BODY_BUTTON);

        //Получение тела ответа из буфера обмена
        String body = mainPageHelper.getClipboardValue();

        //проверка кода и тела ответа
        softAssert.assertEquals(code,"404","Проверка кода ответа");
        softAssert.assertEquals(body, etalonResponceBodyGet404,"Проверка тела ответа");

        softAssert.assertAll();

    }

    @Story("Создание Пользователя запросом PostUser")
    @Test(enabled = true)
    public void CreateUserTest() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();

        //открытие аккордиона GetUserName если не открыто и нажатие на кнопку Try it Out
        if (!mainPageHelper.checkAccordionOpen(POST_CREATE_USER)) {
            mainPageHelper.accordionClick(POST_CREATE_USER);
            mainPageHelper.accordionButtonsClick(POST_CREATE_USER, TRY_IT_OUT_BUTTON);
        }

        //заполнение поля userName
        mainPageHelper.sendTextToField(POST_CREATE_USER, TEXT_AREA, etalonResponseBodyGet200);

        //нажатие кнопки Execute + небольшое гарантированное ожидание ответа
        mainPageHelper.accordionButtonsClick(POST_CREATE_USER, EXECUTE_BUTTON);
        Thread.sleep(3000);

        //получение кода ответа (200)
        String code = mainPageHelper.getCodeResponse(POST_CREATE_USER);

        //нажатие на кнопку Скопировать ответ
        mainPageHelper.accordionButtonsClick(POST_CREATE_USER,COPY_RESPONSE_BODY_BUTTON);

        //Получение тела ответа из буфера обмена
        String body = mainPageHelper.getClipboardValue();

        //проверка кода и тела ответа
        softAssert.assertEquals(code,"200","Проверка кода ответа");
        softAssert.assertEquals(body, etalonResponceBodyPost,"Проверка тела ответа");

        softAssert.assertAll();
    }

    @Story("Проверка запроса GetUser, пользователь существует, код 200")
    @Test(enabled = true, dependsOnMethods = "CreateUserTest")
    public void GetUser200() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();

        //открытие аккордиона GetUserName если не открыто и нажатие на кнопку Try it Out
        if (!mainPageHelper.checkAccordionOpen(GET_USER_BY_NAME_ACC)) {
            mainPageHelper.accordionClick(GET_USER_BY_NAME_ACC);
            mainPageHelper.accordionButtonsClick(GET_USER_BY_NAME_ACC, TRY_IT_OUT_BUTTON);
        }

        //заполнение поля userName
        mainPageHelper.sendTextToField(GET_USER_BY_NAME_ACC,USERNAME_FIELD, userName200);

        //нажатие кнопки Execute + небольшое гарантированное ожидание ответа
        mainPageHelper.accordionButtonsClick(GET_USER_BY_NAME_ACC,EXECUTE_BUTTON);
        Thread.sleep(3000);

        //получение кода ответа (200)
        String code = mainPageHelper.getCodeResponse(GET_USER_BY_NAME_ACC);

        //нажатие на кнопку Скопировать ответ
        mainPageHelper.accordionButtonsClick(GET_USER_BY_NAME_ACC,COPY_RESPONSE_BODY_BUTTON);

        //Получение тела ответа из буфера обмена
        String body = mainPageHelper.getClipboardValue();

        //проверка кода и тела ответа
        softAssert.assertEquals(code,"200","Проверка кода ответа");
        softAssert.assertEquals(body, etalonResponseBodyGet200,"Проверка тела ответа");

        softAssert.assertAll();

    }
}
