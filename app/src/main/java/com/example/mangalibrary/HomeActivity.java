package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeActivity extends AppCompatActivity {
    ImageButton avatar;
    RecyclerView popularRecycler, recentRecycler;
    DatabaseReference dbref;
    MangaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        avatar = findViewById(R.id.avatar);
        popularRecycler= findViewById(R.id.popularManga);
        recentRecycler= findViewById(R.id.recentManga);
        dbref= FirebaseDatabase.getInstance().getReference();

        adapter= new MangaAdapter(dbref,"Manga");
        adapter.setOnItemClickListener(manga -> {

        });

        popularRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setAdapter(adapter);


        recentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recentRecycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        avatar = findViewById(R.id.avatar);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Uri profilePicture = account.getPhotoUrl();
            Glide.with(this).load(profilePicture).into(avatar); //  Get the google avatar image
        } else {
            avatar.setImageResource(R.drawable.blankavatarsmall);
        }
    }

    public void clickAvatar(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}