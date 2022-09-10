package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

	private static final String GENERIC_QUESTION_URL = "/surveys/Survey1/questions";

	@Autowired
	TestRestTemplate template;

	String str = """

			{
				"id": "Question1",
				"description": "Most Popular Cloud Platform Today",
				"options":[
					"AWS",
					"Azure",
					"Google Cloud",
					"Oracle Cloud"
				],
				"correctAnswer": "AWS"
			}

			""";

	// {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
	// [Content-Type:"application/json",

	@Test
	void retrieveSpecificSurveyQuestions_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		String expectedResponse = 
			"""
				{
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"correctAnswer":"AWS"
				}
			""";

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(str, responseEntity.getBody(), true);
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false); // It have not options

	}

	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTION_URL, String.class);

		String expectedResponse = 
			"""
				[
					{
						"id":"Question1"
					},
					{
						"id":"Question2"
					},
					{
						"id":"Question3"
					}
				]
			""";

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}

	@Test
	void addNewSurveyQuestion_basicScenario() {

		String requestBody = """

			{
				"id":"Question1",
				"description":"Your Favorite Language",
				"options":["Java","Python","JavaScript","Haskell"],
				"correctAnswer":"Java"
			}

				""";

		// http://localhost:8080/surveys/Survey1/questions
		// POST
		// Content-Type: "application/json"
		// 201
		// Location: http://localhost:8080/surveys/Survey1/questions/1778741044

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTION_URL, HttpMethod.POST, httpEntity, String.class);

		// [Location:"http://localhost:58500/surveys/Survey1/questions/2781864973", Content-Length:"0", Date:"Sat, 10 Sep 2022 16:53:31 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
		// Asserts
		// 201
		// Location: "/surveys/Survey1/questions/"
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

		// DELETE
		// locationHeader

		template.delete(locationHeader);
	}
}
