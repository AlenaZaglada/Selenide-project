import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.*;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCardTest {
    public String SetDateAfter() {

        return LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }


    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");

    }


    @Test
    public void shouldSendFormWithTrueInformation() {

        $(By.cssSelector("[data-test-id='city'] input")).setValue("Самара");
        $(By.cssSelector("[data-test-id='date'] input")).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id='date'] input")).setValue(SetDateAfter());
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Заглада Алена");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("+79608309609");
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.className("button")).click();
        $("[data-test-id = notification]").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + SetDateAfter()),
                Duration.ofSeconds(15)).shouldBe(visible);

    }


    @Test
    public void shouldSendFormWithIncorrectCity() {
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Париж");
        $(By.cssSelector("[data-test-id='date'] input")).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id='date'] input")).setValue(SetDateAfter());
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Заглада Алена");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("+79608309609");
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.className("button")).click();
        String expected = "Доставка в выбранный город недоступна";
        String actual = $(By.cssSelector("[data-test-id='city'].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWithIncorrectDate() {
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Самара");
        $(By.cssSelector("[data-test-id='date'] input")).doubleClick().sendKeys("22.05.2023");
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Заглада Алена");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("+79608309609");
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.className("button")).click();
        $x("//span[@data-test-id='date']//span[contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);

    }

    @Test
    public void shouldSendFormWithIncorrectName() {
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Самара");
        $(By.cssSelector("[data-test-id='date'] input")).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id='date'] input")).setValue(SetDateAfter());
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Piter Parker");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("+79608308609");
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);

    }

    @Test
    public void shouldSendFormWithIncorrectPhone() {

        $(By.cssSelector("[data-test-id='city'] input")).setValue("Самара");
        $(By.cssSelector("[data-test-id='date'] input")).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id='date'] input")).setValue(SetDateAfter());
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Заглада Алена");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("5986587");
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }


}








