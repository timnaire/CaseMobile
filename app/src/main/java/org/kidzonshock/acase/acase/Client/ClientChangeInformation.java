package org.kidzonshock.acase.acase.Client;

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
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.UpdateClientInfo;
import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientChangeInformation extends AppCompatActivity {

    String client_id, first_name,last_name,sex,phone,address;

    ACProgressFlower dialog;

    TextInputLayout layoutUpdateFirstClient, layoutUpdateLastClient, layoutUpdatePhoneClient, layoutUpdateAddressClient;
    TextInputEditText inputUpdateFirstClient, inputUpdateLastClient, inputUpdatePhoneClient, inputUpdateAddressClient;
    Spinner clientSex;
    Button btnSaveInfoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_change_information);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Information");

        dialog = new ACProgressFlower.Builder(ClientChangeInformation.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        client_id = PreferenceDataClient.getLoggedInClientid(ClientChangeInformation.this);
        first_name = PreferenceDataClient.getLoggedInFirstname(ClientChangeInformation.this);
        last_name = PreferenceDataClient.getLoggedInLastname(ClientChangeInformation.this);
        phone = PreferenceDataClient.getLoggedInPhone(ClientChangeInformation.this);
        address = PreferenceDataClient.getLoggedInAddress(ClientChangeInformation.this);
        sex = PreferenceDataClient.getLoggedInSex(ClientChangeInformation.this);
        Toast.makeText(this, "Client sex:"+sex, Toast.LENGTH_SHORT).show();
//        set all the id from views
        btnSaveInfoClient = findViewById(R.id.btnSaveInfoClient);

        layoutUpdateFirstClient = findViewById(R.id.layoutUpdateFirstClient);
        layoutUpdateLastClient = findViewById(R.id.layoutUpdateLastClient);
        layoutUpdatePhoneClient = findViewById(R.id.layoutUpdatePhoneClient);
        layoutUpdateAddressClient = findViewById(R.id.layoutUpdateAddressClient);
        clientSex = findViewById(R.id.spinnerClientSex);
        inputUpdateFirstClient = findViewById(R.id.inputUpdateFirstClient);
        inputUpdateLastClient = findViewById(R.id.inputUpdateLastClient);
        inputUpdatePhoneClient = findViewById(R.id.inputUpdatePhoneClient);
        inputUpdateAddressClient = findViewById(R.id.inputUpdateAddressClient);

        inputUpdateFirstClient.setText(first_name);
        inputUpdateLastClient.setText(last_name);
        inputUpdatePhoneClient.setText(phone);
        inputUpdateAddressClient.setText(address);
        clientSex.setSelection(getIndex(clientSex, sex));

        btnSaveInfoClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstname = inputUpdateFirstClient.getText().toString();
                String newLastname = inputUpdateLastClient.getText().toString();
                String newPhone = inputUpdatePhoneClient.getText().toString();
                String newAddress = inputUpdateAddressClient.getText().toString();
                String newSex = clientSex.getSelectedItem().toString();
                if(validateForm(newFirstname,newLastname,newPhone,newAddress)){
                    updateInfo(newFirstname,newLastname,newPhone,newAddress,newSex);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    public void updateInfo(final String newFirstname, final String newLastname, final String newPhone, final String newAddress, final String newSex){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> updateLawyerInfoCall = service.updateInfoClient(client_id,new UpdateClientInfo(newFirstname,newLastname,newPhone,newAddress,newSex));
        updateLawyerInfoCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    PreferenceDataClient.setLoggedInFirstname(ClientChangeInformation.this,newFirstname);
                    PreferenceDataClient.setLoggedInLastname(ClientChangeInformation.this,newLastname);
                    PreferenceDataClient.setLoggedInPhone(ClientChangeInformation.this,newPhone);
                    PreferenceDataClient.setLoggedInAddress(ClientChangeInformation.this,newAddress);
                    PreferenceDataClient.setLoggedInSex(ClientChangeInformation.this,newSex);
                    Toast.makeText(ClientChangeInformation.this, commonResponse.getMessage() , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClientChangeInformation.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ClientChangeInformation.this, "Unable to update your information, please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateForm(String first,String last, String phone, String address) {
        boolean valid = true;

        if (TextUtils.isEmpty(address)) {
            layoutUpdateAddressClient.setError("Required");
            layoutUpdateAddressClient.requestFocus();
            valid = false;
        } else {
            layoutUpdateAddressClient.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            layoutUpdatePhoneClient.setError("Required");
            layoutUpdatePhoneClient.requestFocus();
            valid = false;
        } else {
            layoutUpdatePhoneClient.setError(null);
        }

        if (TextUtils.isEmpty(last)) {
            layoutUpdateLastClient.setError("Required");
            layoutUpdateLastClient.requestFocus();
            valid = false;
        } else {
            layoutUpdateLastClient.setError(null);
        }

        if (TextUtils.isEmpty(first)) {
            layoutUpdateFirstClient.setError("Required");
            layoutUpdateFirstClient.requestFocus();
            valid = false;
        } else {
            layoutUpdateFirstClient.setError(null);
        }

        return valid;
    }
}
