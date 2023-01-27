package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.lombok.LombokUserData;
import test.model.UserData;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.Specs.request;


public class ReqresInTest {

    @Test
    @DisplayName("Check single user with spec")
    void checkIdSingleUser() {
        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpec)
                .body("data.id", is(2));

    }

    @Test
    @DisplayName("Check first name of single user with model")
    void checkFirstNameSingleUser() {
        UserData userData = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpec)
                .extract().as(UserData.class);

        assertEquals("Janet", userData.getData().getFirstName());
    }

    @Test
    @DisplayName("Check first name of single user with lombok")
    void singleUserWithLombok() {
        LombokUserData userData = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpec)
                .extract().as(LombokUserData.class);

        assertEquals("Janet", userData.getUser().getFirstName());
    }

    @Test
    @DisplayName("Check name using groovy")
    public void checkEmailUsingGroovy() {
        given()
                .spec(request)
                .when()
                .get("/unknown")
                .then()
                .body("data.findAll{it.id == 5}.name", hasItem("tigerlily"));
    }

}
