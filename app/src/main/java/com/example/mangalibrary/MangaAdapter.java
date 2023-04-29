package com.example.mangalibrary;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangalibrary.MangaViewHolder;
import com.example.mangalibrary.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder> {
    DatabaseReference dbref;
    List<PopularManga> mangas;

    public MangaAdapter(DatabaseReference databaseReference) {
        dbref = databaseReference;
        mangas = new ArrayList<>();

        DatabaseReference mangaRef = dbref.child("Manga");
        mangaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mangas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageUrl = snapshot.child("cover").getValue(String.class);
                    String myString = snapshot.child("title").getValue(String.class);
                    PopularManga manga = new PopularManga(imageUrl, myString);
                    mangas.add(manga);
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Info", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_popular, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        PopularManga manga = mangas.get(position);
        holder.title.setText(manga.getTitle());
        Glide.with(holder.itemView.getContext()).load(manga.getImageUrl()).placeholder(R.drawable.blankavatar).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }

    public class PopularManga {
        private String imageUrl;
        private String title;

        public PopularManga(String imageUrl, String title) {
            this.imageUrl = imageUrl;
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTitle() {
            return title;
        }
    }
}
