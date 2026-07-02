package com.m_quinn29.typing_game.repository;

import com.m_quinn29.typing_game.model.RandomWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomWordRepository extends JpaRepository<RandomWord, Long> {

    RandomWord findById(int id);
}
