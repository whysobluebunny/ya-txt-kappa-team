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
    private ArrayList<String> musicGenres;

    /**
     * Языки, которыми владеет читатель.
     */
    private ArrayList<String> spokenLanguages;

    public PersonaQuiz(String homeTown,
                       String gender,
                       String age,
                       ArrayList<String> musicGenres,
                       ArrayList<String> spokenLanguages) {
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
        return homeTown != null ? homeTown : "Moscow";
    }

    /**
     * @return гендер читателя
     */
    public String getGender() {
        return gender != null ? gender : "Man";
    }

    /**
     * @return возраст читателя
     */
    public String getAge() {
        return age != null ? gender : "18";
    }

    /**
     * @return предпочтительные муз. жанры читателя
     */
    public ArrayList<String> getMusicGenres() {
        if (musicGenres == null || musicGenres.isEmpty()){
            musicGenres = new ArrayList<>();
            musicGenres.add("Rock");
        }
        return musicGenres;
    }

    /**
     * @return языки, которыми владеет читатель
     */
    public ArrayList<String> getSpokenLanguages() {
        if (spokenLanguages == null || spokenLanguages.isEmpty()){
            spokenLanguages = new ArrayList<>();
            spokenLanguages.add("Russian");
        }
        return spokenLanguages;
    }
}
