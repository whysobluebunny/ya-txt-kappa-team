package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.IOException;
import java.text.ParseException;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.BookRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.engines.BookEngine;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IHtmlView;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.interfaces.IHtmlViewEventsHandler;

/**
 * Activity для чтения книги.
 */
public class ReadActivity extends AppCompatActivity implements IHtmlView {

    /**
     * Передаваемый в интенте идентификатор книги, которую нужно подгрузить.
     */
    public static final String BOOK_ID_INTENT_EXTRA = "BOOK_ID_INTENT_EXTRA";

    /**
     * Произошла ли фатальная ошибка в движке читалки?
     */
    private boolean errorState = false;

    /**
     * Внутренний класс, для взаимодействия с JS-ом.
     */
    private class JavaScriptInterface {
        @JavascriptInterface
        public void onBookOpen() {
            eventsHandler.onBookOpen(ReadActivity.this, book.getBookId());
        }

        @JavascriptInterface
        public void onTriggerNodeAppear(String id) {
            ReadActivity.this.eventsHandler.onTriggerNodeAppearance(ReadActivity.this, id);
        }

        @JavascriptInterface
        public void onVariableAppear(String id) {
            ReadActivity.this.eventsHandler.onVariableAppearance(ReadActivity.this, id);
        }
    }

    /**
     * Обработчик событий, происходящих с книгой (движок).
     */
    private IHtmlViewEventsHandler eventsHandler;
    /**
     * WebView (Chromium), в котором производится отрисовка контента и их которого события летят в движок.
     */
    private WebView webView;
    /**
     * Книга, которую читает пользователь в данном Activity.
     */
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // скрываем верхнюю панель
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // устанавливаем layout
        setContentView(R.layout.activity_read);
        // включаем полноэкранный режим
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // инициализируем WebView
        initializeWebView();
        // загружаем ID книги из данных интента
        try {
            String id = getIntent().getStringExtra(BOOK_ID_INTENT_EXTRA);
            book = BookRepository.restoreBookFromRepository(id, getApplicationContext());
        } catch (Exception e) {
            Log.e("ReadActivity","[cons] Error occurred while parsing the book from device drive.", e);
            reportErrorState();
        }
        // назначаем движок книги обработчиком событий
        try {
            eventsHandler = new BookEngine(getApplicationContext(), book);
        }
        catch (Exception e) {
            Log.e("ReadActivity","[cons] Error occurred while instantiating the book engine.", e);
            reportErrorState();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!errorState) {
            webView.evaluateJavascript("javascript:getHtml()", s -> {
                String html = s.substring(1, s.length() - 1);
                eventsHandler.onBookClose(book.getBookId(), html, this);
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    /**
     * Инициализирует webView.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initializeWebView() {
        // находим WebView и подгружаем его в поле класса
        webView = findViewById(R.id.webView);
        // разрешаем debug для подключение с компьютера
        WebView.setWebContentsDebuggingEnabled(true);
        // разрешаем JS
        webView.getSettings().setJavaScriptEnabled(true);
        // устанавливаем движок как Chrome для нормальной работы с JS
        webView.setWebChromeClient(new WebChromeClient());
        // регистрируем мост между Java и JS
        webView.addJavascriptInterface(new JavaScriptInterface(), "Android");
        // загружаем каркас
        webView.loadUrl("file:///android_asset/basis.html");
    }

    /**
     * Вызывает функцию из JavaScript.
     *
     * @param funcName Имя вызываемой функции.
     */
    private void callJsFunction(String funcName) {
        webView.post(() -> webView.loadUrl("javascript:" + funcName));
    }

    /**
     * Загрузить HTML код в тег Body (с заменой)
     */
    @Override
    public void loadHtml(String html) {
        callJsFunction(String.format("loadHtml('%s')", html));
    }

    /**
     * Заменить триггерную ноду новым содержимым.
     * @param nodeId идентификатор триггерной ноды
     * @param html новое содержимое
     */
    @Override
    public void replaceTriggerNodeBlock(String nodeId, String html) {
        callJsFunction(String.format("replaceTriggerNodeBlock('%s', '%s')", nodeId, html));
    }

    /**
     * Заменить переменную вычисленным значением.
     * @param varId идентификатор переменной
     * @param html вычисленное значение
     */
    @Override
    public void replaceVariableBlock(String varId, String html) {
        callJsFunction(String.format("replaceVariableBlock('%s', '%s')", varId, html));
    }

    @Override
    /**
     * Сообщить View о том, что на бекенде все сломалось. :(
     */
    public void reportErrorState() {
        this.errorState = true;
        this.webView.loadUrl("file:///android_asset/error_screen.html");
    }
}
