package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.BookRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.LocationHandler;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views.BookView;

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
            for (Map<String, Object> data : bookData) {
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
//                        StorageReference islandRef = mStorageRef.child((String) data.get("name"));
//                        File localFile = null;
//                        try {
//                            localFile = File.createTempFile("book", "json");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        File finalLocalFile = localFile;
//                        islandRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
//                            try {
//                                System.out.println(finalLocalFile.getAbsolutePath());
//                                BookRepository.addBookFromServerToRepository(BookRepository.readFileAsString(finalLocalFile), (String) data.get("id"), getApplicationContext());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }

                    Intent intent = new Intent(this, ReadActivity.class);
                    intent.putExtra(ReadActivity.BOOK_ID_INTENT_EXTRA, (String) data.get("id"));
                    startActivity(intent);
                    finish();

//                        }).addOnFailureListener(exception -> {
//                            Toast.makeText(MainActivity.this, "Упс. Что-то пошло не так",
//                                    Toast.LENGTH_SHORT).show();
//                            System.out.println("MainActivity. 'addOnCompleteListener' failure.");
//                        });
                });
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
