package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Spinner;

import org.kidzonshock.acase.acase.R;

public class SignupLawyer1 extends AppCompatActivity {

    Button btnRegisterNext;
    TextInputEditText inputRegFirstnameLawyer, inputRegLastnameLawyer, inputRegEmailLawyer, inputRegPhoneLawyer, inputRegRollnoLawyer;
    TextInputLayout layoutRegFirstnameLawyer, layoutRegLastnameLawyer, layoutRegEmailLawyer, layoutRegPhoneLawyer, layoutRegRollnoLawyer;
    String firstname, lastname, email, phone, sex, rollno;
    Spinner spinnerSex;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_lawyer1);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Sign-Up - Step 1");

        btnRegisterNext = findViewById(R.id.btnRegisterNext);
        spinnerSex = findViewById(R.id.spinnerSex);

        inputRegFirstnameLawyer = findViewById(R.id.inputRegFirstnameLawyer);
        inputRegLastnameLawyer = findViewById(R.id.inputRegLastnameLawyer);
        inputRegEmailLawyer = findViewById(R.id.inputRegEmailLawyer);
        inputRegPhoneLawyer = findViewById(R.id.inputRegPhoneLawyer);
        inputRegRollnoLawyer = findViewById(R.id.inputRegRollnoLawyer);

        layoutRegFirstnameLawyer = findViewById(R.id.layoutRegFirstnameLawyer);
        layoutRegLastnameLawyer = findViewById(R.id.layoutRegLastnameLawyer);
        layoutRegEmailLawyer = findViewById(R.id.layoutRegEmailLawyer);
        layoutRegPhoneLawyer = findViewById(R.id.layoutRegPhoneLawyer);
        layoutRegRollnoLawyer = findViewById(R.id.layoutRegRollnoLawyer);

        btnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                sex = spinnerSex.getSelectedItem().toString();
                firstname = inputRegFirstnameLawyer.getText().toString();
                lastname = inputRegLastnameLawyer.getText().toString();
                email = inputRegEmailLawyer.getText().toString();
                phone = inputRegPhoneLawyer.getText().toString();
                rollno = inputRegRollnoLawyer.getText().toString();

                if(validateForm(firstname,lastname,email,phone,rollno)){
                    Intent reg2 = new Intent(SignupLawyer1.this, SignupLawyer2.class);
                    reg2.putExtra("firstname", firstname);
                    reg2.putExtra("lastname", lastname);
                    reg2.putExtra("email", email);
                    reg2.putExtra("phone", phone);
                    reg2.putExtra("rollno",rollno);
                    reg2.putExtra("sex", sex);
                    startActivity(reg2);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean validateForm(String firstname,String lastname,String email, String phone, String rollno) {
        boolean valid = true;

        if (TextUtils.isEmpty(rollno)) {
            layoutRegRollnoLawyer.setError("Required");
            layoutRegRollnoLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegRollnoLawyer.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            layoutRegPhoneLawyer.setError("Required");
            layoutRegPhoneLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegPhoneLawyer.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            layoutRegEmailLawyer.setError("Required");
            layoutRegEmailLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegEmailLawyer.setError(null);
        }

        if (TextUtils.isEmpty(lastname)) {
            layoutRegLastnameLawyer.setError("Required");
            layoutRegLastnameLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegLastnameLawyer.setError(null);
        }

        if (TextUtils.isEmpty(firstname)) {
            layoutRegFirstnameLawyer.setError("Required");
            layoutRegFirstnameLawyer.requestFocus();
            valid = false;
        } else {
            layoutRegFirstnameLawyer.setError(null);
        }

        return valid;
    }
}

