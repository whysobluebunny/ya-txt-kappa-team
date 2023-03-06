package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Класс-активити для запроса предоставить доступ к данным о местоположении.
 */
public class PermissionActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (locationAccepted) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        }
    }

    /**
     * Обработчик события нажатия на кнопку предоставления доступа к данным о местоположении.
     *
     * @param view элемент экрана, по которому происходит нажатие
     */
    public void onPermissionButtonClick(View view) {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }
}
