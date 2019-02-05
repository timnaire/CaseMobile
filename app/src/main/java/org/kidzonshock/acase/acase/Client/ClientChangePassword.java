package org.kidzonshock.acase.acase.Client;

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
import org.kidzonshock.acase.acase.Models.UpdatePassword;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientChangePassword extends AppCompatActivity {

    String client_id;
    TextInputLayout layoutCurrentPassClient,layoutNewPassClient,layoutConfirmPassClient;
    TextInputEditText inputCurrentPassClient,inputNewPassClient,inputConfirmPassClient;
    Button btnChangePasswordClient;

    ACProgressFlower dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_change_password);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");

        dialog = new ACProgressFlower.Builder(ClientChangePassword.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();


//        get client id from SharedPreference
        client_id = PreferenceDataClient.getLoggedInClientid(ClientChangePassword.this);
//      text input layout
        layoutCurrentPassClient = findViewById(R.id.layoutCurrentPassClient);
        layoutNewPassClient = findViewById(R.id.layoutNewPassClient);
        layoutConfirmPassClient = findViewById(R.id.layoutConfirmPassClient);
//      text input edit text
        inputCurrentPassClient = findViewById(R.id.inputCurrentPassClient);
        inputNewPassClient = findViewById(R.id.inputNewPassClient);
        inputConfirmPassClient = findViewById(R.id.inputConfirmPassClient);

        btnChangePasswordClient = findViewById(R.id.btnChangePasswordClient);

        btnChangePasswordClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentpass, newpass, confirmpass;
                currentpass = inputCurrentPassClient.getText().toString();
                newpass = inputNewPassClient.getText().toString();
                confirmpass = inputConfirmPassClient.getText().toString();
                if(validateForm(currentpass,newpass,confirmpass)){
                    changePassword(currentpass,newpass,confirmpass);

                }
            }
        });
    }

    public void changePassword(String currentpass, String newpass, String confirmpass){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.updatePasswordClient(client_id,new UpdatePassword(currentpass,newpass,confirmpass));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    Toast.makeText(ClientChangePassword.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    inputConfirmPassClient.setText("");
                    inputNewPassClient.setText("");
                    inputCurrentPassClient.setText("");
                }else{
                    Toast.makeText(ClientChangePassword.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ClientChangePassword.this, "Unable to change password, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String currentpass, String newpass, String confirmpass){
        boolean valid = true;
        if (TextUtils.isEmpty(confirmpass)) {
            layoutConfirmPassClient.setError("Required");
            layoutConfirmPassClient.requestFocus();
            valid = false;
        } else {
            layoutConfirmPassClient.setError(null);
        }

        if (TextUtils.isEmpty(newpass)) {
            layoutNewPassClient.setError("Required");
            layoutNewPassClient.requestFocus();
            valid = false;
        } else {
            layoutNewPassClient.setError(null);
        }

        if (TextUtils.isEmpty(currentpass)) {
            layoutCurrentPassClient.setError("Required");
            layoutCurrentPassClient.requestFocus();
            valid = false;
        } else {
            layoutCurrentPassClient.setError(null);
        }
        return valid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
