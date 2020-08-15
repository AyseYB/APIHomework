package hw1;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class UINames {

    private final List<String> genders = Arrays.asList("male", "female");

    //    UI names API testing
//    In this assignment, you will test api uinames. This is a free api used to create test users. Documentation
//    for this api is available at https://github.com/CybertekSchool/uinames. You can import Postman
//    collection for this API using link: https://www.getpostman.com/collections/c878c63023e6d788da7d
//    Automate the given test cases. You can use any existing project. You can automate all test cases in
//    same class or different classes.


    @BeforeAll
    public static void beforeAll(){
        baseURI = "https://cybertek-ui-names.herokuapp.com/api/";
    }

    //    TEST CASES
//    No params test
//1. Send a get request without providing any parameters
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that name, surname, gender, region fields have value

    @Test
    @DisplayName("No param test")
    public void noParams(){
        Response response = when().get().prettyPeek();

        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");

    }

    //    Gender test
//1. Create a request by providing query parameter: gender, male or female
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that value of gender field is same from step 1



    @Test
    @DisplayName("Gender test")
    public void genderTest() {
        Collections.shuffle(genders);//to change order of our list
        String gender = genders.get(0);//because of shuffling our index can change and we can pick random gender
        System.out.println("Sending gender as = " + gender);
        Response response =
                given().
                        queryParams("gender",gender).
                        when().
                        get().prettyPeek();

        response.then().
                assertThat().
                statusCode(200).
                and().
                contentType("application/json; charset=utf-8").
                and().
               body("gender", is(gender));

    }

        //     2 params test
    //1. Create a request by providing query parameters: a valid region and gender
    //    NOTE: Available region values are given in the documentation
    //2. Verify status code 200, content type application/json; charset=utf-8
    //3. Verify that value of gender field is same from step 1
    //4. Verify that value of region field is same from step 1

    @Test
    @DisplayName("2 param test")
    public void twoParamTest(){
        Response response =
                given().
                        queryParam("region", "Belgium").
                        queryParam("gender", "female").
                        when().
                        get().prettyPeek();

        response.then().
                assertThat().
                     statusCode(200).
                and().
                     contentType("application/json; charset=utf-8").
                and().
                     body("gender", is("female")).
                     body("region", is("Belgium"));

    }

    //    Invalid gender test
//1. Create a request by providing query parameter: invalid gender
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Invalid gender
    @Test
    @DisplayName("Invalid gender test")
    public void invalidGender(){
        Response response =
                given().
                           queryParam("gender", "dog").
                        when().
                        get().prettyPeek();

        response.then().assertThat().statusCode(400).
                and().
                statusLine(containsString("Bad Request")).
                and().
                body("error", is("Invalid gender"));

    }

    //    Invalid region test
//1. Create a request by providing query parameter: invalid region
//2. Verify status code 400 and status line contains Bad Request
//3. Verify that value of error field is Region or language not found
    @Test
    @DisplayName("invalid region")
    public void invalidRegion(){
        Response response =
                given().
                        queryParam("region", "invalid region").
                        and().
                        get().prettyPeek();
        response.
                then().
                        assertThat().statusCode(400).
                and().
                        statusLine(containsString("Bad Request")).
                and().
                        body("error", is("Region or language not found"));
    }

    //    Amount and regions test
//1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects have different name+surname combination

    @Test
    @DisplayName("Amount and regions test")
    public void amountAndRegionTest(){
        Response response =
                given().
                        queryParam("region", "Argentina").
                queryParam("amount", 2).
                        when().
                get().prettyPeek();

        List<UserPOJO> userPOJOList = response.jsonPath().getList("", UserPOJO.class);
        System.out.println("userPOJOList = " + userPOJOList);
        Set<String > fullNames = new HashSet<>();

        for (UserPOJO userPOJO : userPOJOList){
            String fullName = userPOJO.getName() + " " + userPOJO.getSurname();
            fullNames.add(fullName);
        }

          response.then().
                  assertThat().
                        statusCode(200).
                  and().
                        header("content-Type", "application/json; charset=utf-8").
                  and().
                        body("size()", is(fullNames.size()));
    }


    //      3 params test
//1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger
//            than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that all objects the response have the same region and gender passed in step 1
    @Test
    @DisplayName("3 params test")
    public void threeParam(){
        Response response = given().
                queryParam("region", "France").
                queryParam("gender", "male").
                queryParam("amount", 20).
                when().
                get().prettyPeek();

        response.then().
                statusCode(200).
                and().
                contentType("application/json; charset=utf-8").
                and().
                body("region", everyItem(is("France"))).
                body("gender", everyItem(is("male"))).
                body("size()",is(20));

    }

    //    Amount count test
//1. Create a request by providing query parameter: amount (must be bigger than 1)
//2. Verify status code 200, content type application/json; charset=utf-8
//3. Verify that number of objects returned in the response is same as the amount passed in step 1
    @Test
    @DisplayName("Amount count test")
    public void amountCountTest(){

        Response response = given().
                queryParams("amount", 15).
                when().
                get().prettyPeek();

        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8").
                and().
                body("size()", is(15));
    }


}
