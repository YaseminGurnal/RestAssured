import Model.Location;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
public class _04_ApiTestPOJO {
    @Test
    public void extractJsonAllPOJO(){

        Location location=
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().body().as(Location.class) //Tüm body all location class kalıba göre çevir
        ;
        System.out.println("location.getCountry() = " + location.getCountry());

    }
}
