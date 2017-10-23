 package com.ensardz.fsw.yupivoyv2.screens;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ensardz.fsw.yupivoyv2.R;
import com.google.android.gms.common.SignInButton;

 public class MainMenu extends AppCompatActivity {

    private SignInButton googleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        init();
    }

    public void init(){
        googleSignIn = (SignInButton) findViewById(R.id.google_sign_in_button);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
