package org.kidzonshock.acase.acase.Client;

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
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.UpdateEmail;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientChangeEmail extends AppCompatActivity {

    TextInputLayout layoutCurrentClient,layoutNewEmailClient, layoutPassClient;
    TextInputEditText inputCurrentClient,inputNewEmailClient,inputPassClient;
    Button btnChangeEmailClient;

    String client_id,email;
    ACProgressFlower dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_change_email);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Email");

        dialog = new ACProgressFlower.Builder(ClientChangeEmail.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        client_id = PreferenceDataClient.getLoggedInClientid(ClientChangeEmail.this);

        layoutCurrentClient = findViewById(R.id.layoutCurrentClient);
        layoutNewEmailClient = findViewById(R.id.layoutNewEmailClient);
        layoutPassClient = findViewById(R.id.layoutPassClient);
//        Text input edit text
        inputCurrentClient = findViewById(R.id.inputCurrentClient);
        inputNewEmailClient = findViewById(R.id.inputNewEmailClient);
        inputPassClient = findViewById(R.id.inputPassClient);

        btnChangeEmailClient = findViewById(R.id.btnChangeEmailClient);

        String email = PreferenceDataClient.getLoggedInEmail(ClientChangeEmail.this);

        inputCurrentClient.setText(email);

        btnChangeEmailClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current,newemail,pass;
                current = inputCurrentClient.getText().toString();
                newemail = inputNewEmailClient.getText().toString();
                pass = inputPassClient.getText().toString();
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
        Call<CommonResponse> commonResponseCall = service.updateEmailClient(client_id,new UpdateEmail(current,newemail,pass));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    PreferenceDataLawyer.setLoggedInEmail(ClientChangeEmail.this,newemail);
                    Toast.makeText(ClientChangeEmail.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    inputNewEmailClient.setText("");
                    inputPassClient.setText("");
                    inputCurrentClient.setText("");
                }else{
                    Toast.makeText(ClientChangeEmail.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ClientChangeEmail.this, "Unable to change your email, please try again.", Toast.LENGTH_SHORT).show();
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
            layoutPassClient.setError("Required");
            layoutPassClient.requestFocus();
            valid = false;
        } else {
            layoutPassClient.setError(null);
        }

        if (TextUtils.isEmpty(newemail)) {
            layoutNewEmailClient.setError("Required");
            layoutNewEmailClient.requestFocus();
            valid = false;
        } else {
            layoutNewEmailClient.setError(null);
        }

        if (TextUtils.isEmpty(current)) {
            layoutCurrentClient.setError("Required");
            layoutCurrentClient.requestFocus();
            valid = false;
        } else {
            layoutCurrentClient.setError(null);
        }
        return valid;
    }

}
