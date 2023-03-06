package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.R;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.activities.BookPreviewActivity;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {

    private Context context;

    private BookView[] books;

    public BookRecyclerAdapter(Context ct, BookView[] bv) {
        context = ct;
        books = bv;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_row, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.title.setText(books[position].Title);
        holder.author.setText(books[position].Author);
        holder.genre.setText(books[position].Genre);
        Picasso.get().load(books[position].Cover).placeholder(R.drawable.default_cover)
                .error(R.drawable.default_cover).into(holder.cover);
        holder.bookLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookPreviewActivity.class);
            intent.putExtra("title", books[position].Title);
            intent.putExtra("author", books[position].Author);
            intent.putExtra("genre", books[position].Genre);
            intent.putExtra("description", books[position].Description);
            intent.putExtra("cover", books[position].Cover);
            intent.putExtra("id", books[position].Id);
            intent.putExtra("name", books[position].Name);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.length;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, genre;
        ImageView cover;
        ConstraintLayout bookLayout;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            genre = itemView.findViewById(R.id.bookGenre);
            cover = itemView.findViewById(R.id.bookCover);
            bookLayout = itemView.findViewById(R.id.bookLayout);
        }
    }
}
