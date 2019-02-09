package org.kidzonshock.acase.acase.Client;

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
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.SignupClient;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupClient2 extends AppCompatActivity {

    Button btnSignupClientNow;

    TextInputEditText inputRegPhoneClient, inputRegAddressClient, inputRegPassClient,inputRegConfirmClient;
    TextInputLayout layoutRegPhoneClient, layoutRegAddressClient, layoutRegPassClient,layoutRegConfirmClient;
    ACProgressFlower dialog;
    String firstname,lastname,email,phone,address,password,confirm;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_client2);

        context = this;
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Sign-Up - Step 2");

        dialog = new ACProgressFlower.Builder(SignupClient2.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();


        btnSignupClientNow = findViewById(R.id.btnSignupClientNow);

        inputRegPhoneClient = findViewById(R.id.inputRegPhoneClient);
        inputRegAddressClient = findViewById(R.id.inputRegAddressClient);
        inputRegPassClient = findViewById(R.id.inputRegPassClient);
        inputRegConfirmClient = findViewById(R.id.inputRegConfirmClient);

        layoutRegPhoneClient = findViewById(R.id.layoutRegPhoneClient);
        layoutRegAddressClient = findViewById(R.id.layoutRegAddressClient);
        layoutRegPassClient = findViewById(R.id.layoutRegPassClient);
        layoutRegConfirmClient = findViewById(R.id.layoutRegConfirmClient);

        Intent prev = getIntent();
        firstname = prev.getStringExtra("firstname");
        lastname = prev.getStringExtra("lastname");
        email = prev.getStringExtra("email");

        btnSignupClientNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                phone = inputRegPhoneClient.getText().toString();
                address = inputRegAddressClient.getText().toString();
                password = inputRegPassClient.getText().toString();
                confirm = inputRegConfirmClient.getText().toString();
                if(password.equals(confirm)){
                    if(validateForm(phone,address,password,confirm)){
                        sendPostRequest(firstname,lastname,email,phone,address,password,confirm);
                    }
                }else{
                    Toast.makeText(SignupClient2.this, "Confirmation password does not match, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void sendPostRequest(String firstname, String lastname, String email, String phone, String address, String password, String confirm){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.signupClient(new SignupClient(firstname,lastname,email,phone,address,password,confirm));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if(!commonResponse.isError()){
                    Toast.makeText(SignupClient2.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(SignupClient2.this,ClientSignin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    Toast.makeText(SignupClient2.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(SignupClient2.this, "Unable to sign up Case Account with error "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String phone,String address,String password, String confirm) {
        boolean valid = true;

        if (TextUtils.isEmpty(confirm)) {
            layoutRegConfirmClient.setError("Required");
            layoutRegConfirmClient.requestFocus();
            valid = false;
        } else {
            layoutRegConfirmClient.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            layoutRegPassClient.setError("Required");
            layoutRegPassClient.requestFocus();
            valid = false;
        } else {
            layoutRegPassClient.setError(null);
        }

        if (TextUtils.isEmpty(address)) {
            layoutRegAddressClient.setError("Required");
            layoutRegAddressClient.requestFocus();
            valid = false;
        } else {
            layoutRegAddressClient.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            layoutRegPhoneClient.setError("Required");
            layoutRegPhoneClient.requestFocus();
            valid = false;
        } else {
            layoutRegPhoneClient.setError(null);
        }

        return valid;
    }
}
