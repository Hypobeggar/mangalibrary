package com.example.mangalibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient client ;
    SignInButton signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {// on click for Google sign in
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1234){
            Task<GoogleSignInAccount> task =  GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acct = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG);
                        }

                    }
                });

            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for already authenticated user
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // TODO: Update UI

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent i= new Intent(this,HomeActivity.class);

        }
    }



    private void signIn() {
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }
}