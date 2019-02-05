package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.SigninBody;
import org.kidzonshock.acase.acase.Models.SigninResponseLawyer;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LawyerSignin extends AppCompatActivity {

    TextInputEditText inputEmailLawyer, inputPasswordLawyer;
    TextInputLayout layoutEmailLawyer,layoutPasswordLawyer;

    Button btnSigninLawyer, btnSignupLawyer;

    String email,password;

    Intent intentLogin;
    ACProgressFlower dialog;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_signin);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Sign-In");

        dialog = new ACProgressFlower.Builder(LawyerSignin.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        inputEmailLawyer = findViewById(R.id.inputEmailLawyer);
        inputPasswordLawyer = findViewById(R.id.inputPasswordLawyer);

        layoutEmailLawyer = findViewById(R.id.layoutEmailLawyer);
        layoutPasswordLawyer = findViewById(R.id.layoutPassLawyer);
        btnSigninLawyer = findViewById(R.id.btnSigninLawyer);
        btnSignupLawyer = findViewById(R.id.btnSignupLawyer);

        btnSigninLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                email = inputEmailLawyer.getText().toString();
                password = inputPasswordLawyer.getText().toString();
                if(validateForm(email,password)){
                    sendLoginRequest(email,password);
                }
            }
        });

        btnSignupLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                Intent signup = new Intent(LawyerSignin.this, SignupLawyer1.class);
                startActivity(signup);
            }
        });

        intentLogin = new Intent(LawyerSignin.this, Dashboard.class);
        if(PreferenceDataLawyer.getUserLoggedInStatus(LawyerSignin.this)){
            startActivity(intentLogin);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void sendLoginRequest(String email, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<SigninResponseLawyer> signinResponseCall = service.signinLawyer(new SigninBody(email,password));
        signinResponseCall.enqueue(new Callback<SigninResponseLawyer>() {
            @Override
            public void onResponse(Call<SigninResponseLawyer> call, Response<SigninResponseLawyer> response) {
                SigninResponseLawyer signinResponseLawyer = response.body();
                dialog.dismiss();
                if(!signinResponseLawyer.isError()){
//                  populating preference data to be saved in shared preference.
                    PreferenceDataLawyer.setLoggedInLawyerid(LawyerSignin.this, signinResponseLawyer.getLawyer());
                    PreferenceDataLawyer.setLoggedInFirstname(LawyerSignin.this, signinResponseLawyer.getFirst_name());
                    PreferenceDataLawyer.setLoggedInLastname(LawyerSignin.this, signinResponseLawyer.getLast_name());
                    PreferenceDataLawyer.setLoggedInEmail(LawyerSignin.this, signinResponseLawyer.getEmail());
                    PreferenceDataLawyer.setLoggedInPhone(LawyerSignin.this, signinResponseLawyer.getPhone());
                    PreferenceDataLawyer.setLoggedInCityOrMunicipality(LawyerSignin.this, signinResponseLawyer.getCityOrMunicipality());
                    PreferenceDataLawyer.setLoggedInOffice(LawyerSignin.this, signinResponseLawyer.getOffice());
                    PreferenceDataLawyer.setLoggedInAboutme(LawyerSignin.this, signinResponseLawyer.getAboutme());
                    PreferenceDataLawyer.setLoggedInProfilePicture(LawyerSignin.this, signinResponseLawyer.getProfile_pic());
                    PreferenceDataLawyer.setUserLoggedInStatus(LawyerSignin.this,true);
                    Toast.makeText(LawyerSignin.this, signinResponseLawyer.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(intentLogin);
                } else {
                    Toast.makeText(LawyerSignin.this, signinResponseLawyer.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SigninResponseLawyer> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(LawyerSignin.this, "Please check your internet connection" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String email, String password){
        boolean valid = true;
        if (TextUtils.isEmpty(password)) {
            layoutPasswordLawyer.setError("Required");
            layoutPasswordLawyer.requestFocus();
            valid = false;
        } else {
            layoutPasswordLawyer.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            layoutEmailLawyer.setError("Required");
            layoutEmailLawyer.requestFocus();
            valid = false;
        } else {
            layoutEmailLawyer.setError(null);
        }
        return valid;
    }

}
