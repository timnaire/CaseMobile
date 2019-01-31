package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreferenceData;
import org.kidzonshock.acase.acase.Models.UpdateEmail;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeEmail extends AppCompatActivity {

    String lawyer_id;
    TextInputLayout layoutCurrent,layoutNewEmail, layoutPass;
    TextInputEditText inputCurrent,inputNewEmail,inputPass;
    Button btnChangeEmail;

    ACProgressFlower dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        dialog = new ACProgressFlower.Builder(ChangeEmail.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Email");

//        get lawyer id from SharedPrefence
        lawyer_id = PreferenceData.getLoggedInLawyerid(getApplication());

//        Text input layout
        layoutCurrent = findViewById(R.id.layoutCurrent);
        layoutNewEmail = findViewById(R.id.layoutNewEmail);
        layoutPass = findViewById(R.id.layoutPass);
//        Text input edit text
        inputCurrent = findViewById(R.id.inputCurrent);
        inputNewEmail = findViewById(R.id.inputNewEmail);
        inputPass = findViewById(R.id.inputPass);

        btnChangeEmail = findViewById(R.id.btnChangeEmail);

        Intent prev = getIntent();
        String email = prev.getStringExtra("email");

        inputCurrent.setText(email);

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current,newemail,pass;
                current = inputCurrent.getText().toString();
                newemail = inputNewEmail.getText().toString();
                pass = inputPass.getText().toString();
                if(validateForm(current,newemail,pass)){
                    changeEmail(current,newemail,pass);

                }

            }
        });

    }

    public void changeEmail(String current, final String newemail, String pass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.updateEmail(lawyer_id,new UpdateEmail(current,newemail,pass));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    PreferenceData.setLoggedInEmail(getApplication(),newemail);
                    Toast.makeText(ChangeEmail.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    inputCurrent.setText("");
                    inputNewEmail.setText("");
                    inputPass.setText("");
                }else{
                    Toast.makeText(ChangeEmail.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ChangeEmail.this, "Unable to change your email, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean validateForm(String current, String newemail, String pass){
        boolean valid = true;
        if (TextUtils.isEmpty(pass)) {
            layoutPass.setError("Required");
            layoutPass.requestFocus();
            valid = false;
        } else {
            layoutPass.setError(null);
        }

        if (TextUtils.isEmpty(newemail)) {
            layoutNewEmail.setError("Required");
            layoutNewEmail.requestFocus();
            valid = false;
        } else {
            layoutNewEmail.setError(null);
        }

        if (TextUtils.isEmpty(current)) {
            layoutCurrent.setError("Required");
            layoutCurrent.requestFocus();
            valid = false;
        } else {
            layoutCurrent.setError(null);
        }
        return valid;
    }
}
