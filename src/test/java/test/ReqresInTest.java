package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.model.RegisterUser;
import test.model.User;
import test.model.UserRequest;
import test.model.UserResponse;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.Specs.request;
import static test.Specs.responseSpec;


public class ReqresInTest {
    @Test
    @DisplayName("Получение списка пользователей")
    void userListWithLombok() {
        User data = given().spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .log().body()
                .extract().as(User.class);
    }

    @Test
    @DisplayName("Проверка наличия пользователя в базе")
    void singleUserNotFound() {
        given()
                .spec(request)
                .when()
                .get("/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Добавление пользователя")
    void createUserLombok() {
        UserRequest body = new UserRequest();
        body.setName("Dmitry");
        body.setJob("lawyer");

        UserResponse response = given().spec(request)
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract().as(UserResponse.class);

        assertEquals(body.getName(), response.getName());
        assertEquals(body.getJob(), response.getJob());
    }

    @Test
    @DisplayName("Обновление информации о пользователе")
    void updateUserLombok() {
        UserRequest body = new UserRequest();
        body.setName("Vasilii");
        body.setJob("programmer");

        UserResponse response = given().spec(request)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .extract().as(UserResponse.class);

        assertEquals(body.getName(), response.getName());
        assertEquals(body.getJob(), response.getJob());
    }

    @Test
    @DisplayName("Регистрация пользователя")
    void registerSuccessfulLombok() {
        RegisterUser body = new RegisterUser();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("pistol");

        UserResponse response = given().spec(request)
                .body(body)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .extract().as(UserResponse.class);

        assertEquals("4", response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    @DisplayName("Проверка id и email пользователя")
    void checkIdAndEmailOfFeaturedUser() {
        User userResponse = given().spec(request)
                .when()
                .pathParam("id", "2")
                .get("/users/{id}")
                .then()
                .spec(responseSpec)
                .extract().jsonPath().getObject("data", User.class);

        assertEquals(2, userResponse.getId());
        assertTrue(userResponse.getEmail().endsWith("@reqres.in"));
    }
}
