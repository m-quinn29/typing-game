package com.m_quinn29.typing_game.model;

import jakarta.persistence.*;

@Entity
public class TextPrompt {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    @Column(length = 1000)
    private String text;
    private int wordCount;
    private String textSize; // "short", "medium", "long"
    private String media;


    // Constructor exists only for the sake off JPA
    protected TextPrompt() {}

    public TextPrompt(String title, String author, String text, String media){
        this.title = title;
        this.author = author;
        this.text = text;
        this.media = media;
        this.wordCount = calculateWordCount();
        this.textSize = calculateTextSize();
    }

    //TODO
    private int calculateWordCount(){
        return 0;
    }

    //TODO

    /**
     * Calculates text size depending on word count.
     * @return String: "short", "medium", or "long"
     */
    private String calculateTextSize(){
        return "short";
    }

    @Override
    public String toString(){
        return String.format(
                "id=%d : title=%s : author=%s : size=%s",
                id, title, author, textSize);
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getText(){
        return text;
    }

    public int getWordCount(){
        return wordCount;
    }

    public String getTextSize() {
        return textSize;
    }

    public String getMedia() {
        return media;
    }
}
