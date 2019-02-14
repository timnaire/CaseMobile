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
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
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

    String lawyer_id, first_name,last_name,phone,office,cityOrMunicipality, aboutme,firm,sex;
    String[] law_practice;

    CheckBox chkBusinessLaw, chkCivilLaw,chkConstitutionalLaw,chkCriminalLaw,chkFamilyLaw,chkLaborLaw,chkTaxationLaw;
    ACProgressFlower dialog;

    TextInputLayout layoutUpdateFirst, layoutUpdateLast, layoutUpdatePhone, layoutUpdateCity,layoutUpdateOffice,layoutUpdateFirm, layoutUpdateSex;
    TextInputEditText inputUpdateFirst, inputUpdateLast, inputUpdatePhone, inputUpdateCity,inputUpdateOffice,inputUpdateFirm, inputUpdateSex;
    Button btnSaveInfo;
    EditText updateAboutme;

    private final String TAG = "ChangeInformation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);

        Intent prev = getIntent();
        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(ChangeInformation.this);
        first_name = PreferenceDataLawyer.getLoggedInFirstname(ChangeInformation.this);
        last_name = PreferenceDataLawyer.getLoggedInLastname(ChangeInformation.this);
        phone = PreferenceDataLawyer.getLoggedInPhone(ChangeInformation.this);
        cityOrMunicipality = PreferenceDataLawyer.getLoggedInCityOrMunicipality(ChangeInformation.this);
        office = PreferenceDataLawyer.getLoggedInOffice(ChangeInformation.this);
        aboutme = PreferenceDataLawyer.getLoggedInAboutme(ChangeInformation.this);
        firm = PreferenceDataLawyer.getLoggedInFirm(ChangeInformation.this);
        sex = PreferenceDataLawyer.getLoggedInSex(ChangeInformation.this);
        law_practice = prev.getStringArrayExtra("law_practice");

//        set all the id from views
        btnSaveInfo = findViewById(R.id.btnSaveInfo);

        layoutUpdateFirst = findViewById(R.id.layoutUpdateFirst);
        layoutUpdateLast = findViewById(R.id.layoutUpdateLast);
        layoutUpdatePhone = findViewById(R.id.layoutUpdatePhone);
        layoutUpdateCity = findViewById(R.id.layoutUpdateCity);
        layoutUpdateOffice = findViewById(R.id.layoutUpdateOffice);
        layoutUpdateFirm = findViewById(R.id.layoutUpdateFirm);
        layoutUpdateSex = findViewById(R.id.layoutUpdateSex);

        inputUpdateFirst = findViewById(R.id.inputUpdateFirst);
        inputUpdateLast = findViewById(R.id.inputUpdateLast);
        inputUpdatePhone = findViewById(R.id.inputUpdatePhone);
        inputUpdateCity = findViewById(R.id.inputUpdateCity);
        inputUpdateOffice = findViewById(R.id.inputUpdateOffice);
        updateAboutme = findViewById(R.id.updateAboutme);
        inputUpdateFirm = findViewById(R.id.inputUpdateFirm);
        inputUpdateSex = findViewById(R.id.inputUpdateSex);

        chkBusinessLaw = findViewById(R.id.chkBusinessLaw);
        chkCivilLaw = findViewById(R.id.chkCivilLaw);
        chkConstitutionalLaw = findViewById(R.id.chkConstitutionalLaw);
        chkCriminalLaw = findViewById(R.id.chkCriminalLaw);
        chkFamilyLaw = findViewById(R.id.chkFamilyLaw);
        chkLaborLaw = findViewById(R.id.chkLaborLaw);
        chkTaxationLaw = findViewById(R.id.chkTaxationLaw);
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
        inputUpdateFirm.setText(firm);
        inputUpdateSex.setText(sex);
        updateAboutme.setText(aboutme);

        for(int i=0; i < law_practice.length; i++){
            if(law_practice[i].equals(chkBusinessLaw.getText().toString())){
                chkBusinessLaw.setChecked(true);
            }else if(law_practice[i].equals(chkCivilLaw.getText().toString())){
                chkCivilLaw.setChecked(true);
            }else if(law_practice[i].equals(chkConstitutionalLaw.getText().toString())){
                chkConstitutionalLaw.setChecked(true);
            }else if(law_practice[i].equals(chkCriminalLaw.getText().toString())){
                chkCriminalLaw.setChecked(true);
            }else if(law_practice[i].equals(chkFamilyLaw.getText().toString())){
                chkFamilyLaw.setChecked(true);
            }else if(law_practice[i].equals(chkLaborLaw.getText().toString())){
                chkLaborLaw.setChecked(true);
            }else if(law_practice[i].equals(chkTaxationLaw.getText().toString())){
                chkTaxationLaw.setChecked(true);
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
                String newFirm = inputUpdateFirm.getText().toString();
                String newSex = inputUpdateSex.getText().toString();
                if(validateForm(newFirstname,newLastname,newPhone,newCity,newOffice,newAboutme,newFirm,newSex)){
                    ArrayList<String> newLaw_practice = new ArrayList<String>();
                    if(chkBusinessLaw.isChecked()){
                        newLaw_practice.add(chkBusinessLaw.getText().toString());
                    }
                    if(chkCivilLaw.isChecked()){
                        newLaw_practice.add(chkCivilLaw.getText().toString());
                    }
                    if(chkConstitutionalLaw.isChecked()){
                        newLaw_practice.add(chkConstitutionalLaw.getText().toString());
                    }
                    if(chkCriminalLaw.isChecked()){
                        newLaw_practice.add(chkCriminalLaw.getText().toString());
                    }
                    if(chkFamilyLaw.isChecked()){
                        newLaw_practice.add(chkFamilyLaw.getText().toString());
                    }
                    if(chkLaborLaw.isChecked()){
                        newLaw_practice.add(chkLaborLaw.getText().toString());
                    }
                    if(chkTaxationLaw.isChecked()){
                        newLaw_practice.add(chkTaxationLaw.getText().toString());
                    }

                    updateInfo(newFirstname,newLastname,newPhone,newCity,newOffice,newFirm,newSex,newAboutme, newLaw_practice);

                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void updateInfo(final String newFirstname, final String newLastname, final String newPhone, final String newCity, final String newOffice,final String newFirm, final String newSex, final String newAboutme, final ArrayList<String> newLaw_practice){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dialog.show();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> updateLawyerInfoCall = service.updateInfo(lawyer_id,new UpdateLawyerInfo(newFirstname,newLastname,newPhone,newCity,newOffice,newAboutme,newFirm,newSex,newLaw_practice));
        updateLawyerInfoCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if(!commonResponse.isError()){
                    PreferenceDataLawyer.setLoggedInFirstname(ChangeInformation.this,newFirstname);
                    PreferenceDataLawyer.setLoggedInLastname(ChangeInformation.this,newLastname);
                    PreferenceDataLawyer.setLoggedInPhone(ChangeInformation.this,newPhone);
                    PreferenceDataLawyer.setLoggedInCityOrMunicipality(ChangeInformation.this,newCity);
                    PreferenceDataLawyer.setLoggedInOffice(ChangeInformation.this,newOffice);
                    PreferenceDataLawyer.setLoggedInFirm(ChangeInformation.this,newFirm);
                    PreferenceDataLawyer.setLoggedInAboutme(ChangeInformation.this,newAboutme);
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

    private boolean validateForm(String first,String last, String phone, String city, String office,String aboutme, String firm, String sex) {
        boolean valid = true;

        if(aboutme.equals(null)){
            valid = false;
        }

        if (TextUtils.isEmpty(sex)) {
            layoutUpdateSex.setError("Required");
            layoutUpdateSex.requestFocus();
            valid = false;
        } else {
            layoutUpdateSex.setError(null);
        }

        if (TextUtils.isEmpty(firm)) {
            layoutUpdateFirm.setError("Required");
            layoutUpdateFirm.requestFocus();
            valid = false;
        } else {
            layoutUpdateFirm.setError(null);
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
