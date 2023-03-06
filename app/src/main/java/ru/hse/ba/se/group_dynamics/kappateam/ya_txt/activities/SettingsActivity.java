package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
    }

    public void logoutButtonClicked(View view) {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void questionnaireButtonClicked(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }
}
