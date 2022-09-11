package com.in28minutes.springboot.firstrestapi.survey;

import org.junit.jupiter.api.Test;
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

	@MockBean
	private SurveyService surveyService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void tretrieveSpecificSurveyQuestionsest_basicScenario() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(mvcResult.getResponse().getContentAsString());
		System.out.println(mvcResult.getResponse().getStatus());
	}

}
