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

public class Signup2 extends AppCompatActivity {

    Button btnRegSend;
    TextInputLayout layoutRegPhone, layoutRegOffice;
    TextInputEditText inputRegPhone, inputRegOffice;
    Spinner spinnerLawpractice, spinnerRegProvince;
    String firstname, lastname, email, phone, province, office, selectedProvince, selectedLawpractice;
    ACProgressFlower dialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        context = this;
        dialog = new ACProgressFlower.Builder(Signup2.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

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

        dialog.show();
        Case service = retrofit.create(Case.class);

        Call<CommonResponse> commonResponseCall = service.signupLawyer(new SignupLawyer(firstname,lastname,email,phone, selectedProvince,office,lawpractice));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse signupResponse = response.body();
                dialog.dismiss();
                if(response.isSuccessful() && signupResponse.isError()){
                    Toast.makeText(Signup2.this, signupResponse.getMessage(), Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(Signup2.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    Toast.makeText(Signup2.this, signupResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();

                Toast.makeText(Signup2.this, "Server response: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean validateFormAgain(String phone,String office) {
        boolean valid = true;

        if (TextUtils.isEmpty(office)) {
            layoutRegOffice.setError("Required");
            layoutRegOffice.requestFocus();
            valid = false;
        } else {
            layoutRegOffice.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            layoutRegPhone.setError("Required");
            layoutRegPhone.requestFocus();
            valid = false;
        } else {
            layoutRegPhone.setError(null);
        }

        return valid;
    }

}
