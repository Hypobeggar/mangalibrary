package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView cover;
    TextView title1, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        cover= findViewById(R.id.detail_cover);
        title1= findViewById(R.id.title1);
        description= findViewById(R.id.description);

        Intent i = getIntent();
        if (i != null) {
            String title = i.getStringExtra("title");
            String imageUrl = i.getStringExtra("imageUrl");
            String description1 = i.getStringExtra("description");
            title1.setText(title);
            description.setText(description1);
            Glide.with(this).load(imageUrl).placeholder(R.drawable.blankavatar).into(cover);
        }
    }
}