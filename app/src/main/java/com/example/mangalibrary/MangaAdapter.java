package com.example.mangalibrary;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder> implements View.OnClickListener {
    DatabaseReference dbref;
    List<PopularManga> mangas;
    OnItemClickListener onItemClickListener;
    public MangaAdapter(DatabaseReference databaseReference) {
        dbref = databaseReference;
        mangas = new ArrayList<>();

        DatabaseReference mangaRef = dbref.child("Manga");
        mangaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //
                mangas.clear(); // prevent duplicates of the list
                // create manga object using data from Firebase Realtime database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageUrl = snapshot.child("cover").getValue(String.class);
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    PopularManga manga = new PopularManga(imageUrl, title,description);
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
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }
    public interface OnItemClickListener {
        void onItemClick(PopularManga manga);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    //@Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            PopularManga manga = mangas.get(position);
            onItemClickListener.onItemClick(manga);
            Intent i = new Intent(view.getContext(), DetailActivity.class);
            i.putExtra("title", manga.getTitle());
            i.putExtra("imageUrl", manga.getImageUrl());
            i.putExtra("description", manga.getDescription());
            view.getContext().startActivity(i);
        }
    }
    public class PopularManga {
        private String imageUrl;
        private String title;
        private String description;

        public PopularManga(String imageUrl, String title, String description) {
            this.imageUrl = imageUrl;
            this.title = title;
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
