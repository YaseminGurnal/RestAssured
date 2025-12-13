package GoRest;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _07_GoRestUsersTest {
    RequestSpecification requestSpec;
    Faker randomUreteci = new Faker();

    @BeforeClass
    public void Setup() {
        baseURI = " https://gorest.co.in/public/v2/";

        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer,892d5d20233be822447831b9314f7e6")
                .setContentType(ContentType.JSON)
                .build();

    }

    public void CreateUser() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "Male");
        newUser.put("email", rndEmail);
        newUser.put("status", "actıve");

        given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post("users") //Http ile başlamıyorsa baseUrı
                .then()
        ;
    }

}
