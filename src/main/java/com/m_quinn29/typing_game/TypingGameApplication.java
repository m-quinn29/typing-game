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

			repository.save(new TextPrompt("Book1", "John", "Once upon a time in Hollywood...", "other"));
			repository.save(new TextPrompt("Java101",  "Ryan", "This is how to use java..", "other"));
			repository.save(new TextPrompt("Cooking101", "Bob", "A cookie is..", "other"));
			repository.save(new TextPrompt("A Separate Peace", "John Knowles", "Sometimes you are too ashamed to leave. That was true now. And sometimes you need too much to know the facts, and so humbly and stupidly you stay.", "Book"));
			repository.save(new TextPrompt("Change Your Brain", "Daniel G. Amen", "Language is one of the keys to being human. It allows us to communicate with other human beings and to leave a legacy of our thoughts and actions for future generations. The dominant temporal lobe helps to process sounds and written words into meaningful information.", "Book"));
			repository.save(new TextPrompt("Gulliver's Travels", "Jonathan Swift", "They look upon fraud as a greater crime than theft, and therefore seldom fail to punish it with death.", "Book"));
			repository.save(new TextPrompt("Alan Turing Quote", "Alan Turing", "Sometimes it is the people no one can imagine anything of who do the things no one can imagine.", "Quote"));
			repository.save(new TextPrompt("Grace Hopper Quote", "Grace Hopper", "Humans are allergic to change. They love to say, 'We've always done it this way.' I try to fight that. That's why I have a clock on my wall that runs counter-clockwise.", "Quote"));
			repository.save(new TextPrompt("The Book of Useless Information", "Noel Botham & The Useless information Society", "The fastest dog, the greyhound, can reach speeds of up to forty five miles per hour.", "Book"))
;

			logger.info("Texts found with findAll():");
			logger.info("---------------------------");
			repository.findAll().forEach(text -> {
				logger.info(text.toString());
			});
		};
	}

}
