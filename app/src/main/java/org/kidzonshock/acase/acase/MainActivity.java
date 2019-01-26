package org.kidzonshock.acase.acase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressConstant;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Lawyer.Dashboard;
import org.kidzonshock.acase.acase.Lawyer.Signup1;
import org.kidzonshock.acase.acase.Models.SigninLawyer;
import org.kidzonshock.acase.acase.Models.SigninResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextInputEditText inputEmail, inputPassword;
    TextInputLayout layoutEmail,layoutPassword;
    Button btnRegister, btnLogin;
    Spinner spinnerRegChoice;
    String selectedRegChoice,email,password;
    ACProgressFlower dialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ACProgressFlower.Builder(MainActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();
        context = this;
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPassword = findViewById(R.id.layoutPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        spinnerRegChoice = findViewById(R.id.spinnerRegChoice);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                if(validateForm(email,password)){
                    sendLoginRequest(email,password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRegChoice = spinnerRegChoice.getSelectedItem().toString();
                if(selectedRegChoice.equals("Client")){
                    Toast.makeText(MainActivity.this, "You Selected Client registration!", Toast.LENGTH_SHORT).show();
                }else if(selectedRegChoice.equals("Lawyer")){
                    Intent reg1 = new Intent(getApplicationContext(), Signup1.class );
                    startActivity(reg1);
                } else {
                    Toast.makeText(MainActivity.this, "Please choose either client or lawyer to signup", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendLoginRequest(String email, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<SigninResponse> signinResponseCall = service.signinLawyer(new SigninLawyer(email,password));
        signinResponseCall.enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                SigninResponse signinResponse = response.body();
                dialog.dismiss();
                if(response.isSuccessful() && signinResponse.isError()){
                    Toast.makeText(MainActivity.this, signinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, signinResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String email, String password){
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            layoutEmail.setError("Required");
            layoutEmail.requestFocus();
            valid = false;
        } else {
            layoutEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            layoutPassword.setError("Required");
            layoutPassword.requestFocus();
            valid = false;
        } else {
            layoutPassword.setError(null);
        }

        return valid;
    }

}
