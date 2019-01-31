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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreferenceData;
import org.kidzonshock.acase.acase.Models.UpdateLawyerInfo;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeInformation extends AppCompatActivity {

    String lawyer_id, first_name,last_name,phone,office,cityOrMunicipality, aboutme;
    String[] law_practice;

    CheckBox chkBankruptcy, chkBusiness,chkCommercial,chkCriminal,chkEmployment,chkFamily,chkImmigration,chkInjury,chkRealEstate,chkWills;
    ACProgressFlower dialog;

    TextInputLayout layoutUpdateFirst, layoutUpdateLast, layoutUpdatePhone, layoutUpdateCity,layoutUpdateOffice;
    TextInputEditText inputUpdateFirst, inputUpdateLast, inputUpdatePhone, inputUpdateCity,inputUpdateOffice;
    Button btnSaveInfo;
    EditText updateAboutme;

    private final String TAG = "ChangeInformation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);

        Intent prev = getIntent();
        lawyer_id = PreferenceData.getLoggedInLawyerid(getApplicationContext());
        first_name = PreferenceData.getLoggedInFirstname(getApplicationContext());
        last_name = PreferenceData.getLoggedInLastname(getApplicationContext());
        phone = PreferenceData.getLoggedInPhone(getApplicationContext());
        cityOrMunicipality = PreferenceData.getLoggedInCityOrMunicipality(getApplicationContext());
        office = PreferenceData.getLoggedInOffice(getApplicationContext());
        aboutme = PreferenceData.getLoggedInAboutme(getApplicationContext());
        law_practice = prev.getStringArrayExtra("law_practice");

//        set all the id from views
        btnSaveInfo = findViewById(R.id.btnSaveInfo);

        layoutUpdateFirst = findViewById(R.id.layoutUpdateFirst);
        layoutUpdateLast = findViewById(R.id.layoutUpdateLast);
        layoutUpdatePhone = findViewById(R.id.layoutUpdatePhone);
        layoutUpdateCity = findViewById(R.id.layoutUpdateCity);
        layoutUpdateOffice = findViewById(R.id.layoutUpdateOffice);

        inputUpdateFirst = findViewById(R.id.inputUpdateFirst);
        inputUpdateLast = findViewById(R.id.inputUpdateLast);
        inputUpdatePhone = findViewById(R.id.inputUpdatePhone);
        inputUpdateCity = findViewById(R.id.inputUpdateCity);
        inputUpdateOffice = findViewById(R.id.inputUpdateOffice);
        updateAboutme = findViewById(R.id.updateAboutme);

        chkBankruptcy = findViewById(R.id.chkBankruptcy);
        chkBusiness = findViewById(R.id.chkBusiness);
        chkCommercial = findViewById(R.id.chkCommercial);
        chkCriminal = findViewById(R.id.chkCriminal);
        chkEmployment = findViewById(R.id.chkEmployment);
        chkFamily = findViewById(R.id.chkFamily);
        chkImmigration = findViewById(R.id.chkImmigration);
        chkInjury = findViewById(R.id.chkInjury);
        chkRealEstate = findViewById(R.id.chkRealEstate);
        chkWills = findViewById(R.id.chkWills);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Information");

        dialog = new ACProgressFlower.Builder(ChangeInformation.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        inputUpdateFirst.setText(first_name);
        inputUpdateLast.setText(last_name);
        inputUpdatePhone.setText(phone);
        inputUpdateCity.setText(cityOrMunicipality);
        inputUpdateOffice.setText(office);
        updateAboutme.setText(aboutme);

        for(int i=0; i < law_practice.length; i++){
            if(law_practice[i].equals(chkBankruptcy.getText().toString())){
                chkBankruptcy.setChecked(true);
            }else if(law_practice[i].equals(chkBusiness.getText().toString())){
                chkBusiness.setChecked(true);
            }else if(law_practice[i].equals(chkCommercial.getText().toString())){
                chkCommercial.setChecked(true);
            }else if(law_practice[i].equals(chkCriminal.getText().toString())){
                chkCriminal.setChecked(true);
            }else if(law_practice[i].equals(chkEmployment.getText().toString())){
                chkEmployment.setChecked(true);
            }else if(law_practice[i].equals(chkFamily.getText().toString())){
                chkFamily.setChecked(true);
            }else if(law_practice[i].equals(chkImmigration.getText().toString())){
                chkImmigration.setChecked(true);
            }else if(law_practice[i].equals(chkInjury.getText().toString())){
                chkInjury.setChecked(true);
            }else if(law_practice[i].equals(chkRealEstate.getText().toString())){
                chkRealEstate.setChecked(true);
            }else if(law_practice[i].equals(chkWills.getText().toString())){
                chkWills.setChecked(true);
            }
        }


        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstname = inputUpdateFirst.getText().toString();
                String newLastname = inputUpdateLast.getText().toString();
                String newPhone = inputUpdatePhone.getText().toString();
                String newCity = inputUpdateCity.getText().toString();
                String newOffice = inputUpdateOffice.getText().toString();
                String newAboutme = updateAboutme.getText().toString();
                if(validateForm(newFirstname,newLastname,newPhone,newCity,newOffice,newAboutme)){
                    ArrayList<String> newLaw_practice = new ArrayList<String>();
                    if(chkBankruptcy.isChecked()){
                        newLaw_practice.add(chkBankruptcy.getText().toString());
                    }
                    if(chkBusiness.isChecked()){
                        newLaw_practice.add(chkBusiness.getText().toString());
                    }
                    if(chkCommercial.isChecked()){
                        newLaw_practice.add(chkCommercial.getText().toString());
                    }
                    if(chkCriminal.isChecked()){
                        newLaw_practice.add(chkCriminal.getText().toString());
                    }
                    if(chkEmployment.isChecked()){
                        newLaw_practice.add(chkEmployment.getText().toString());
                    }
                    if(chkFamily.isChecked()){
                        newLaw_practice.add(chkFamily.getText().toString());
                    }
                    if(chkImmigration.isChecked()){
                        newLaw_practice.add(chkImmigration.getText().toString());
                    }
                    if(chkInjury.isChecked()){
                        newLaw_practice.add(chkInjury.getText().toString());
                    }
                    if(chkRealEstate.isChecked()){
                        newLaw_practice.add(chkRealEstate.getText().toString());
                    }
                    if(chkWills.isChecked()){
                        newLaw_practice.add(chkWills.getText().toString());
                    }

                    if(first_name.equals(newFirstname) && last_name.equals(newLastname) && phone.equals(newPhone) && cityOrMunicipality.equals(newCity) && office.equals(newOffice) ){
                        Toast.makeText(ChangeInformation.this, "No changes has been made.", Toast.LENGTH_SHORT).show();
                    }{
                        updateInfo(newFirstname,newLastname,newPhone,newCity,newOffice,newAboutme, newLaw_practice);
                    }

                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void updateInfo(final String newFirstname, final String newLastname, final String newPhone, final String newCity, final String newOffice, final String newAboutme, final ArrayList<String> newLaw_practice){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> updateLawyerInfoCall = service.updateInfo(lawyer_id,new UpdateLawyerInfo(newFirstname,newLastname,newPhone,newCity,newOffice,newAboutme,newLaw_practice));
        updateLawyerInfoCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    PreferenceData.setLoggedInFirstname(ChangeInformation.this,newFirstname);
                    PreferenceData.setLoggedInLastname(ChangeInformation.this,newLastname);
                    PreferenceData.setLoggedInPhone(ChangeInformation.this,newPhone);
                    PreferenceData.setLoggedInCityOrMunicipality(ChangeInformation.this,newCity);
                    PreferenceData.setLoggedInOffice(ChangeInformation.this,newOffice);
                    PreferenceData.setLoggedInAboutme(ChangeInformation.this,newAboutme);
                    Toast.makeText(ChangeInformation.this, commonResponse.getMessage() , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangeInformation.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ChangeInformation.this, "Unable to update your information, please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateForm(String first,String last, String phone, String city, String office,String aboutme) {
        boolean valid = true;

        if(aboutme.equals(null)){
            valid = false;
        }

        if (TextUtils.isEmpty(office)) {
            layoutUpdateOffice.setError("Required");
            layoutUpdateOffice.requestFocus();
            valid = false;
        } else {
            layoutUpdateOffice.setError(null);
        }

        if (TextUtils.isEmpty(city)) {
            layoutUpdateCity.setError("Required");
            layoutUpdateCity.requestFocus();
            valid = false;
        } else {
            layoutUpdateCity.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            layoutUpdatePhone.setError("Required");
            layoutUpdatePhone.requestFocus();
            valid = false;
        } else {
            layoutUpdatePhone.setError(null);
        }

        if (TextUtils.isEmpty(last)) {
            layoutUpdateLast.setError("Required");
            layoutUpdateLast.requestFocus();
            valid = false;
        } else {
            layoutUpdateLast.setError(null);
        }

        if (TextUtils.isEmpty(first)) {
            layoutUpdateFirst.setError("Required");
            layoutUpdateFirst.requestFocus();
            valid = false;
        } else {
            layoutUpdateFirst.setError(null);
        }

        return valid;
    }

}
