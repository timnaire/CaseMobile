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

import org.kidzonshock.acase.acase.R;

public class SignupLawyer1 extends AppCompatActivity {

    Button btnRegisterNext;
    TextInputEditText inputRegFirstnameLawyer, inputRegLastnameLawyer, inputRegEmailLawyer, inputRegPhoneLawyer;
    TextInputLayout layoutRegFirstnameLawyer, layoutRegLastnameLawyer, layoutRegEmailLawyer, layoutRegPhoneLawyer;
    String firstname, lastname, email, phone;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_lawyer1);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Sign-Up - Step 1");

        btnRegisterNext = findViewById(R.id.btnRegisterNext);
        inputRegFirstnameLawyer = findViewById(R.id.inputRegFirstnameLawyer);
        inputRegLastnameLawyer = findViewById(R.id.inputRegLastnameLawyer);
        inputRegEmailLawyer = findViewById(R.id.inputRegEmailLawyer);
        inputRegPhoneLawyer = findViewById(R.id.inputRegPhoneLawyer);

        layoutRegFirstnameLawyer = findViewById(R.id.layoutRegFirstnameLawyer);
        layoutRegLastnameLawyer = findViewById(R.id.layoutRegLastnameLawyer);
        layoutRegEmailLawyer = findViewById(R.id.layoutRegEmailLawyer);
        layoutRegPhoneLawyer = findViewById(R.id.layoutRegPhoneLawyer);

        btnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                firstname = inputRegFirstnameLawyer.getText().toString();
                lastname = inputRegLastnameLawyer.getText().toString();
                email = inputRegEmailLawyer.getText().toString();
                phone = inputRegPhoneLawyer.getText().toString();

                if(validateForm(firstname,lastname,email,phone)){
                    Intent reg2 = new Intent(SignupLawyer1.this, SignupLawyer2.class);
                    reg2.putExtra("firstname", firstname);
                    reg2.putExtra("lastname", lastname);
                    reg2.putExtra("email", email);
                    reg2.putExtra("phone", phone);
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

    private boolean validateForm(String firstname,String lastname,String email, String phone) {
        boolean valid = true;

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

