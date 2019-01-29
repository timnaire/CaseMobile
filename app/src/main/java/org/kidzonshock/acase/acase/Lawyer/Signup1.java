package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import org.kidzonshock.acase.acase.R;

public class Signup1 extends AppCompatActivity {

    Button btnRegisterNext;
    TextInputEditText inputRegFirstname, inputRegLastname, inputRegEmail;
    TextInputLayout layoutRegFirstname, layoutRegLastname, layoutRegEmail;
    String firstname, lastname, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        btnRegisterNext = findViewById(R.id.btnRegisterNext);
        inputRegFirstname = findViewById(R.id.inputRegFirstname);
        inputRegLastname = findViewById(R.id.inputRegLastname);
        inputRegEmail = findViewById(R.id.inputRegEmail);

        layoutRegFirstname = findViewById(R.id.layoutRegFirstname);
        layoutRegLastname = findViewById(R.id.layoutRegLastname);
        layoutRegEmail = findViewById(R.id.layoutRegEmail);

        btnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = inputRegFirstname.getText().toString();
                lastname = inputRegLastname.getText().toString();
                email = inputRegEmail.getText().toString();

                if(validateForm(firstname,lastname,email)){
                    Intent reg2 = new Intent(getApplicationContext(), Signup2.class);
                    reg2.putExtra("firstname", firstname);
                    reg2.putExtra("lastname", lastname);
                    reg2.putExtra("email", email);
                    startActivity(reg2);
                }
            }
        });

    }
    private boolean validateForm(String firstname,String lastname,String email) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            layoutRegEmail.setError("Required");
            layoutRegEmail.requestFocus();
            valid = false;
        } else {
            layoutRegEmail.setError(null);
        }

        if (TextUtils.isEmpty(lastname)) {
            layoutRegLastname.setError("Required");
            layoutRegLastname.requestFocus();
            valid = false;
        } else {
            layoutRegLastname.setError(null);
        }

        if (TextUtils.isEmpty(firstname)) {
            layoutRegFirstname.setError("Required");
            layoutRegFirstname.requestFocus();
            valid = false;
        } else {
            layoutRegFirstname.setError(null);
        }

        return valid;
    }
}

