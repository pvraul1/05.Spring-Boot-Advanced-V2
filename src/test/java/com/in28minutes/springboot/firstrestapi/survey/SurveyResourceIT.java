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
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

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

}
