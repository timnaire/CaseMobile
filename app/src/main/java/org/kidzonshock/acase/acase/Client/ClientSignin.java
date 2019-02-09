package org.kidzonshock.acase.acase.Client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.AddFCMToken;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.SigninBody;
import org.kidzonshock.acase.acase.Models.SigninResponseClient;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSignin extends AppCompatActivity {

    TextInputEditText inputEmailClient, inputPasswordClient;
    TextInputLayout layoutEmailClient,layoutPasswordClient;

    Button btnSigninClient, btnSignupClient;

    String email,password, token;

    Intent intentLogin;
    ACProgressFlower dialog;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);
    private final String TAG = "ClientSignin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signin);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Sign-In");

        dialog = new ACProgressFlower.Builder(ClientSignin.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        inputEmailClient = findViewById(R.id.inputEmailClient);
        inputPasswordClient = findViewById(R.id.inputPasswordClient);

        layoutEmailClient = findViewById(R.id.layoutEmailClient);
        layoutPasswordClient = findViewById(R.id.layoutPasswordClient);
        btnSigninClient = findViewById(R.id.btnSigninClient);
        btnSignupClient = findViewById(R.id.btnSignupClient);

        btnSigninClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                email = inputEmailClient.getText().toString();
                password = inputPasswordClient.getText().toString();
                if(validateForm(email,password)){
                    // Get token
                    // [START retrieve_current_token]
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    // Get new Instance ID token
                                    token = task.getResult().getToken();
                                    PreferenceDataLawyer.setLoggedInFcmToken(ClientSignin.this,token);
                                }
                            });
                    // [END retrieve_current_token]
                    sendLoginRequest(email,password);
                }
            }
        });

        btnSignupClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                Intent reg = new Intent(ClientSignin.this, SignupClient1.class);
                startActivity(reg);
            }
        });

        intentLogin = new Intent(ClientSignin.this, ClientNavigation.class);
        if(PreferenceDataClient.getUserLoggedInStatus(ClientSignin.this)){
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
        Call<SigninResponseClient> signinResponseClientCall = service.signinClient(new SigninBody(email,password));
        signinResponseClientCall.enqueue(new Callback<SigninResponseClient>() {
            @Override
            public void onResponse(Call<SigninResponseClient> call, Response<SigninResponseClient> response) {
                SigninResponseClient signinResponseClient = response.body();
                dialog.dismiss();
<<<<<<< HEAD
                PreferenceDataClient.setLoggedInClientid(ClientSignin.this, signinResponseClient.getClient());
                PreferenceDataClient.setLoggedInFirstname(ClientSignin.this, signinResponseClient.getFirst_name());
                PreferenceDataClient.setLoggedInLastname(ClientSignin.this, signinResponseClient.getLast_name());
                PreferenceDataClient.setLoggedInEmail(ClientSignin.this, signinResponseClient.getEmail());
                PreferenceDataClient.setLoggedInPhone(ClientSignin.this, signinResponseClient.getPhone());
                PreferenceDataClient.setLoggedInAddress(ClientSignin.this, signinResponseClient.getAdress());
                PreferenceDataLawyer.setLoggedInProfilePicture(ClientSignin.this, signinResponseClient.getProfile_pic());
                PreferenceDataLawyer.setUserLoggedInStatus(ClientSignin.this,true);
                saveFCMToken(PreferenceDataClient.getLoggedInClientid(ClientSignin.this));
                Toast.makeText(ClientSignin.this, signinResponseClient.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(intentLogin);
=======
                if(!signinResponseClient.isError()){
                    PreferenceDataClient.setLoggedInClientid(ClientSignin.this, signinResponseClient.getClient());
                    PreferenceDataClient.setLoggedInFirstname(ClientSignin.this, signinResponseClient.getFirst_name());
                    PreferenceDataClient.setLoggedInLastname(ClientSignin.this, signinResponseClient.getLast_name());
                    PreferenceDataClient.setLoggedInEmail(ClientSignin.this, signinResponseClient.getEmail());
                    PreferenceDataClient.setLoggedInPhone(ClientSignin.this, signinResponseClient.getPhone());
                    PreferenceDataClient.setLoggedInAddress(ClientSignin.this, signinResponseClient.getAdress());
                    PreferenceDataLawyer.setLoggedInProfilePicture(ClientSignin.this, signinResponseClient.getProfile_pic());
                    PreferenceDataLawyer.setUserLoggedInStatus(ClientSignin.this,true);
                    Toast.makeText(ClientSignin.this, signinResponseClient.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(intentLogin);
                } else {
                    Toast.makeText(ClientSignin.this, signinResponseClient.getMessage(), Toast.LENGTH_SHORT).show();
                }
>>>>>>> 8cfc75d15815cbbe9f51535eb0b1c3b77691a740
            }

            @Override
            public void onFailure(Call<SigninResponseClient> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ClientSignin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveFCMToken(String client_id){
        if(PreferenceDataClient.getLoggedInFcmToken(ClientSignin.this).equals("")){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Case.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Case service = retrofit.create(Case.class);
            Call<ResponseBody> responseBodyCall = service.lawyer_fcm_token(client_id,new AddFCMToken(token));
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ClientSignin.this, "FCM token saved!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    private boolean validateForm(String email, String password){
        boolean valid = true;
        if (TextUtils.isEmpty(password)) {
            layoutPasswordClient.setError("Required");
            layoutPasswordClient.requestFocus();
            valid = false;
        } else {
            layoutPasswordClient.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            layoutEmailClient.setError("Required");
            layoutEmailClient.requestFocus();
            valid = false;
        } else {
            layoutEmailClient.setError(null);
        }
        return valid;
    }

}
