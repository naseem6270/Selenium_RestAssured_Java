package com.Company.BusinessObjects;

import java.io.File;
import java.util.List;

import org.testng.annotations.Test;

import com.Company.FrameworkController.BasePage;
import com.Company.ReusableComponents.CommonFunctions;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 *******************************************************************************
 * 
 * Test class to validate the business requirement of the application API
 * 
 * @implNote:- This class should extend the BasePage. This is required to
 *             initialize the driver, start reporting etc.
 * 
 * @author Naseem Ahmed, Date:- 04/20/2022
 *******************************************************************************
 **/

public class TestScenariosAPI extends BasePage {
	CommonFunctions commFunc;
	String baseURI, userID, tokenKey, placeToBeSearched, styleID;

	public TestScenariosAPI() {
		commFunc = new CommonFunctions();
		baseURI = commFunc.readPropertyFile("TestData", "baseURI");
		userID = commFunc.readPropertyFile("TestData", "userID");
		tokenKey = commFunc.readPropertyFile("TestData", "tokenKey");
		placeToBeSearched = commFunc.readPropertyFile("TestData", "placeToBeSearched");

	}

	/**
	 *****************************************************************************
	 * This function will verify the Map box location features
	 * 
	 * HTTP Method:- GET, Response Code:- 200
	 * 
	 * @param location String
	 * @param tokenID  String
	 *****************************************************************************
	 **/

	@Test(priority = 1)
	public void testSearchForPlaceMapAPI() {

		String subURL = "geocoding/v5/mapbox.places";
		String apiURL = baseURI + "/" + subURL + "/" + placeToBeSearched + ".json?access_token=" + tokenKey;

		Response response = RestAssured.given().when().get(apiURL).then().extract().response();

		JsonPath jsonPath = response.jsonPath();
		String placeName = jsonPath.getString("features.place_name[0]");
		int noofRecords = jsonPath.getList("features.id").size();

		if (response.statusCode() == 200) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the search result with the response code as "
							+ response.statusCode());

		} else {
			extentTest.log(LogStatus.FAIL,
					"Application is not showing the search result with the proper response code. Expected was 200 but got from the API is "
							+ String.valueOf(response.statusCode()));

		}

		extentTest.log(LogStatus.INFO, "Number of records founds: " + noofRecords);

		if (placeName.contains(placeToBeSearched)) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the searched place name as " + placeToBeSearched + " in the API");
		} else {
			extentTest.log(LogStatus.FAIL, "Application is not showing the searched place name in the API.");

		}

	}

	/**
	 *****************************************************************************
	 * This function will verify the Invalid cases which are mentioned below
	 * 
	 * 1. Test with Blank Address
	 * 
	 * 2. Test with Invalid Token Key
	 * 
	 * 3. Testing when address entered with CAPS LOCK ON
	 * 
	 * 4. Testing when address entered with all in lower case
	 * 
	 * 5. Testing when address entered with some special chars
	 * 
	 * 6. Testing when invalid address entered
	 * 
	 * HTTP Method:- GET,Response Code:- 200, 404
	 * 
	 * @param location       String
	 * @param invalidTokenID String
	 * @param tokenID        String
	 *****************************************************************************
	 **/

	@Test(priority = 2)
	public void testInvalidSearchAPIScenarios() {

		// Test with Blank Address
		String blankPlaceToBeSearched = "";

		String subURL = "geocoding/v5/mapbox.places";
		String apiURL = baseURI + "/" + subURL + "/" + blankPlaceToBeSearched + ".json?access_token=" + tokenKey;

		Response response = RestAssured.given().when().get(apiURL).then().extract().response();

		if (response.statusCode() == 200) {
			extentTest.log(LogStatus.FAIL,
					"Application is showing the response code as 200 but it should throw an error when user tries to search with a blank address.");

		} else {
			extentTest.log(LogStatus.PASS,
					"Application is properly not showing the response code as 200 when user tries to search with a blank address.");

		}
		// Test with Invalid Token Key

		String invalidTokenKey = "123456";

		subURL = "geocoding/v5/mapbox.places";
		apiURL = baseURI + "/" + subURL + "/" + placeToBeSearched + ".json?access_token=" + invalidTokenKey;

		response = RestAssured.given().when().get(apiURL).then().extract().response();

		if (response.statusCode() == 401) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the UnAuthorized status code when user tries to search with an Invalid Token ID");

		} else {
			extentTest.log(LogStatus.FAIL,
					"Application is showing the UnAuthorized status code when user tries to search with an Invalid Token ID. Expected was 401 but got from application is "
							+ String.valueOf(response.statusCode()));

		}

		// Testing when address entered with CAPS LOCK ON

		String placeToBeSearchedInDifferentCase = placeToBeSearched.toUpperCase();

		subURL = "geocoding/v5/mapbox.places";
		apiURL = baseURI + "/" + subURL + "/" + placeToBeSearchedInDifferentCase + ".json?access_token=" + tokenKey;

		response = RestAssured.given().when().get(apiURL).then().extract().response();

		if (response.statusCode() == 200) {
			extentTest.log(LogStatus.PASS, "Application is properly showing the response code as "
					+ response.statusCode() + " when user enters the address in Upper case.");

		} else {
			extentTest.log(LogStatus.FAIL, "Application is not showing the response code as " + response.statusCode()
					+ " when user enters the address in Upper case.");

		}

		// Testing when address entered with all in lower case

		placeToBeSearchedInDifferentCase = placeToBeSearched.toLowerCase();

		subURL = "geocoding/v5/mapbox.places";
		apiURL = baseURI + "/" + subURL + "/" + placeToBeSearchedInDifferentCase + ".json?access_token=" + tokenKey;

		response = RestAssured.given().when().get(apiURL).then().extract().response();

		if (response.statusCode() == 200) {
			extentTest.log(LogStatus.PASS, "Application is properly showing the response code as "
					+ response.statusCode() + " when user enters the address in lower case.");

		} else {
			extentTest.log(LogStatus.FAIL, "Application is not showing the response code as " + response.statusCode()
					+ " when user enters the address in lower case.");

		}

		// Testing when address entered with some special chars

		String placewithSpecialCharacter = "201- \"New Jersey\", //USA ";

		subURL = "geocoding/v5/mapbox.places";
		apiURL = baseURI + "/" + subURL + "/" + placewithSpecialCharacter + ".json?access_token=" + tokenKey;

		response = RestAssured.given().when().get(apiURL).then().extract().response();

		if (response.statusCode() == 200) {
			extentTest.log(LogStatus.PASS, "Application is properly showing the response code as "
					+ response.statusCode() + " when user enters the address with special chars.");

		} else {
			extentTest.log(LogStatus.FAIL, "Application is not showing the response code as " + response.statusCode()
					+ " when user enters the address with special chars.");

		}

		// Testing when invalid address entered

		String invalidAddress = "InvalidAddress";

		subURL = "geocoding/v5/mapbox.places";
		apiURL = baseURI + "/" + subURL + "/" + invalidAddress + ".json?access_token=" + tokenKey;

		response = RestAssured.given().when().get(apiURL).then().extract().response();

		JsonPath jsonPath = response.jsonPath();

		List<String> list = jsonPath.getList("query");
		if (list.contains("invalidaddress")) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the error as Invalid Address when user tries to search for an invalid address");

		} else {
			extentTest.log(LogStatus.FAIL,
					"Application is not showing the error as Invalid Address when user tries to search for an invalid address");

		}

	}

	/**
	 *****************************************************************************
	 * This function will verify the Create Style functionality
	 * 
	 * HTTP Method:- POST, Response Code:- 201
	 * 
	 * @param tokenID String
	 * @param body    String
	 *****************************************************************************
	 **/

	@Test(priority = 3)
	public void testCreateStyleMapAPI() {

		File testDataFile = new File(
				System.getProperty("user.dir") + "//src//test//resources//config//APITestData//CreateStyle.txt");

		String subURL = "styles/v1";
		String apiURL = baseURI + "/" + subURL + "/" + userID + "?access_token=" + tokenKey;

		Response response = RestAssured.given().header("Content-Type", "application/json").body(testDataFile).when()
				.post(apiURL).then().extract().response();

		JsonPath jsonPath = response.jsonPath();
		styleID = jsonPath.get("id");
		String ownerName = jsonPath.get("owner");

		if (response.statusCode() == 201) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the response code as " + response.statusCode());

		} else {
			extentTest.log(LogStatus.FAIL,
					"Application is not showing proper response code. Expected was 201 but got from the API is "
							+ String.valueOf(response.statusCode()));

		}

		extentTest.log(LogStatus.INFO, "Stored the Created Style ID as " + styleID);

		if (ownerName.equals(userID)) {
			extentTest.log(LogStatus.PASS, "Application is properly showing the Owner name in the Create Style API");
		} else {
			extentTest.log(LogStatus.FAIL,
					"Failed:- Application is properly showing the Owner name in the Create Style API. Expected was "
							+ userID + " but got from API is " + ownerName);

		}

	}

	/**
	 *****************************************************************************
	 * This function will verify the Update Style functionality
	 * 
	 * HTTP Method:- PATCH, Response Code:- 200
	 * 
	 * @param tokenID String
	 * @param styleID String
	 * @param body    String
	 *****************************************************************************
	 **/

	@Test(priority = 4)
	public void testUpdateStyleMapAPI() {

		File testDataFile = new File(
				System.getProperty("user.dir") + "//src//test//resources//config//APITestData//UpdateStyle.txt");

		String subURL = "styles/v1";
		String apiURL = baseURI + "/" + subURL + "/" + userID + "/" + styleID + "?access_token=" + tokenKey;

		Response response = RestAssured.given().header("Content-Type", "application/json").body(testDataFile).when()
				.patch(apiURL).then().extract().response();

		if (response.statusCode() == 200) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the response code as " + response.statusCode());

		} else {
			extentTest.log(LogStatus.FAIL,
					"Application is not showing proper response code. Expected was 200 but got from the API is "
							+ String.valueOf(response.statusCode()));

		}

		JsonPath jsonPath = response.jsonPath();
		String newStyleID = jsonPath.get("id");
		String ownerName = jsonPath.get("owner");

		if (newStyleID.equals(styleID)) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the Same Style ID after the Update Style API");
		} else {
			extentTest.log(LogStatus.FAIL,
					"Failed:- Application is not showing the same Style ID after the Update Style API. Expected was "
							+ styleID + " but got from API is " + newStyleID);
		}

		if (ownerName.equals(userID)) {
			extentTest.log(LogStatus.PASS, "Application is properly showing the Owner name in the Update Style API");
		} else {
			extentTest.log(LogStatus.FAIL,
					"Failed:- Application is properly showing the Owner name in the Update Style API. Expected was "
							+ userID + " but got from API is " + ownerName);

		}
	}

	/**
	 *****************************************************************************
	 * This function will verify the Delete Style functionality
	 * 
	 * HTTP Method:- Delete, Response Code:- 204
	 * 
	 * @param tokenID String
	 * @param styleID String
	 *****************************************************************************
	 **/

	@Test(priority = 5)
	public void testDeleteStyleMapAPI() {

		String subURL = "styles/v1";
		String apiURL = baseURI + "/" + subURL + "/" + userID + "/" + styleID + "?access_token=" + tokenKey;

		Response response = RestAssured.given().when().delete(apiURL).then().extract().response();

		if (response.statusCode() == 204) {
			extentTest.log(LogStatus.PASS,
					"Application is properly showing the response code as " + response.statusCode());

		} else {
			extentTest.log(LogStatus.FAIL,
					"Application is not showing proper response code. Expected was 204 but got from the API is "
							+ String.valueOf(response.statusCode()));

		}
	}

}
