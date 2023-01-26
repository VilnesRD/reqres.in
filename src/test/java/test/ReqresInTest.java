package test;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;


public class ReqresInTest {

    @Test
    void checkIdSingleUser() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .body("data.id", is(2));

    }

    @Test
    void checkFirstNameSingleUser() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .body("data.first_name", is("Janet"));
    }

    @Test
    void singleUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void createUser() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void updateUser() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        given()
                .contentType(JSON)
                .body(body)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void registerSuccessful() {
        String body = "{\n" +
                "    \"email\":  \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n}";
        given()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerUnSuccessful() {
        String body = "{\n" +
                "    \"email\":  \"eve.holt@reqres.in\"\n}";
        given()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
