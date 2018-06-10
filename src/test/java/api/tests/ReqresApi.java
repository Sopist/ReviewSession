package api.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class ReqresApi {
	
	/*
	 * Send a get request to http://reqres.in/api/users
	 * Including query param -> page = 2
	 * Accept type is Json
	 * Verify status code is 200, verify response body
	 */
	@Test
	public void getUsersTest() {
		given().accept(ContentType.JSON)
		.and().param("page", 2)
		.when().get("http://reqres.in/api/users")
		.then().assertThat().statusCode(200);
		
		Response response = given().accept(ContentType.JSON)
							.and().param("page", 2)
							.when().get("http://reqres.in/api/users");
		
		System.out.println(response.getStatusLine());
		System.out.println(response.getContentType());
		System.out.println(response.headers());
		System.out.println(response.body().asString());
		
		//Add assertions for parts of response
		assertEquals(200, response.statusCode());
		assertTrue(response.contentType().contains("application/json"));
		
		// Keep header object
		Header header = new Header("X-Powered-By", "Express");
		assertTrue(response.headers().asList().contains(header));
		
		JsonPath json = response.jsonPath();
		assertEquals(12, json.getInt("total"));
		
		System.out.println(json.getInt("data.id[0]")); // list of data, need to specify index
		
		//Verify that Charles's id id 5
		assertEquals(5, json.getInt("data.find{it.first_name == 'Charles'}.id"));
		
		//Assert using JsonPath that person with id 6, first name is Tracey and last name is Ramos
		assertEquals("Tracey", json.getString("data.find{it.id == 6}.first_name"));
		assertEquals("Ramos", json.getString("data.find{it.id == 6}.last_name"));
		
		
	}

}
