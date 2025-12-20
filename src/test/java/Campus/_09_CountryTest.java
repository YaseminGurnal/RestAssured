package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _09_CountryTest {
    RequestSpecification reqSpec;
    Faker randomUreteci = new Faker();
    String ulkeAdı = "";
    String ulkeKodu = "";
    String ulkeID = "";

    @BeforeClass
    public void Setup() {
        baseURI = "https://test.mersys.io";
        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().detailedCookies();

        ;
        System.out.println("cookies = " + cookies);
        reqSpec = (RequestSpecification) new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();

    }

    @Test
    public void CountryCreateTest() {
        ulkeAdı = randomUreteci.address().country() + randomUreteci.number().digits(5);
        ulkeKodu = randomUreteci.address().countryCode() + randomUreteci.number().digits(5);

        Map<String, String> createCountry = new HashMap<>();
        createCountry.put("name", ulkeAdı);
        createCountry.put("code", ulkeKodu);


        ulkeID =
                given()
                        .spec(reqSpec)
                        .body(createCountry)

                        .when()
                        .post("/school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")

        ;
    }

    @Test(dependsOnMethods = " CountryCreateTest")
    public void createCountryNegativeTest() {
        Map<String, String> createCountry = new HashMap<>();
        createCountry.put("name", ulkeAdı);
        createCountry.put("code", ulkeKodu);
        given()
                .spec(reqSpec)
                .body(createCountry)

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsStringIgnoringCase("already"))
        ;
    }

    @Test(dependsOnMethods = "createCountryNegativeTest")
    public void updateCountry() {
        Map<String, String> updateCountry = new HashMap<>();

        ulkeAdı = "ulke " + randomUreteci.number().digits(5);
        ulkeKodu = "1344ymn" + randomUreteci.number().digits(5);

        updateCountry.put("id", ulkeID);
        updateCountry.put("name", ulkeAdı);
        updateCountry.put("code", ulkeKodu);

        given()
                .spec(reqSpec)
                .body(updateCountry)

                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(ulkeAdı))
                .body("code", equalTo(ulkeKodu))
        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry() {
        given()
                .spec(reqSpec)
                .pathParam("ulkeID", ulkeID)
                .log().uri()

                .when()
                .delete("/school-service/api/countries/{ulkeID}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteCountry")
    public void deleteCountryNegative() {
        given()
                .spec(reqSpec)
                .pathParam("ulkeID", ulkeID)
                .log().uri()

                .when()
                .delete("/school-service/api/countries/{ulkeID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsStringIgnoringCase("Country not found"))
        ;
    }
}
