package com.m_quinn29.typing_game.repository;

import com.m_quinn29.typing_game.model.TextPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextPromptRepository extends JpaRepository<TextPrompt, Long> {

    List<TextPrompt> findByAuthor(String author);

    List<TextPrompt> findByTextSize(String textSize);

    TextPrompt findById(int id);

}
