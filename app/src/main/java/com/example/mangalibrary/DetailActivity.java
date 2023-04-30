package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    ImageView cover;
    TextView title1, description;
    ListView chapterList;
    DatabaseReference dbref;
    List<ChapterListAdapter> chapter;
    ArrayAdapter<ChapterListAdapter> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        cover= findViewById(R.id.detail_cover);
        title1= findViewById(R.id.title1);
        description= findViewById(R.id.description);
        chapterList= findViewById(R.id.chapterlist);
        dbref= FirebaseDatabase.getInstance().getReference();

        chapter = new ArrayList<>();
         adapter = new ArrayAdapter<>(this, R.layout.chapter_layout, chapter);
        chapterList.setAdapter(adapter);


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
    public class Chapter{
        private String chapNum;

        public Chapter(String chapNum) {
            this.chapNum = chapNum;
        }
    }
}