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

import org.kidzonshock.acase.acase.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class ChangeInformation extends AppCompatActivity {

    String lawyer_id, first_name,last_name,phone,office,cityOrMunicipality, aboutme;


    String[] available_practice = {"Family","Employment","Criminal Defense","Business","Personal Injury","Immigration","Bankruptcy","Wills, Trust, and Estates", "Real Estate","Commercial Law"};
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
        lawyer_id = prev.getStringExtra("lawyer_id");
        first_name = prev.getStringExtra("first_name");
        last_name = prev.getStringExtra("last_name");
        phone = prev.getStringExtra("phone");
        cityOrMunicipality = prev.getStringExtra("cityOrMunicipality");
        office = prev.getStringExtra("office");
        aboutme = prev.getStringExtra("aboutme");

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
        getSupportActionBar().setTitle("Back");

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

        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm(first_name,last_name,phone,cityOrMunicipality,office)){
                    Toast.makeText(ChangeInformation.this, "Testing saving Info !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateForm(String first,String last, String phone, String city, String office) {
        boolean valid = true;

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
