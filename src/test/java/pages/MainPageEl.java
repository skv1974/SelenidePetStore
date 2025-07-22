package pages;

import lombok.Data;

@Data
public class MainPageEl {

    //Локаторы элементов на странице https://petstore.swagger.io (можно вынести в отдельный класс)
    public static final String USER_SPAN = "//section[@class = 'block col-12 block-desktop col-12-desktop']//span[3]//button[@class = 'expand-operation']";

    public static final String GET_USER_BY_NAME_ACC = ".//div[@id = 'operations-user-getUserByName']"; //аккордеон GetUser
    public static final String POST_CREATE_USER = ".//div[@id = 'operations-user-createUser']";

    public static final String TRY_IT_OUT_BUTTON = ".//button[@class = 'btn try-out__btn']"; //кнопка Try it Out
    public static final String EXECUTE_BUTTON = ".//button[text() = 'Execute']"; //кнопка Execute
    public static final String USERNAME_FIELD = ".//tr[@data-param-name = 'username']//input";
    public static final String TEXT_AREA = ".//table[@class = 'parameters']//textarea[@class = 'body-param__text']";
    public static final String CODE_FIELD = ".//tr[@class = 'response']/td[@class = 'response-col_status']";
    public static final String COPY_RESPONSE_BODY_BUTTON = ".//tr[@class = 'response']//div[@class = 'copy-to-clipboard']";
    public static final String DOWNLOAD_RESPONSE_BUTTON = ".//tr[@class = 'response']//button[@class = 'download-contents']";

}
