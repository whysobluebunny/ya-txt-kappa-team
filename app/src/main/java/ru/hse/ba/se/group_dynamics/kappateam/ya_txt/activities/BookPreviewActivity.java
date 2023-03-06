package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import androidx.appcompat.app.AppCompatActivity;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.BookRepository;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class BookPreviewActivity extends AppCompatActivity {

    TextView title, author, genre, description;
    ImageView cover;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_preview);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        title = findViewById(R.id.bookTitle);
        author = findViewById(R.id.bookAuthor);
        genre = findViewById(R.id.bookGenre);
        description = findViewById(R.id.bookDescription);
        cover = findViewById(R.id.bookCover);

        setExtraData();
    }

    private void setExtraData() {
        title.setText(getIntent().getStringExtra("title"));
        author.setText(getIntent().getStringExtra("author"));
        genre.setText(getIntent().getStringExtra("genre"));
        description.setText(getIntent().getStringExtra("description"));
        Picasso.get().load(getIntent().getStringExtra("cover")).placeholder(R.drawable.default_cover)
                .error(R.drawable.default_cover).into(cover);
    }

    public void readButtonClicked(View view) throws IOException {
        StorageReference islandRef = mStorageRef.child(getIntent().getStringExtra("name"));
        File localFile = File.createTempFile("book", "json");
        islandRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            try {
                BookRepository.addBookFromServerToRepository(BookRepository.readFileAsString(localFile), getIntent().getStringExtra("id"), getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, ReadActivity.class);
            intent.putExtra(ReadActivity.BOOK_ID_INTENT_EXTRA, getIntent().getStringExtra("id"));
            try {
                BookRepository.addSavedBookId(getIntent().getStringExtra("id"), getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startActivity(intent);
            finish();

        }).addOnFailureListener(exception -> {
            Toast.makeText(BookPreviewActivity.this, "Упс. Что-то пошло не так",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
