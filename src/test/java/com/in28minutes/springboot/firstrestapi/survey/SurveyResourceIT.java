package com.in28minutes.springboot.firstrestapi.survey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

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

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

	@Autowired
	TestRestTemplate template;

	// {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
	// [Content-Type:"application/json"
	
	@Test
	void retrieveSpecificSurveyQuestions_basicScenario() {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
		System.out.println(responseEntity.getBody());
		System.out.println(responseEntity.getHeaders());
	}
}
