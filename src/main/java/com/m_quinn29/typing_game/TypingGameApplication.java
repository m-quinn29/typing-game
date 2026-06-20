package com.m_quinn29.typing_game;

import com.m_quinn29.typing_game.model.TextPrompt;
import com.m_quinn29.typing_game.repository.TextPromptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class TypingGameApplication {

	private static final Logger logger = LoggerFactory.getLogger(TypingGameApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TypingGameApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx, TextPromptRepository repository){
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			repository.save(new TextPrompt("Book1", "John", "Once upon a time in Hollywood..."));
			repository.save(new TextPrompt("Java101",  "Ryan", "This is how to use java.. "));
			repository.save(new TextPrompt("Cooking101", "Bob", "A cookie is.."));

			logger.info("Texts found with findAll():");
			logger.info("---------------------------");
			repository.findAll().forEach(text -> {
				logger.info(text.toString());
			});
		};
	}

}
