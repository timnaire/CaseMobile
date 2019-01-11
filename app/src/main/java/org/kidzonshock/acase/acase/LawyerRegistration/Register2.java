package org.kidzonshock.acase.acase.LawyerRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.MainActivity;
import org.kidzonshock.acase.acase.Models.RegisterLawyer;
import org.kidzonshock.acase.acase.Models.RegisterResponse;
import org.kidzonshock.acase.acase.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register2 extends AppCompatActivity {

    Button btnRegSend;
    TextInputLayout layoutRegPhone, layoutRegOffice;
    TextInputEditText inputRegPhone, inputRegOffice;
    Spinner spinnerLawpractice, spinnerRegProvince;
    String firstname, lastname, email, phone, province, office, selectedProvince, selectedLawpractice;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        progressBar = findViewById(R.id.progressBar);

        btnRegSend = findViewById(R.id.btnRegSend);
        inputRegPhone = findViewById(R.id.inputRegPhone);
        inputRegOffice = findViewById(R.id.inputRegOffice);

        layoutRegPhone = findViewById(R.id.layoutRegPhone);
        layoutRegOffice = findViewById(R.id.layoutRegOffice);

        spinnerLawpractice = findViewById(R.id.spinnerLawpractice);
        spinnerRegProvince = findViewById(R.id.spinnerRegProvince);

        //getting the previous data intent
        Intent prev = getIntent();
        firstname = prev.getStringExtra("firstname");
        lastname = prev.getStringExtra("lastname");
        email = prev.getStringExtra("email");

        btnRegSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLawpractice = spinnerLawpractice.getSelectedItem().toString();
                selectedProvince = spinnerRegProvince.getSelectedItem().toString();
                phone = inputRegPhone.getText().toString();
                office = inputRegOffice.getText().toString();

                if(validateFormAgain(phone,office)){
                    sendPostRequest(firstname,lastname,email,phone,selectedProvince,office,selectedLawpractice);
                }
            }
        });

    }

    private void sendPostRequest(String firstname, String lastname, String email, String phone, String selectedProvince, String office, String lawpractice) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        progressBar.setVisibility(View.VISIBLE);
        Case service = retrofit.create(Case.class);

        Call<RegisterResponse> registerResponseCall = service.registerLawyer(new RegisterLawyer(firstname,lastname,email,phone, selectedProvince,office,lawpractice));
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && registerResponse.getCode().equals(200)){
                    Toast.makeText(Register2.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();
                    Intent toLogin = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(toLogin);
                } else {
                    Toast.makeText(Register2.this, registerResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(Register2.this, "Server response: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean validateFormAgain(String phone,String office) {
        boolean valid = true;

        if (TextUtils.isEmpty(phone)) {
            layoutRegPhone.setError("Required");
            layoutRegPhone.requestFocus();
            valid = false;
        } else {
            layoutRegPhone.setError(null);
        }

        if (TextUtils.isEmpty(office)) {
            layoutRegOffice.setError("Required");
            layoutRegOffice.requestFocus();
            valid = false;
        } else {
            layoutRegOffice.setError(null);
        }

        return valid;
    }

}
