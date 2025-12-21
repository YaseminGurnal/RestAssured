package Campus;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _10_CitizenShipTest extends CampusParent {
    String citizenShipID = "";
    String citizenShipName = "";

    @Test
    public void createCitizenShip() {

        Map<String, String> citizenShip = new HashMap<>();
        citizenShip.put("name", citizenShipName);
        citizenShip.put("shortName", "kısaAdı");

        citizenShipID =
                citizenShipName = randomUreteci.nation().nationality() + randomUreteci.number().digits(5);

        given()
                .spec(reqSpec)
                .body(citizenShip)

                .when()
                .post("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;
        System.out.println("citizenShipID = " + citizenShipID);
    }

    @Test(dependsOnMethods = "createCitizenShip")
    public void createCitizenShipNegative() {

        Map<String, String> citizenShip = new HashMap<>();
        citizenShip.put("name", citizenShipName);
        citizenShip.put("shortName", "kısaAdı");


        given()
                .spec(reqSpec)
                .body(citizenShip)

                .when()
                .post("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsStringIgnoringCase("already"))
        ;

    }

    @Test(dependsOnMethods = "createCitizenShipNegative")
    public void updateCitizenShip() {
        citizenShipName = "Yasemin" + randomUreteci.number().digits(5);

        Map<String, String> updtCitizen = new HashMap<>();
        updtCitizen.put("id", citizenShipID);
        updtCitizen.put("name", citizenShipName);
        updtCitizen.put("shortName", "kısaAdı");


        given()
                .spec(reqSpec)
                .body(updtCitizen)

                .when()
                .put("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(citizenShipName))
        ;

    }

    @Test(dependsOnMethods = "updateCitizenShip")
    public void deleteCitizenShip() {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/citizenships" + citizenShipID)

                .then()
                .log().body()
                .statusCode(200)

        ;
    }

    @Test(dependsOnMethods = "deleteCitizenShip")
    public void deleteCitizenShipNegative() {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/citizenships" + citizenShipID)

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))

        ;
    }
}
