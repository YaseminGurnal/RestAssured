import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _03_ApiTestExtract {
    @Test
    public void extractingJsonPath() {
        String ulkeAdı =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("country") //Path i country olan değeri extract yap

                ;
        System.out.println("ulkeAdı = " + ulkeAdı);
        Assert.assertEquals(ulkeAdı, "United States");
    }

    public void extractingJsonPath2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız
        String eyaletAdı =

                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("places[0].state");
        System.out.println("eyaletAdı = " + eyaletAdı);
        Assert.assertEquals(eyaletAdı, "California");
    }

    public void extractingJsonPath3() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.
        int limit =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().path("meta.pagination.limit");
        System.out.println("limit = " + limit);
        Assert.assertTrue(limit == 10);
    }

}
