package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views.BookRecyclerAdapter;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views.BookView;

public class BookLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_library);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.bookRecyclerView);
        ArrayList<BookView> books = new ArrayList<>();

        db.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            books.add(new BookView((String) data.get("title"),
                                                   (String) data.get("author"),
                                                   (String) data.get("genre"),
                                                   (String) data.get("description"),
                                                   (String) data.get("cover"),
                                                   (String) data.get("name"),
                                                   (String) data.get("id")));
                        }

                        BookRecyclerAdapter bookAdapter = new BookRecyclerAdapter(this, books.toArray(new BookView[0]));
                        recyclerView.setAdapter(bookAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));

                    } else {
                        Toast.makeText(BookLibraryActivity.this, "Упс. Что-то пошло не так",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
