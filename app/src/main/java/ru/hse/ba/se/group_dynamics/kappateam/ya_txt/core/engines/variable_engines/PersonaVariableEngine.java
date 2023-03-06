package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.variable_engines;

import android.content.Context;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.PersonaPersistence;

/**
 * Движок для переменных класса PERSONA.
 */
public class PersonaVariableEngine {
    /**
     * Получить динамический контент для переменной.
     *
     * @param variable переменная
     * @return вычисленный динамический контент для переменной
     */
    public static String getCalculatedContent(Variable variable, Context context) throws IOException, ParseException {
            switch (variable.getVarType()) {
                case PERSONA_AGE:
                    return PersonaPersistence.restorePersonaQuizFromFile(context).getAge();
                case PERSONA_GENDER:
                    return PersonaPersistence.restorePersonaQuizFromFile(context).getGender();
                case PERSONA_HOMETOWN:
                    return PersonaPersistence.restorePersonaQuizFromFile(context).getHomeTown();
                case PERSONA_MUSIC_GENRE:
                    ArrayList<String> genres = PersonaPersistence.restorePersonaQuizFromFile(context).getMusicGenres();
                    return genres.get(new Random().nextInt(genres.size()));
                case PERSONA_SPOKEN_LANGUAGES:
                    ArrayList<String> langs = PersonaPersistence.restorePersonaQuizFromFile(context).getSpokenLanguages();
                    return langs.get(new Random().nextInt(langs.size()));
                default:
                    throw new IllegalStateException("Unsupported variable type class occurred.");
            }
    }
}
