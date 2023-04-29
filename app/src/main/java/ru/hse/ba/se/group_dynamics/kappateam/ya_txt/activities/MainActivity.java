package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.BookRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.ServiceLocator;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthRepository;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core.auth.AuthUser;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.LocationHandler;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.resources.Resources;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views.BookRecyclerAdapter;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views.BookView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Класс-активити, основной в приложении.
 */
public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 2;

    private final String id = "19f6c76d-08b3-47b8-98cc-93b6d366d2b4";//UUID.randomUUID().toString();

    private final AuthRepository auth = ServiceLocator.getInstance().getAuthRepository();

    private StorageReference mStorageRef;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkConnection())
            switchToAutonomousMode();

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        CheckUserAuthorization();

        ArrayList<BookView> books = new ArrayList<>();

        if (savedInstanceState == null && !checkPermission()) {
            Intent intent = new Intent(this, PermissionActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        LocationHandler appLocationManager = new LocationHandler(MainActivity.this, Resources.INSTANCE.geoLocation);
        Resources.INSTANCE.createLightResource(this);

        AuthUser user = auth.getCurrentUser();
        if (user != null) {
            ArrayList<String> idBooks = null;
            try {
                idBooks = BookRepository.getSavedBooksIDs(getApplicationContext());
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> bookData = new ArrayList<>();
            if (idBooks != null && !idBooks.isEmpty()) {
                TextView titleView = findViewById(R.id.mybooksTitle);
                titleView.setText("Читаю сейчас");
                ArrayList<String> finalIdBooks = idBooks;
                db.collection("books")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> data = document.getData();
                                    System.out.println("Book loaded");
                                    bookData.add(data);
                                    if (finalIdBooks.contains((String) data.get("id"))) {
                                        books.add(new BookView((String) data.get("title"),
                                                (String) data.get("author"),
                                                (String) data.get("genre"),
                                                (String) data.get("description"),
                                                (String) data.get("cover"),
                                                (String) data.get("name"),
                                                (String) data.get("id")));

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
                                            StorageReference islandRef = mStorageRef.child((String) data.get("name"));
                                            File localFile = null;
                                            try {
                                                localFile = File.createTempFile("book", "json");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            File finalLocalFile = localFile;
                                            islandRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                                try {
                                                    BookRepository.addBookFromServerToRepository(BookRepository.readFileAsString(finalLocalFile), (String) data.get("id"), getApplicationContext());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                Intent intent = new Intent(this, ReadActivity.class);
                                                intent.putExtra(ReadActivity.BOOK_ID_INTENT_EXTRA, (String) data.get("id"));
                                                startActivity(intent);
                                                finish();

                                            }).addOnFailureListener(exception -> {
                                                Toast.makeText(MainActivity.this, "Упс. Что-то пошло не так",
                                                        Toast.LENGTH_SHORT).show();
                                                System.out.println("MainActivity. 'addOnCompleteListener' failure.");
                                            });
                                        });
                                    }
                                }
                                try {
                                    BookRepository.saveBookInfo(bookData, getApplicationContext());
                                    System.out.println(BookRepository.getBookInfo(getApplicationContext()).get(0).get("title"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            } else {
                TextView titleView = findViewById(R.id.mybooksTitle);
                titleView.setText("Пусто...");
            }
        }
    }


    private void CheckUserAuthorization() {
        AuthUser user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public void switchToAutonomousMode() {
        Intent intent = new Intent(this, AutonomousMainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            View view = findViewById(R.id.activity_main);
            Snackbar.make(view, "Доступ к данным о местоположении предоставлен.", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Проверка предоставления данных о местоположении.
     *
     * @return возвращает true, если доступ предоставлен, иначе false
     */
    private boolean checkPermission() {
        int currPerm = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return currPerm == PackageManager.PERMISSION_GRANTED;
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

