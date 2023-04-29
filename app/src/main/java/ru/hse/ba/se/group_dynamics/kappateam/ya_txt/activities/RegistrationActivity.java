package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.ServiceLocator;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthException;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.EmailPasswordAuth;

public class RegistrationActivity extends AppCompatActivity {

    private final AuthRepository auth = ServiceLocator.getInstance().getAuthRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    private void signUp(String email, String password) {
        try {
            auth.register(new EmailPasswordAuth(email, password)).addOnCompleteListener(this, task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this,
                            "Регистрация не удалась :(\nОшибка: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            });

        } catch (AuthException e) {
            Toast.makeText(RegistrationActivity.this, "Пустые данные :(\n"+e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void registrationButtonClicked(View view) {
        EditText email = findViewById(R.id.newEmailText);
        EditText password = findViewById(R.id.newPasswordText);

        signUp(email.getText().toString(), password.getText().toString());
    }
}
