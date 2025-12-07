import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

    public void extractingJsonPath4() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // datada ki bütün id leri nasıl alırız
        ArrayList<Integer> idler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().path("data.id");
        ;

        System.out.println("idler = " + idler);
    }

    public void extractingJsonPath5() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // datada ki bütün name leri nasıl alırız
        ArrayList<String> nameler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().path("data.name");
        ;

        System.out.println("nameler = " + nameler);
    }

    @Test
    public void extractingJsonPathResponseAll() {
        Response donenData =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .extract().response();

        ;
        List<Integer> idler = donenData.path("data.id");
        List<String> nameler = donenData.path("data.name");
        int limit = donenData.path("meta.pagination.limit");
        System.out.println("idler = " + idler);
        System.out.println("nameler = " + nameler);
        System.out.println("limit = " + limit);

        Assert.assertTrue(idler.contains(8229227));
        Assert.assertTrue(nameler.contains("Bhagwati Pilla"));
        Assert.assertTrue(limit == 10);
    }
}
