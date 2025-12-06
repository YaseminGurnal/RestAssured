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

    public void BodyArrayHasSizeTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .body("places", hasSize(1)) //places in eleman uzunluğu 1 mi

        ;
    }

    public void combiningTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .body("places", hasSize(1)) //places in eleman uzunluğu 1 mi
                .body("places[0].state", equalTo("California"))
                .body("places.'place name'", hasItem("Beverly Hills"))
        //İstenilen kadar test eklenebilir.

        ;
    }

    public void pathParamTest() {

        given()
                .pathParam("ulke", "us")
                .pathParam("postaKodu", 90210)
                .log().uri() //oluşacak endpoint i yazdıralım

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKodu}")

                .then()
                .log().body()


        ;
    }

    public void queryParamTest() {

        given()
                .param("page", 3)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")//URL kısmı buraya yazıldı

                .then()
                .log().body()


        ;
    }

    public void queryParamTest2() {
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i <= 10; i++) {

            given()
                    .param("page", i)

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))

            ;
        }
    }

}



