package com.m_quinn29.typing_game.model;

import jakarta.persistence.*;

@Entity
public class WordPrompt {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String word;
    private int length;

    protected WordPrompt() {}

    public WordPrompt(String word, int length) {
        this.word = word;
        this.length = length;
    }

    @Override
    public String toString(){
        return String.format("%d : %s : %d", id, word, length);
    }

    public int getLength() {
        return length;
    }

    public String getWord() {
        return word;
    }

    public Long getId() {
        return id;
    }
}
