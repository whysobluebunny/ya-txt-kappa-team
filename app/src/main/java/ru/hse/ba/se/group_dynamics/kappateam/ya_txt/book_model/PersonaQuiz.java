package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model;

import java.util.ArrayList;

/**
 * Анкета, заполняемая пользователем.
 */
public class PersonaQuiz {

    /**
     * Родной город читателя.
     */
    private final String homeTown;

    /**
     * Гендер читателя.
     */
    private final String gender;

    /**
     * Возраст читателя.
     */
    private final String age;

    /**
     * Предпочитаемые читателем музыкальные жанры.
     */
    private final ArrayList<String> musicGenres;

    /**
     * Языки, которыми владеет читатель.
     */
    private final ArrayList<String> spokenLanguages;

    public PersonaQuiz(String homeTown, String gender, String age, ArrayList<String> musicGenres, ArrayList<String> spokenLanguages) {
        this.homeTown = homeTown;
        this.gender = gender;
        this.age = age;
        this.musicGenres = musicGenres;
        this.spokenLanguages = spokenLanguages;
    }

    /**
     * @return родной город читателя
     */
    public String getHomeTown() {
        return homeTown;
    }

    /**
     * @return гендер читателя
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return возраст читателя
     */
    public String getAge() {
        return age;
    }

    /**
     * @return предпочтительные муз. жанры читателя
     */
    public ArrayList<String> getMusicGenres() {
        return musicGenres;
    }

    /**
     * @return языки, которыми владеет читатель
     */
    public ArrayList<String> getSpokenLanguages() {
        return spokenLanguages;
    }
}
