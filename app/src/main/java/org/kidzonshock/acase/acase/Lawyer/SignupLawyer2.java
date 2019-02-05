package org.kidzonshock.acase.acase.Lawyer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.MainActivity;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.SignupLawyer;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupLawyer2 extends AppCompatActivity {

    Button btnRegSend;
    TextInputLayout layoutRegOfficeLawyer, layoutRegPassLawyer, layoutRegConfirmLawyer;
    TextInputEditText inputRegOfficeLawyer, inputRegPassLawyer, inputRegConfirmLawyer;
    Spinner spinnerLawpractice, spinnerRegProvince;
    String firstname, lastname, email, phone, office, cityOrMunicipality, selectedLawpractice, password, confirm;
    ACProgressFlower dialog;
    Context context;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_lawyer2);
        context = this;
        dialog = new ACProgressFlower.Builder(SignupLawyer2.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Sign-Up - Step 2");

        btnRegSend = findViewById(R.id.btnRegLawyer);
        inputRegOfficeLawyer = findViewById(R.id.inputRegOfficeLawyer);
        layoutRegPassLawyer = findViewById(R.id.layoutRegPassLawyer);
        layoutRegConfirmLawyer = findViewById(R.id.layoutRegConfirmLawyer);

        layoutRegOfficeLawyer = findViewById(R.id.layoutRegOfficeLawyer);
        inputRegPassLawyer = findViewById(R.id.inputRegPassLawyer);
        inputRegConfirmLawyer = findViewById(R.id.inputRegConfirmLawyer);

        spinnerLawpractice = findViewById(R.id.spinnerLawpracticeLawyer);
        spinnerRegProvince = findViewById(R.id.spinnerRegProvinceLawyer);

        //getting the previous data intent
        Intent prev = getIntent();
        firstname = prev.getStringExtra("firstname");
        lastname = prev.getStringExtra("lastname");
        email = prev.getStringExtra("email");
        phone = prev.getStringExtra("phone");

        btnRegSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                selectedLawpractice = spinnerLawpractice.getSelectedItem().toString();
                cityOrMunicipality = spinnerRegProvince.getSelectedItem().toString();

                office = inputRegOfficeLawyer.getText().toString();
                password = inputRegPassLawyer.getText().toString();
                confirm = inputRegConfirmLawyer.getText().toString();
                
                if(password.equals(confirm)){
                    if(validateFormAgain(office,password,confirm)){
                        sendPostRequest(firstname,lastname,email,phone,cityOrMunicipality,office,selectedLawpractice,password,confirm);
                    }    
                } else {
                    Toast.makeText(SignupLawyer2.this, "Confirmation password does not match, please try again.", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void sendPostRequest(String firstname, String lastname, String email, String phone, String cityOrMunicipality, String office, String lawpractice, String password, String confirm) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dialog.show();
        Case service = retrofit.create(Case.class);

        Call<CommonResponse> commonResponseCall = service.signupLawyer(new SignupLawyer(firstname,lastname,email,phone, cityOrMunicipality,office,lawpractice,password,confirm));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse signupResponse = response.body();
                dialog.dismiss();
                if(!signupResponse.isError()){
                    Toast.makeText(SignupLawyer2.this, signupResponse.getMessage(), Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(SignupLawyer2.this,LawyerSignin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    Toast.makeText(SignupLawyer2.this, signupResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();

                Toast.makeText(SignupLawyer2.this, "Unable to sign up Case Account with error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean validateFormAgain(String office, String password, String confirm) {
        boolean valid = true;

        if (TextUtils.isEmpty(confirm)) {
            layoutRegConfirmLawyer.setError("Required");
            layoutRegConfirmLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegConfirmLawyer.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            layoutRegPassLawyer.setError("Required");
            layoutRegPassLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegPassLawyer.setError(null);
        }

        if (TextUtils.isEmpty(office)) {
            layoutRegOfficeLawyer.setError("Required");
            layoutRegOfficeLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegOfficeLawyer.setError(null);
        }

        return valid;
    }

}
