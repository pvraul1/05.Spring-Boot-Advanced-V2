package com.in28minutes.springboot.firstrestapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

	private static final String ROL_ADMIN = "Admin";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDetailsRepository repository;

	@Override
	public void run(String... args) throws Exception {
		UserDetails userDetails = new UserDetails();
		userDetails.setName("Ranga");
		userDetails.setRole(ROL_ADMIN);
		repository.save(userDetails);

		userDetails = new UserDetails();
		userDetails.setName("Rava");
		userDetails.setRole(ROL_ADMIN);
		repository.save(userDetails);

		userDetails = new UserDetails();
		userDetails.setName("John");
		userDetails.setRole("User");
		repository.save(userDetails);

		userDetails = new UserDetails();
		userDetails.setName("Raul");
		userDetails.setRole("User");
		repository.save(userDetails);

		//List<UserDetails> users = repository.findAll();
		List<UserDetails> users = repository.findByRole(ROL_ADMIN);
		users.forEach(user -> logger.info(user.toString()));

	}

}
