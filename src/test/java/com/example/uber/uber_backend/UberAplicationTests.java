package com.example.uber.uber_backend;

import com.example.uber.uber_backend.utilis.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAplicationTests {

	@Autowired
	private EmailService emailService;

	@Test
	void contextLoads() {
		String[] mutlipleEmail={"jaforo3396@camjoint.com","xipugo@forexnews.bg"};
		emailService.sendEmail(mutlipleEmail,"Uber mail","Testing......",null);
	}

}
