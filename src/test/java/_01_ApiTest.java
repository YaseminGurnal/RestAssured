import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _01_ApiTest {
    @Test
    public void Test1() {

        given().

                when().


                then()
        ;
    }

    public void statusCodeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210 ")

                .then()
                .log().body() //Dönen data kısmı
                //.log().all() //Dönen bütün bilgiler
                .statusCode(200) //Dönen değer 200 e eşit mi Assert


        ;
    }

    public void contentTypeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210 ")

                .then()
                .log().body() //Dönen data kısmı
                //.log().all() //Dönen bütün bilgiler
                .statusCode(200) //Dönen değer 200 e eşit mi Assert
                .contentType(ContentType.JSON)  //Dönen datanın tipi JSON mı?


        ;
    }

    public void checkCountryInResponseBodyTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210 ")

                .then()
                .log().body() //Dönen data kısmı
                //.log().all() //Dönen bütün bilgiler
                .statusCode(200) //Dönen değer 200 e eşit mi Assert
                .contentType(ContentType.JSON)  //Dönen datanın tipi JSON mı?
                .body("country", equalTo("United States")) //Country dışarı almadan
        //bulundu yeri (path i) vererek içerde assertion hamcrest kütüphanesi yapıyor


        ;
    }

    public void checkCountryInResponseBodyTest2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu doğrulayınız

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
        ;
    }

    public void checkHasItem() {
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))
        ;
    }
}



