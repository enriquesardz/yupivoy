package com.example.ensardz.yupivoyenrique.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ensardz.yupivoyenrique.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;

    private Context mContext;

    private FirebaseAuth auth;
    private List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;

        initComponentes();
    }

    public void initComponentes() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        providers = new ArrayList<>();

        startLoginProviders();
    }

    public void startLoginProviders() {

        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //user logged in
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN);
        }

    } //end startLoginProviders

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //user logged in
                Toast.makeText(mContext, auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            }
            else{
                //User not authenticated
                Toast.makeText(mContext, "Not authenticated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
