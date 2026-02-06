package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojoClasses.PojoFirstForTestCase_OneAndThree;
import com.pojoClasses.PojoSecondForTestCase_Four;
import com.pojoClasses.PojoThirdForTestCase_FiveAndEight;

import io.restassured.response.Response;
import listeners.TestListenerRest;

@Listeners(listeners.TestListenerRest.class)
public class TelecomProject {

	String DynamicMailId;
	String token;
	String id;

	@Test(priority = 1)
	public void testCase_01_AddNewUser_POST() throws JsonProcessingException {

		PojoFirstForTestCase_OneAndThree one = new PojoFirstForTestCase_OneAndThree();
		
		DynamicMailId= "Anurag"+System.currentTimeMillis()+"@gmail.com";
		
		one.setFirstName("Anurag");
		one.setLastName("Maurya");
		one.setEmail(DynamicMailId);
		one.setPassword("ANURAGMAURYA");

		Response res = given().header("Content-Type", "application/json").body(one).when()
				.post("https://thinking-tester-contact-list.herokuapp.com/users");

		TestListenerRest.getTest().info("Request Payload : ");
		TestListenerRest.getTest().info(new ObjectMapper().writeValueAsString(one));

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		token = res.jsonPath().getString("token");

		System.out.println("token is : " + token);

		res.then().statusCode(equalTo(201)).statusLine(containsString("Created")).log().body();

	}

	@Test(priority = 2, dependsOnMethods = { "testCase_01_AddNewUser_POST" })
	public void testCase_02_GetUserProfile_GET() {
		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.when().get("https://thinking-tester-contact-list.herokuapp.com/users/me");

		TestListenerRest.getTest().info("Request Type : GET ");
		TestListenerRest.getTest().info("EndPoint : /users/me");

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();

	}

	@Test(priority = 3, dependsOnMethods = { "testCase_02_GetUserProfile_GET" })
	public void testCase_03_UpdateUser_PATCH() throws JsonProcessingException {
		PojoFirstForTestCase_OneAndThree three = new PojoFirstForTestCase_OneAndThree();
		three.setFirstName("Anu");
		three.setLastName("Maurya");
		three.setEmail(DynamicMailId);
		three.setPassword("MauryaJiKaLadka");

		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.body(three).when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me");

		TestListenerRest.getTest().info("Request Payload : ");
		TestListenerRest.getTest().info(new ObjectMapper().writeValueAsString(three));

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();

	}

	@Test(priority = 4, dependsOnMethods = { "testCase_03_UpdateUser_PATCH" })
	public void testCase_04_LogInUser_POST() throws JsonProcessingException {

		PojoSecondForTestCase_Four four = new PojoSecondForTestCase_Four();

		four.setEmail(DynamicMailId);
		four.setPassword("MauryaJiKaLadka");

		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.body(four).when().post("https://thinking-tester-contact-list.herokuapp.com/users/login");

		TestListenerRest.getTest().info("Request Payload : ");
		TestListenerRest.getTest().info(new ObjectMapper().writeValueAsString(four));

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		String Token2 = res.jsonPath().getString("token");

		System.out.println("Token : " + Token2);

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();

	}

	@Test(priority = 5, dependsOnMethods = { "testCase_04_LogInUser_POST" })
	public void testCase_05_AddContact_POST() throws JsonProcessingException {

		PojoThirdForTestCase_FiveAndEight five = new PojoThirdForTestCase_FiveAndEight();

		five.setFirstName("Upendra");
		five.setLastName("Sharma");
		five.setBirthdate("1998-01-02");
		five.setEmail("sharmaUp@restAssured.com");
		five.setPhone("9988776655");
		five.setStreet1("61 street");
		five.setStreet2("sector 44");
		five.setCity("Noida");
		five.setStateProvince("Uttar Pradesh");
		five.setPostalCode("229911");
		five.setCountry("India");

		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.body(five).when().post("https://thinking-tester-contact-list.herokuapp.com/contacts");

		TestListenerRest.getTest().info("Request Payload : ");
		TestListenerRest.getTest().info(new ObjectMapper().writeValueAsString(five));

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		res.then().statusCode(equalTo(201)).statusLine(containsString("Created")).log().body();

	}

	@Test(priority = 6, dependsOnMethods = { "testCase_05_AddContact_POST" })
	public void testCase_06_GetContactList_GET() {
		given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token).when()
				.get("https://thinking-tester-contact-list.herokuapp.com/contacts").then().statusCode(equalTo(200))
				.statusLine(containsString("OK")).log().body();
	}

	@Test(priority = 7, dependsOnMethods = { "testCase_06_GetContactList_GET" })
	public void testCase_07_GetContact_GET() {
		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.when().get("https://thinking-tester-contact-list.herokuapp.com/contacts/");

		id = res.jsonPath().getString("[0]._id");

		System.out.println("id : " + id);

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();
	}

	@Test(priority = 8, dependsOnMethods = { "testCase_07_GetContact_GET" })
	public void testCase_08_FullUpdateContact_PUT() throws JsonProcessingException {

		PojoThirdForTestCase_FiveAndEight eight = new PojoThirdForTestCase_FiveAndEight();

		eight.setFirstName("Anurag");
		eight.setLastName("Maurya");
		eight.setBirthdate("1999-01-01");
		eight.setEmail("anuragM@restAssured.com");
		eight.setPhone("9876543011");
		eight.setStreet1("62 Cleo Caunty");
		eight.setStreet2("sector 73");
		eight.setCity("Noida");
		eight.setStateProvince("U. P.");
		eight.setPostalCode("098097");
		eight.setCountry("INDIA");

		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.body(eight).when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/" + id);
		 
		TestListenerRest.getTest().info("Request Payload : ");
		TestListenerRest.getTest().info(new ObjectMapper().writeValueAsString(eight));

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());
		
		String email = res.jsonPath().getString("email");
		Assert.assertTrue(email.contains("anur"), "Mail id is not matched");

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();
	}

	@Test(priority = 9, dependsOnMethods = { "testCase_08_FullUpdateContact_PUT" })
	public void testCase_09_UpdateContact_PATCH() {

		String payload = "\"{\"firstName\":\"Vivek\"}";
		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.body("{\"firstName\":\"Vivek\"}").when()
				.patch("https://thinking-tester-contact-list.herokuapp.com/contacts/" + id);

		TestListenerRest.getTest().info("Request Payload : ");
		TestListenerRest.getTest().info(payload);

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		String name = res.jsonPath().getString("firstName");
		Assert.assertTrue(name.contains("Viv"));

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();
	}

	@Test(priority = 10, dependsOnMethods = { "testCase_09_UpdateContact_PATCH" })
	public void testCase_10_LogOutUser_POST() {
		Response res = given().headers("Content-Type", "application/json").header("Authorization", "Bearer " + token)
				.when().post(" https://thinking-tester-contact-list.herokuapp.com/users/logout");

		TestListenerRest.getTest().info("Request Type : POST ");
		TestListenerRest.getTest().info("EndPoint : /logout");

		TestListenerRest.getTest().info("Response : ");
		TestListenerRest.getTest().info(res.asPrettyString());

		res.then().statusCode(equalTo(200)).statusLine(containsString("OK")).log().body();
	}

}
