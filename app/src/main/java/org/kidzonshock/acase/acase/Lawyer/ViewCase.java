package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.kidzonshock.acase.acase.R;

public class ViewCase extends AppCompatActivity {

    TextView txtTitle, txtDate, txtClient_name,txtClient_email,txtClient_phone,txtClient_address,txtCase_status;
    String title,date,client_name,client_email,client_phone,client_address,case_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_case);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Case Information");

        txtTitle = findViewById(R.id.caseTitle);
        txtDate = findViewById(R.id.caseDate);
        txtClient_name = findViewById(R.id.clientName);
        txtClient_email = findViewById(R.id.clientEmail);
        txtClient_phone = findViewById(R.id.clientPhone);
        txtClient_address = findViewById(R.id.clientAddress);
        txtCase_status = findViewById(R.id.caseStatus);

        Intent prev = getIntent();
        title = prev.getStringExtra("title");
        date = prev.getStringExtra("date");
        client_name = prev.getStringExtra("client_name");
        client_email = prev.getStringExtra("client_email");
        client_phone = prev.getStringExtra("client_phone");
        client_address = prev.getStringExtra("client_address");
        case_status = prev.getStringExtra("case_status");

        txtTitle.setText(title);
        txtDate.setText(date);
        txtClient_name.setText(client_name);
        txtClient_email.setText(client_email);
        txtClient_phone.setText(client_phone);
        txtClient_address.setText(client_address);
        txtCase_status.setText(case_status);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
