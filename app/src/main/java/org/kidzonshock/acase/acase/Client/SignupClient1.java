package org.kidzonshock.acase.acase.Client;

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

public class SignupClient1 extends AppCompatActivity {

    Button btnRegisterNextClient;
    TextInputEditText inputRegFirstnameClient, inputRegLastnameClient, inputRegEmailClient;
    TextInputLayout layoutRegFirstnameClient, layoutRegLastnameClient, layoutRegEmailClient;
    String firstname,lastname,email;
    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_client1);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Sign-Up - Step 1");

        btnRegisterNextClient = findViewById(R.id.btnRegisterNextClient);
<<<<<<< HEAD
        inputRegFirstnameClient = findViewById(R.id.inputRegFirstnameLawyer);
        inputRegLastnameClient = findViewById(R.id.inputRegLastnameLawyer);
        inputRegEmailClient = findViewById(R.id.inputRegEmailLawyer);
=======
        inputRegFirstnameClient = findViewById(R.id.inputRegFirstnameClient);
        inputRegLastnameClient = findViewById(R.id.inputRegLastnameClient);
        inputRegEmailClient = findViewById(R.id.inputRegEmailClient);
>>>>>>> 8cfc75d15815cbbe9f51535eb0b1c3b77691a740

        layoutRegFirstnameClient = findViewById(R.id.layoutRegFirstnameClient);
        layoutRegLastnameClient = findViewById(R.id.layoutRegLastnameClient);
        layoutRegEmailClient = findViewById(R.id.layoutRegEmailClient);

        btnRegisterNextClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                firstname = inputRegFirstnameClient.getText().toString();
                lastname = inputRegLastnameClient.getText().toString();
                email = inputRegEmailClient.getText().toString();
                if(validateForm(firstname,lastname,email)){
                    Intent reg2 = new Intent(SignupClient1.this, SignupClient2.class);
                    reg2.putExtra("firstname", firstname);
                    reg2.putExtra("lastname", lastname);
                    reg2.putExtra("email", email);
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

    private boolean validateForm(String firstname,String lastname,String email) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            layoutRegEmailClient.setError("Required");
            layoutRegEmailClient.requestFocus();
            valid = false;
        } else {
            layoutRegEmailClient.setError(null);
        }

        if (TextUtils.isEmpty(lastname)) {
            layoutRegLastnameClient.setError("Required");
            layoutRegLastnameClient.requestFocus();
            valid = false;
        } else {
            layoutRegLastnameClient.setError(null);
        }

        if (TextUtils.isEmpty(firstname)) {
            layoutRegFirstnameClient.setError("Required");
            layoutRegFirstnameClient.requestFocus();
            valid = false;
        } else {
            layoutRegFirstnameClient.setError(null);
        }

        return valid;
    }

}
