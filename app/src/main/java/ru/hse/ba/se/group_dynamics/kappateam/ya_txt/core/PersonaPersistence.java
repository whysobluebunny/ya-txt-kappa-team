package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.PersonaQuiz;

/**
 * Класс представляет собой механизм сохранения анкеты пользователя.
 */
public class PersonaPersistence {

    /**
     * Имя файла с данными анкеты.
     */
    private static String PERSONA_QUIZ_FILE = "persona_quiz_file";

    /**
     * Восстановить объект анкеты из файла.
     * @return объект анкеты
     */
    public static PersonaQuiz restorePersonaQuizFromFile(Context context) throws IOException, ParseException {
        File quizFilePath = new File(context.getFilesDir(), PERSONA_QUIZ_FILE);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        PersonaQuiz quiz = gson.fromJson(readFileAsString(quizFilePath), PersonaQuiz.class);
        if (quiz == null)
            throw new ParseException("Can't parse JSON.", 0);
        return quiz;
    }

    /**
     * Добавить анкету в файловую систему. Если анкета уже существует, то она перезаписывается.
     */
    public static void addOrUpdatePersonaQuiz(PersonaQuiz quiz, Context context) throws IOException {
        // from POJO to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        String JSON = gson.toJson(quiz);
        // write to file
        File quizFilePath = new File(context.getFilesDir(), PERSONA_QUIZ_FILE);
        quizFilePath.createNewFile();
        try (FileWriter fileWriter = new FileWriter(quizFilePath, false)) {
            fileWriter.write(JSON);
        }
    }

    /**
     * Удалить анкету из файловой системы.
     */
    public static void removePersonaQuizFromFileSystem(Context context) throws IOException {
        File quizFilePath = new File(context.getFilesDir(), PERSONA_QUIZ_FILE);
        if(!quizFilePath.delete()) {
            throw new IOException("Persona Quiz cannot be deleted.");
        }
    }

    // прочитать файл и вернуть содержимое в виде строки
    private static String readFileAsString(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line = buf.readLine();
            while (line != null) {
                sb.append(line);
                line = buf.readLine();
            }
        }
        return sb.toString();
    }
}
