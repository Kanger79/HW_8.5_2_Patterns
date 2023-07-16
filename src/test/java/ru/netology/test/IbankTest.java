package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegUser;
import static ru.netology.data.DataGenerator.Registration.getUser;

public class IbankTest {

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Positive Test")
    void shouldComeInRegActiveUser() {
        var regUser = getRegUser("active");
        $("[data-test-id='login'] input").setValue(regUser.getLogin());
        $("[data-test-id='password'] input").setValue(regUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);

    }

    @Test
    void shouldNotComeInActiveNotRegUser() {
        var notRegUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldNotComeInBlockedRegUser() {
        var regUser = getRegUser("blocked");
        $("[data-test-id='login'] input").setValue(regUser.getLogin());
        $("[data-test-id='password'] input").setValue(regUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldNotComeInRegActiveUserInvalidLogin() {
        var regUser = getRegUser("active");
        $("[data-test-id='login'] input").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id='password'] input").setValue(regUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNotComeInRegActiveUserInvalidPass() {
        var regUser = getRegUser("active");
        $("[data-test-id='login'] input").setValue(regUser.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.getRandomPass());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

}
