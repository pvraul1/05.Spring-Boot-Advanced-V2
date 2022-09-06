package com.in28minutes.springboot.firstrestapi.survey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {

	private String id;
	private String title;
	private String description;
	private List<Question> questions;

}
