package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeActivity extends AppCompatActivity {
    ImageButton avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        avatar = findViewById(R.id.avatar);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            avatar.setBackground(getDrawable(R.drawable.cloud)); // TODO: Get the google avatar image
        }
    }
    public void clickAvatar(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}