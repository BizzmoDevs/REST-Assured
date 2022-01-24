import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class TestScenario {

    //TEST DATA
    private final String URLTESTED = "http://localhost:3000/";



    @Test
    private void getUrl() {
        Response response = RestAssured.get(URLTESTED);
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test (dependsOnMethods = "getUrl")
    public void getAllUsers() {
        Response response = RestAssured.get("http://localhost:3000/users");
        Assert.assertEquals(response.getStatusCode(),200);
        response.getBody().prettyPrint();
    }

    @Test
    public void getUserById() {
        given().get("http://localhost:3000/users/1").then().statusCode(200);
    }

    @Test
    public void getNonExistUser(){
        //TODO count all users
        given().get("http://localhost:3000/users/" + 99999 ).then().statusCode(404);
    }

    @Test
    public void testGet(){
        Response response = RestAssured.get("http://localhost:3000/users");
        System.out.println("TEST LIBRARY: ");
        System.out.println("ALL USERS: - "+response.asString());
        System.out.println("RESPONSE STATUS CODE - "+response.getStatusCode());
        System.out.println("RESPONSE STATUS LINE - "+response.getStatusLine());
        System.out.println("RESPONSE HEADER - "+response.getHeader("content-type"));
        System.out.println("RESPONSE TIME - "+response.getTime());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test
    public void addNewUser() {
        //Response response = RestAssured.post("http://localhost:3000/users?name=John&surname=Rambo");
        //int statusCode = response.getStatusCode();
        //Assert.assertEquals(statusCode,201);
    }

    @Test
    public void checkUserByName() {
        String name = "Karol";
        given()
                .get("http://localhost:3000/users?name="+name)
        .then()
                .statusCode(200);
                //.body("profile",equalTo("Master"));
    }

    @Test
    public void tT() {
        
    }

}
