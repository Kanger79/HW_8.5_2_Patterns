package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {

    }

    private static void sendRequest(SendReg user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPass() {
        String pass = faker.internet().password();
        return pass;
    }

    public static class Registration {
        private Registration() {
        }

        public static SendReg getUser(String status) {
            var user = new SendReg(getRandomLogin(), getRandomPass(), status);
            return user;
        }

        public static SendReg getRegUser(String status) {
            var regUser = getUser(status);
            sendRequest(regUser);
            return regUser;
        }
    }

    @Value
    public static class SendReg {
        String login;
        String password;
        String status;
    }

}
