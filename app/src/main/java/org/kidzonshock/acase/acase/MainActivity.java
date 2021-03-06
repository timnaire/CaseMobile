package org.kidzonshock.acase.acase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import org.kidzonshock.acase.acase.Client.ClientNavigation;
import org.kidzonshock.acase.acase.Client.ClientSignin;
import org.kidzonshock.acase.acase.Lawyer.Dashboard;
import org.kidzonshock.acase.acase.Lawyer.LawyerSignin;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;

public class MainActivity extends AppCompatActivity {

    Button btnLawyerSignin, btnClientSignin;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();



        btnClientSignin = findViewById(R.id.btnClientSignin);
        btnLawyerSignin = findViewById(R.id.btnLawyerSignin);

        btnLawyerSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                Intent lawyer = new Intent(getApplicationContext(), LawyerSignin.class);
                startActivity(lawyer);
            }
        });

        btnClientSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                Intent client = new Intent(getApplicationContext(), ClientSignin.class);
                startActivity(client);
            }
        });

        Intent intentLogin;
        if(PreferenceDataLawyer.getUserLoggedInStatus(MainActivity.this)){
            intentLogin = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intentLogin);
        }
        if(PreferenceDataClient.getUserLoggedInStatus(MainActivity.this)){
            intentLogin = new Intent(MainActivity.this, ClientNavigation.class);
            startActivity(intentLogin);
        }

    }

}
