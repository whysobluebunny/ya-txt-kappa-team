package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.BookRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.LocationHandler;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;

public class AutonomousMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> idBooks = null;
        List<Map<String, Object>> bookData = null;
        try {
            bookData = BookRepository.getBookInfo(getApplicationContext());
            idBooks = BookRepository.getSavedBooksIDs(getApplicationContext());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        if (idBooks != null && !idBooks.isEmpty()) {
            ArrayList<String> finalIdBooks = idBooks;
            for (Map<String, Object> data : bookData) {
                if (finalIdBooks.contains((String) data.get("id"))) {
                    LinearLayout view = findViewById(R.id.mybookLayout);
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    View item = inflater.inflate(R.layout.book_column, null, false);
                    TextView title = item.findViewById(R.id.bookTitle);
                    title.setText((String) data.get("title"));
                    ImageView cover = item.findViewById(R.id.bookCover);
                    Picasso.get().load((String) data.get("cover")).placeholder(R.drawable.default_cover)
                            .error(R.drawable.default_cover).into(cover);
                    view.addView(item);
                    item.setTag(data.get("name"));
                    item.setOnClickListener(v -> {
                        Intent intent = new Intent(this, ReadActivity.class);
                        intent.putExtra(ReadActivity.BOOK_ID_INTENT_EXTRA, (String) data.get("id"));
                        startActivity(intent);
                        finish();
                    });
                }
            }
            LocationHandler appLocationManager = new LocationHandler(AutonomousMainActivity.this, Resources.INSTANCE.geoLocation);
            Resources.INSTANCE.createLightResource(this);
        }
    }

    public void libraryButtonClicked(View view) {
        Intent intent = new Intent(this, BookLibraryActivity.class);
        startActivity(intent);
    }

    public void SettingsButtonClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
