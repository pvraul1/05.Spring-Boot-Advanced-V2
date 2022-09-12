package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
class SurveyResourceTest {

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

	private static final String GENERIC_QUESTION_URL = "/surveys/Survey1/questions";

	private static final String GOOGLE_CLOUD = "Google Cloud";

	@MockBean
	private SurveyService surveyService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void tretrieveSpecificSurveyQuestionsest_404Scenario() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	void tretrieveSpecificSurveyQuestionsest_basicScenario() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", GOOGLE_CLOUD, "Oracle Cloud"), "AWS");

		when(surveyService.retrieveSpecificSurveyQuestions("Survey1", "Question1")).thenReturn(question);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		String expectedResponse = """
				{
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"correctAnswer":"AWS"
				}
				""";

		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
	}

	// addNewSurveyQuestions
	// POST
	// 201
	// Location: http://localhost:8080/surveys/Survey1/questions/1778741044

	String requestBody = """

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

	@Test
	void addNewSurveyQuestions() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(GENERIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);

		when(surveyService.addNewSurveyQuetion(anyString(), any())).thenReturn("SOME_ID");

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(201, response.getStatus());
		String locationHeader = response.getHeader("Location");
		assertTrue(locationHeader.contains("/surveys/Survey1/questions/SOME_ID"));
	}

}
