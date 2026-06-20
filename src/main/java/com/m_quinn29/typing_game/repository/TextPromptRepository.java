package com.m_quinn29.typing_game.repository;

import com.m_quinn29.typing_game.model.TextPrompt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TextPromptRepository extends CrudRepository<TextPrompt, Long> {

    List<TextPrompt> findByAuthor(String author);

    List<TextPrompt> findByTextSize(String textSize);

    TextPrompt findById(int id);

}
