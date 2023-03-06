package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Node;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.StaticNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.TriggerNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils.RuntimeTypeAdapterFactory;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils.SampleBookGenerator;

/**
 * Класс представляет собой репозиторий книг и предоставляет ряд операций для работы с этим репозиторием.
 */
public class BookRepository {

    /**
     * Имя файла с прогрессом чтения книги.
     */
    private static String PROGRESS_FILE_NAME = "progress_file";

    /**
     * Имя файла с книгой.
     */
    private static String BOOK_FILE_NAME = "book_contents_file";

    /**
     * Имя файла с данными по скачанным книгам.
     */
    private static String SAVED_BOOKS_FILE_NAME = "saved_books_file";

    /**
     * Восстановить объект книги из репозитория.
     * @param id идентификатор книги
     * @return объект книги
     */
    public static Book restoreBookFromRepository(String id, Context context) throws IOException, ParseException {
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File bookFilePath = new File(bookDir, BOOK_FILE_NAME);
        return parseFromJSON(readFileAsString(bookFilePath));
    }

    /**
     * Отдать айдишники сохраненных книг.
     * @param context контекст приложения
     * @return айдишники сохраненных книг в виде строк
     */
    public static ArrayList<String> getSavedBooksIDs(Context context) throws IOException, ParseException {
        File booksFile = new File(getCreateBooksDir(context), SAVED_BOOKS_FILE_NAME);
        ArrayList<String> parametrized;
        if (booksFile.length() == 0) {
            // файла не существует или он пуст
            return new ArrayList<String>();
        }
        else {
            String content = readFileAsString(booksFile);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            ArrayList unparametrized = gson.fromJson(content, ArrayList.class);
            if (unparametrized == null) {
                throw new ParseException("Can't parse JSON.", 0);
            }
            else {
                parametrized = new ArrayList<>(unparametrized);
            }
        }
        return parametrized;
    }

    /**
     * Добавляет книгу в список сохраненных.
     * @param id идентификатор книги
     * @param context контекст приложения
     */
    public static void addSavedBookId(String id, Context context) throws IOException, ParseException {
        File booksFile = new File(getCreateBooksDir(context), SAVED_BOOKS_FILE_NAME);
        if(!booksFile.createNewFile()) {
            ArrayList<String> current = getSavedBooksIDs(context);
            current.add(id);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            String JSON = gson.toJson(current);
            try (FileWriter fileWriter = new FileWriter(booksFile, false)) {
                fileWriter.write(JSON);
            }
        }
        else {
            ArrayList<String> current = new ArrayList<>();
            current.add(id);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            String JSON = gson.toJson(current);
            try (FileWriter fileWriter = new FileWriter(booksFile, false)) {
                fileWriter.write(JSON);
            }
        }
    }

    /**
     * Удалить книгу из репозитория.
     * @param id идентификатор книги
     */
    public static void removeBookFromRepository(String id, Context context) throws IOException {
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File bookFilePath = new File(bookDir, BOOK_FILE_NAME);
        File progressFilePath = new File(bookDir, PROGRESS_FILE_NAME);
        if(!progressFilePath.delete()) {
            throw new IOException("Progress file for the book of id " + id + " cannot be deleted.");
        }
        if(!bookFilePath.delete()) {
            throw new IOException("Book contents file for the book of id " + id + " cannot be deleted.");
        }
        if(!bookDir.delete()) {
            throw new IOException("Book folder for the book of id " + id + " cannot be deleted.");
        }
    }

    /**
     * Добавить в репозиторий книгу, скачав ее с сервера.
     * @param id идентификатор книги
     */
    public static void addBookFromServerToRepository(String JSON, String id, Context context) throws IOException {
        prepareDirForABook(id, context);
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File bookFilePath = new File(bookDir, BOOK_FILE_NAME);

        try (FileWriter fileWriter = new FileWriter(bookFilePath, false)) {
            fileWriter.write(JSON);
        }
    }

    /**
     * Сохранить прогресс чтения по книге.
     * @param id идентификатор книги
     * @param progress Текущий прогресс чтения по книге
     */
    public static void saveCurrentBookProgress(String id, String progress, Context context) throws IOException {
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File progressFilePath = new File(bookDir, PROGRESS_FILE_NAME);
        try (FileWriter fileWriter = new FileWriter(progressFilePath)) {
            fileWriter.write(progress);
        }
    }

    /**
     * Получит прогресс чтения по книге.
     * @param id идентификатор книги
     * @return прогресс чтения в виде строки
     */
    public static String getCurrentBookProgress(String id, Context context) throws IOException {
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File progressFilePath = new File(bookDir, PROGRESS_FILE_NAME);
        return readFileAsString(progressFilePath);
    }

    /**
     * Есть ли у книги прогресс чтения или он пуст?
     * @param id идентификатор книги
     * @return true, если прогресс чтения есть в репозитории, false - в ином случае
     */
    public static boolean isCurrentBookProgressEmpty(String id, Context context) {
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File progressFilePath = new File(bookDir, PROGRESS_FILE_NAME);
        return progressFilePath.length() == 0L;
    }

    /**
     * Обнулить прогресс чтения книги.
     * @param id идентификатор книги
     */
    public static void resetCurrentBookProgress(String id, Context context) throws IOException {
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        File progressFilePath = new File(bookDir, PROGRESS_FILE_NAME);
        if(!progressFilePath.delete()) {
            throw new IOException("Progress file for the book of id " + id + " cannot be deleted.");
        }
        if(!progressFilePath.createNewFile()) {
            throw new IOException("Progress file for the book of id " + id + " already exists.");
        }
    }

    // получить директорию с книгами (при этом создать ее, если таковой не существует)
    private static File getCreateBooksDir(Context context){
        return context.getDir("books", Context.MODE_PRIVATE);
    }

    // подготовить директорию со всем необходимым для книги (при добавлении книги в репозиторий)
    private static File prepareDirForABook(String id, Context context) throws IOException {
        // создаем путь к папке с данными книги
        File bookDir = new File(getCreateBooksDir(context), getHashedRepresentationOfId(id));
        if(!bookDir.mkdir()){
            throw new IOException("Directory for the book of id " + id + " already exists.");
        }
        // добавляем в папку progress_file и book
        File progressFilePath = new File(bookDir, PROGRESS_FILE_NAME);
        if(!progressFilePath.createNewFile()) {
            throw new IOException("Progress file for the book of id " + id + " already exists.");
        }
        File bookFilePath = new File(bookDir, BOOK_FILE_NAME);
        if(!bookFilePath.createNewFile()) {
            throw new IOException("Book contents file for the book of id " + id + " already exists.");
        }

        return bookDir;
    }

    // прочитать файл и вернуть содержимое в виде строки
    public static String readFileAsString(File file) throws IOException {
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

    // спарсить книгу из JSON
    private static Book parseFromJSON(String JSON) throws ParseException {
        RuntimeTypeAdapterFactory<Node> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Node.class, "nodeSubtype")
                .registerSubtype(TriggerNode.class, "triggerNode")
                .registerSubtype(StaticNode.class, "staticNode");

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        Book book = gson.fromJson(JSON, Book.class);
        if (book == null)
            throw new ParseException("Can't parse JSON.", 0);
        return book;
    }

    // получить безопасное для применения в качестве имени файла представление идентификатора
    private static String getHashedRepresentationOfId(String id){
        return id.replaceAll("[^a-zA-Z0-9.-]", "_") +
                (id + id.length()).hashCode() +
                id.length() + id.length();
    }

}
