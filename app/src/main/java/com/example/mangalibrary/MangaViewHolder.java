package com.example.mangalibrary;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MangaViewHolder extends RecyclerView.ViewHolder {
    ImageView cover;
    TextView title;

    public MangaViewHolder(@NonNull View itemView) {
        super(itemView);
        cover= itemView.findViewById(R.id.manga_cover);
        title= itemView.findViewById(R.id.manga_title);
    }

}
