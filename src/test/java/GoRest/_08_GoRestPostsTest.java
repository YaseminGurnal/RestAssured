package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _08_GoRestPostsTest {
    //Gorest Posts kaynağında ki API leri test ediniz.
    RequestSpecification requestSpec;
    Faker randomUreteci = new Faker();
    int postID = 0;

    @BeforeClass
    public void Setup() {
        baseURI = " https://gorest.co.in/public/v2/";

        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer,892d5d20233be822447831b9314f7e6")
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void CreatePost() {
        String rndTitle = randomUreteci.lorem().sentence();
        String rndParagraph = randomUreteci.lorem().paragraph();

        Map<String, String> newUser = new HashMap<>();
        newUser.put("user_id", "8296802");
        newUser.put("title", rndTitle);
        newUser.put("body", rndParagraph);
        newUser.put("status", "actıve");

        postID =
                given()
                        .spec(requestSpec)
                        .body(newUser)
                        .when()
                        .post("posts") //Http ile başlamıyorsa baseUrı

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("postID = " + postID);
    }

    @Test(dependsOnMethods = "CreatePost")
    public void getPostByID() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .get("posts/" + postID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(postID))
        ;
    }

    @Test(dependsOnMethods = "getPostByID")
    public void updatePost() {   //Bu aşamadan sonra class çalıştırılmalı
        String updTitle = "yasemin gurnal Post Test";
        Map<String, String> updPost = new HashMap<>();
        updPost.put("title", updTitle);

        given()
                .spec(requestSpec)
                .body(updPost)

                .when()
                .put("posts/" + postID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(postID))
                .body("name", equalTo(updTitle))
        ;
    }

    @Test(dependsOnMethods = "updatePost")
    public void deletePost() {
        given()
                .spec(requestSpec)
                .when()
                .delete("posts/" + postID)

                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deletePost")
    public void deletePostNegative() {
        given()
                .spec(requestSpec)
                .when()
                .delete("posts/" + postID)

                .then()
                .statusCode(404)
        ;
    }
}
