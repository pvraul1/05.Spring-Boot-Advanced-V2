package com.in28minutes.springboot.firstrestapi.survey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

	private String id;
	private String description;
	private List<String> options;
	private String correctAnswer;
	
}
