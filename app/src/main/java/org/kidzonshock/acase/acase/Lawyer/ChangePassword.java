package org.kidzonshock.acase.acase.Lawyer;

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
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.UpdatePassword;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassword extends AppCompatActivity {

    String lawyer_id;
    TextInputLayout layoutCurrentPass,layoutNewPass,layoutConfirmPass;
    TextInputEditText inputCurrentPass,inputNewpass,inputConfirmPass;
    Button btnChangePassword;

    ACProgressFlower dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        dialog = new ACProgressFlower.Builder(ChangePassword.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");

//      get lawyer id from SharedPreference
        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getApplication());
//      text input layout
        layoutCurrentPass = findViewById(R.id.layoutCurrentPass);
        layoutNewPass = findViewById(R.id.layoutNewPass);
        layoutConfirmPass = findViewById(R.id.layoutConfirmPass);
//      text input edit text
        inputCurrentPass = findViewById(R.id.inputCurrentPass);
        inputNewpass = findViewById(R.id.inputNewPass);
        inputConfirmPass = findViewById(R.id.inputConfirmPass);

        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentpass, newpass, confirmpass;
                currentpass = inputCurrentPass.getText().toString();
                newpass = inputNewpass.getText().toString();
                confirmpass = inputConfirmPass.getText().toString();
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
        Call<CommonResponse> commonResponseCall = service.updatePassword(lawyer_id,new UpdatePassword(currentpass,newpass,confirmpass));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    Toast.makeText(ChangePassword.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    inputCurrentPass.setText("");
                    inputConfirmPass.setText("");
                    inputNewpass.setText("");
                }else{
                    Toast.makeText(ChangePassword.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ChangePassword.this, "Unable to change password, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean validateForm(String currentpass, String newpass, String confirmpass){
        boolean valid = true;
        if (TextUtils.isEmpty(confirmpass)) {
            layoutConfirmPass.setError("Required");
            layoutConfirmPass.requestFocus();
            valid = false;
        } else {
            layoutConfirmPass.setError(null);
        }

        if (TextUtils.isEmpty(newpass)) {
            layoutNewPass.setError("Required");
            layoutNewPass.requestFocus();
            valid = false;
        } else {
            layoutNewPass.setError(null);
        }

        if (TextUtils.isEmpty(currentpass)) {
            layoutCurrentPass.setError("Required");
            layoutCurrentPass.requestFocus();
            valid = false;
        } else {
            layoutCurrentPass.setError(null);
        }
        return valid;
    }

}
