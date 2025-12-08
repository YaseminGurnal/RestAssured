import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class _06_PathAndJsonPath {
    @Test
    public void extractingJsonPath() {
        int postCode =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post Code'");
        System.out.println("postCode = " + postCode);
        //Tip dönüşümü otomatik uygun tip verilmeli
    }

    @Test
    public void extractingJsonPathIcNesne() {
        Response donenData =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().response() //Tüm sonucu body i aldık
                ;
        Location location = donenData.as(Location.class);//Bütün class yapısını yazmak zorundayız
        System.out.println("location = " + location);
        //Bana sadece place dizisi lazım olsa bile bütün dğer classları yazmak zorundayım

        //Eğer içerdeki bir nesne tipli parçayı almak isteseydim
        List<Place> places = donenData.jsonPath().getList("places", Place.class);
        System.out.println("places = " + places);

    }
}
