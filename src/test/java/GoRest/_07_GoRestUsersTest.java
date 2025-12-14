package GoRest;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _07_GoRestUsersTest {
    RequestSpecification requestSpec;
    Faker randomUreteci = new Faker();
    int userID = 0;

    @BeforeClass
    public void Setup() {
        baseURI = " https://gorest.co.in/public/v2/";

        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer,892d5d20233be822447831b9314f7e6")
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void CreateUser() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "Male");
        newUser.put("email", rndEmail);
        newUser.put("status", "actıve");

        userID =
                given()
                        .spec(requestSpec)
                        .body(newUser)
                        .when()
                        .post("users") //Http ile başlamıyorsa baseUrı

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("userID = " + userID);
    }

    @Test(dependsOnMethods = "CreateUser")
    public void getUserByID() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .get("users/" + userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "getUserByID")
    public void updateUser() {   //Bu aşamadan sonra class çalıştırılmalı
        String updName = "yasemin gurnal";
        Map<String, String> updUser = new HashMap<>();
        updUser.put("name", updName);

        given()
                .spec(requestSpec)

                .when()
                .put("users/" + userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
                .body("name", equalTo(updName))
        ;
    }

    @Test(dependsOnMethods = "updateUser")
    public void deleteUser() {
        given()
                .spec(requestSpec)
                .when()
                .delete("users/" + userID)

                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative() {
        given()
                .spec(requestSpec)
                .when()
                .delete("users/" + userID)

                .then()
                .statusCode(404)
        ;
    }
}
