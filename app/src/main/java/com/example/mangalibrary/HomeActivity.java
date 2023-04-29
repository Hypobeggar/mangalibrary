package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        avatar = findViewById(R.id.avatar);
        popularRecycler= findViewById(R.id.popularManga);
        recentRecycler= findViewById(R.id.recentManga);
        dbref= FirebaseDatabase.getInstance().getReference();

        popularRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularRecycler.setAdapter(new MangaAdapter(dbref));
        recentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recentRecycler.setAdapter(new MangaAdapter(dbref));
    }

    @Override
    protected void onResume() {
        super.onResume();
        avatar = findViewById(R.id.avatar);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Uri personPhoto = account.getPhotoUrl();
            Glide.with(this).load(personPhoto).into(avatar); //  Get the google avatar image
        } else {
            avatar.setImageResource(R.drawable.blankavatarsmall);
        }
    }

    public void clickAvatar(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}