package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signUp(String email, String password) {
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Регистрация не удалась :(",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(RegistrationActivity.this, "Пустые данные :(",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void registrationButtonClicked(View view) {
        EditText email = findViewById(R.id.newEmailText);
        EditText password = findViewById(R.id.newPasswordText);
        signUp(email.getText().toString(), password.getText().toString());
    }
}
