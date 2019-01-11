package org.kidzonshock.acase.acase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.kidzonshock.acase.acase.LawyerRegistration.Register1;

public class MainActivity extends AppCompatActivity {

    TextInputEditText inputEmail, inputPassword;
    TextInputLayout layoutEmail,layoutPassword;
    Button btnRegister, btnLogin;
    Spinner spinnerRegChoice;
    String selectedRegChoice,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPassword = findViewById(R.id.layoutPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        spinnerRegChoice = findViewById(R.id.spinnerRegChoice);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();

                if(validateForm(email,password)){
                    sendLoginRequest(email,password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRegChoice = spinnerRegChoice.getSelectedItem().toString();
                if(selectedRegChoice.equals("Client")){
                    Toast.makeText(MainActivity.this, "You Selected Client registration!", Toast.LENGTH_SHORT).show();
                }else if(selectedRegChoice.equals("Lawyer")){
                    Intent reg1 = new Intent(getApplicationContext(), Register1.class );
                    startActivity(reg1);
                } else {
                    Toast.makeText(MainActivity.this, "Please choose either client or lawyer to signup", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendLoginRequest(String email, String password){

        Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show();

    }

    private boolean validateForm(String email, String password){
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            layoutEmail.setError("Required");
            layoutEmail.requestFocus();
            valid = false;
        } else {
            layoutEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            layoutPassword.setError("Required");
            layoutPassword.requestFocus();
            valid = false;
        } else {
            layoutPassword.setError(null);
        }

        return valid;
    }

}
