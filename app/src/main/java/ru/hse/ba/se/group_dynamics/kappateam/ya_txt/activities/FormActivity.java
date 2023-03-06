package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.PersonaQuiz;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.PersonaPersistence;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils.MultiSelectionSpinner;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initializeCityListeners();
        initializeAgeListeners();
        initializeLanguagesListeners();
        initializeMusicGenreListeners();
        initializeApproveProgressButtonListeners();
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getWindow().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * Вложенный класс предоставляющий кнопку с  progress bar.
     */
    public static class ProgressButton {
        private CardView cardView;
        private ConstraintLayout constraintLayout;
        private ProgressBar progressBar;
        private TextView textView;

        Animation fade_in;

        ProgressButton(Context context, View view) {
            cardView = view.findViewById(R.id.progressButtonCardView);
            constraintLayout = view.findViewById(R.id.progressButtonConstraintLayout);
            progressBar = view.findViewById(R.id.progressButtonProgressBar);
            textView = view.findViewById(R.id.progressButtonTextView);
            fade_in = AnimationUtils.loadAnimation(context, R.anim.progress_button_fade_in);
        }

        /**
         * Метод, который должен сработать при нажатии на кнопку.
         */
        void buttonActivated() {
            progressBar.setAnimation(fade_in);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setAnimation(fade_in);
            textView.setText("Подождите...");
        }

        /**
         * Метод, который должен сработать при восстановлении кнопки.
         */
        void buttonRestored() {
            constraintLayout.setBackgroundColor(cardView.getContext().getColor(R.color.colorPrimary));
            progressBar.setVisibility(View.GONE);
            textView.setText(R.string.approve);
        }

        /**
         * Метод, который должен сработать при ошибке ввода.
         */
        void buttonFailed() {
            constraintLayout.setBackgroundColor(cardView.getContext().getColor(R.color.red));
            progressBar.setVisibility(View.GONE);
            textView.setText("Ошибка");
        }

        /**
         * Метод, который должен сработать при верном вводе.
         */
        void buttonFinished() {
            constraintLayout.setBackgroundColor(cardView.getContext().getColor(R.color.green));
            progressBar.setVisibility(View.GONE);
            textView.setText("Готово");
        }
    }

    private boolean isCityTextCorrect = false;
    private boolean isAgeTextCorrect = false;

    /**
     * Инициализирует обработчики взаимодействия с полем текста о родном городе пользователя.
     */
    private void initializeCityListeners() {
        AutoCompleteTextView cityText = findViewById(R.id.cityText);
        String[] cities = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        cityText.setAdapter(adapter);
        cityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cityText.getText().toString().trim().length() <= 0) {
                    cityText.setError("Введите название города");
                    isCityTextCorrect = false;
                } else {
                    cityText.setError(null);
                    isCityTextCorrect = true;
                }
            }
        });
        cityText.setOnFocusChangeListener((v, hasFocus) -> {
            if (cityText.getText().toString().trim().length() <= 0) {
                cityText.setError("Введите название города");
                isCityTextCorrect = false;
            } else {
                cityText.setError(null);
                isCityTextCorrect = true;
            }
        });
    }

    /**
     * Инициализирует обработчики взаимодействия с полем текста о возрасте пользователя.
     */
    private void initializeAgeListeners() {
        EditText ageText = findViewById(R.id.ageText);
        ageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ageStr = ageText.getText().toString();
                if (ageStr.length() > 0) {
                    int age = Integer.parseInt(ageStr);
                    if (age < 0 || age > 100 || (ageStr.length() > 1 && ageStr.charAt(0) == '0')) {
                        ageText.setError("Введите возраст от 0 до 100 лет");
                        isAgeTextCorrect = false;
                    } else {
                        ageText.setError(null);
                        isAgeTextCorrect = true;
                    }
                } else {
                    ageText.setError("Введите возраст от 0 до 100 лет");
                    isAgeTextCorrect = false;
                }
            }
        });
        ageText.setOnFocusChangeListener((v, hasFocus) -> {
            String ageStr = ageText.getText().toString();
            if (ageStr.length() > 0) {
                int age = Integer.parseInt(ageStr);
                if (age < 0 || age > 100 || (ageStr.length() > 1 && ageStr.charAt(0) == '0')) {
                    ageText.setError("Введите возраст от 0 до 100 лет");
                    isAgeTextCorrect = false;
                } else {
                    ageText.setError(null);
                    isAgeTextCorrect = true;
                }
            } else {
                ageText.setError("Введите возраст от 0 до 100 лет");
                isAgeTextCorrect = false;
            }
        });
    }

    /**
     * Инициализирует обработчики взаимодействия с выподающим списком любимого языка.
     */
    private void initializeLanguagesListeners() {
        MultiSelectionSpinner languageSpinner = findViewById(R.id.languageSpinner);
        String[] languages = getResources().getStringArray(R.array.languages);
        languageSpinner.setItems(languages);
        languageSpinner.setSelection(0);
    }

    /**
     * Инициализирует обработчикивзаимодействия с выподающим списком любимого музыкального жанра.
     */
    private void initializeMusicGenreListeners() {
        MultiSelectionSpinner musicGenreSpinner = findViewById(R.id.musicGenreSpinner);
        String[] musicGenres = getResources().getStringArray(R.array.music_genre);
        musicGenreSpinner.setItems(musicGenres);
        musicGenreSpinner.setSelection(0);
    }

    /**
     * Инициализирует обработчики события нажатия на кнопку.
     */
    private void initializeApproveProgressButtonListeners() {
        View view = findViewById(R.id.approveProgressButton);
        view.setOnClickListener(v -> {
            ProgressButton progressButton = new ProgressButton(FormActivity.this, v);
            progressButton.buttonActivated();
            v.setEnabled(false);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (isAgeTextCorrect && isCityTextCorrect) {
                    progressButton.buttonFinished();
                    new Handler().postDelayed(() -> {
                        // Получаем все блоки
                        AutoCompleteTextView cityText = findViewById(R.id.cityText);
                        RadioButton genderRadioButton = findViewById(((RadioGroup)findViewById(R.id.genderRadioGroup)).getCheckedRadioButtonId());
                        EditText ageText = findViewById(R.id.ageText);
                        MultiSelectionSpinner musicGenreSpinner = findViewById(R.id.musicGenreSpinner);
                        MultiSelectionSpinner languageSpinner = findViewById(R.id.languageSpinner);
                        // Получаем от каждого блока значение
                        String town = String.valueOf(cityText.getText());
                        String gender = String.valueOf(genderRadioButton.getText());
                        String age = String.valueOf(ageText.getText());
                        ArrayList<String> languages = languageSpinner.getSelectedItemsList();
                        ArrayList<String> genres = musicGenreSpinner.getSelectedItemsList();
                        PersonaQuiz personaQuiz =  new PersonaQuiz(town, gender, age, genres, languages);
                        try {
                            PersonaPersistence.addOrUpdatePersonaQuiz(personaQuiz, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(FormActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }, 1000);
                } else {
                    progressButton.buttonFailed();
                    new Handler().postDelayed(() -> {
                        progressButton.buttonRestored();
                        v.setEnabled(true);
                    }, 1000);
                }
            }, 1500);
        });
    }
}
