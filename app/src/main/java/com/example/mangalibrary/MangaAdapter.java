package com.example.mangalibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangalibrary.MangaViewHolder;
import com.example.mangalibrary.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder> {
    //Context context;
    DatabaseReference dbref;
    List<String> images;

    public MangaAdapter(DatabaseReference databaseReference) {
        dbref = databaseReference;
        images = new ArrayList<>();

    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_popular, parent, false);
        return new MangaViewHolder(view);
        //new MangaViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_popular,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
