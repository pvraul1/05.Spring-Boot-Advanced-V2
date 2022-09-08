package com.in28minutes.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {

	private SurveyService surveyService;

	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}

	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys() {
		return surveyService.retrieveAllSurveys();
	}

	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId) {
		Survey survey = surveyService.retrieveSurveyById(surveyId);
		if (survey == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return survey;
	}

	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);
		if (questions == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return questions;
	}

	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestions(@PathVariable String surveyId, @PathVariable String questionId) {
		Question question = surveyService.retrieveSpecificSurveyQuestions(surveyId, questionId);
		if (question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return question;
	}

	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Object> addNewSurveyQuestions(@PathVariable String surveyId, @RequestBody Question question) {
		String questionId = surveyService.addNewSurveyQuetion(surveyId, question);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{questionId}")
				.buildAndExpand(questionId)
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> deleteSurveyQuestions(@PathVariable String surveyId, @PathVariable String questionId) {
		surveyService.deleteSurveyQuestions(surveyId, questionId);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> updateSurveyQuestions(@PathVariable String surveyId, @PathVariable String questionId,
			@RequestBody Question question) {

		surveyService.updateSurveyQuestions(surveyId, questionId, question);

		return ResponseEntity.noContent().build();
	}

}
