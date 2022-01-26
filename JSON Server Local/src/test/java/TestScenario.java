import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestScenario {

    //TEST DATA
    private final String URLTESTED = "http://localhost:3000/";
    private String[] resourceNames = {"characters","weapons","spells"};
    //private List<String> apiResourcesStringList = new ArrayList<>();

    //JSON Parser
    private static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }

    @BeforeTest
    private void initTest() {

    }

    @Test(priority = 1)
    public void getUrl() {
        Response response = RestAssured.get(URLTESTED);
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(dependsOnMethods = "getUrl",priority = 2)
    public void listAllApiResources() {
        for (String resourceName : resourceNames) {
            Response response = doGetRequest(URLTESTED+resourceName);
            Assert.assertEquals(response.getStatusCode(),200);
        }
    }

    @Test(priority = 3)
    public void printAllApiResources() {
        System.out.println("Resources available:");
        int counter = 0;
        for (String resourceName : resourceNames) {
            counter++;
            System.out.println(counter + "." + resourceName);
        }
    }

    @Test(priority = 4)
    public void getNonExisting(){
        given().get(URLTESTED+"xyz/"+ 99999 ).then().statusCode(404);
    }

    @Test(priority = 5)
    public void getObjectById() {
        String iD = "1";
        System.out.println(URLTESTED+resourceNames[0]+"/"+iD);
        given().get(URLTESTED+resourceNames[0]+"/"+iD)
            .then().statusCode(200);
    }

    @Test(priority = 6)
    public void testGet(){
        Response response = RestAssured.get(URLTESTED+resourceNames[0]+"/");
        System.out.println("TEST LIBRARY: ");
        System.out.println("ALL USERS: - "+response.asString());
        System.out.println("RESPONSE STATUS CODE - "+response.getStatusCode());
        System.out.println("RESPONSE STATUS LINE - "+response.getStatusLine());
        System.out.println("RESPONSE HEADER - "+response.getHeader("content-type"));
        System.out.println("RESPONSE TIME - "+response.getTime());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority = 7)
    public void addNewObject() {
        baseURI = URLTESTED;
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id",4);
        jsonObject.put("name","John");
        jsonObject.put("surname","Rambo");
        jsonObject.put("profile","Killer");


        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(jsonObject.toJSONString())
        .then()
                .statusCode(201)
                .log().all();


    }

    @Test(priority = 8)
    public void checkUserByName() {
        String name = "Karol";
        given().get(URLTESTED+resourceNames[0]+"?"+name)
        .then()
                .statusCode(200)
                //.body("id",hasItem("1"))
                .body("surname",hasItems("Odwald"));
    }


    //@Test(priority = 9)
    public void deleteInResourceBy(String resourceName, String resourceData) {
    }



}
