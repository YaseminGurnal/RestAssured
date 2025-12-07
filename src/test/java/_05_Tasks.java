import Model.ToDo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _05_Tasks {
    //create a request to https://jsonplaceholder.typicode.com/todos/2
    //Converting Into POJO body data and write*/

    @Test
    public void Task1() {
        ToDo toDoNesne =

                given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .extract().body().as(ToDo.class);
        System.out.println("toDoNesne = " + toDoNesne);
    }
}
