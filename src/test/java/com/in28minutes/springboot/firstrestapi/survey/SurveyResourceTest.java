package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
class SurveyResourceTest {

	private static final String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

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

		assertEquals(200, mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
	}

}
