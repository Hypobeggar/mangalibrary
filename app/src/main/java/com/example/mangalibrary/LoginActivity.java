package com.example.mangalibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    TextView signinText;
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, gso);

        // Sign in feature
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {// on click for Google sign in
            @Override
            public void onClick(View view) {

                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
                Log.i("Info","result code =" );
            }

        });
        // Sign out feature
        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });


    }
    private void signOut() {
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this, "Sign Out Successful", Toast.LENGTH_SHORT);
                        revokeAccess();
                        Intent i= new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }
    private void revokeAccess() {
        client.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Info","result code =" + resultCode);
        if (resultCode == -1){
            Task<GoogleSignInAccount> task =  GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acct = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Sign-in Successful", Toast.LENGTH_SHORT);
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            finish();

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

        // Change to sign out if already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signinText= findViewById(R.id.signin);
        signout= findViewById(R.id.signout);
        if(account != null){
            signInButton.setVisibility(View.INVISIBLE);
            signout.setVisibility(View.VISIBLE);
            signinText.setText("Sign Out");

        } else {
            signout.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            signinText.setText("Sign In");

        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // if(user != null){
         //   Intent i= new Intent(this,HomeActivity.class);

        //}
    }




}