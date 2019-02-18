package org.kidzonshock.acase.acase.Client;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.ClientListCase;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.CreateEventModelClient;
import org.kidzonshock.acase.acase.Models.DatePickerFragment;
import org.kidzonshock.acase.acase.Models.ListLawyer;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.TimePickerFragment;
import org.kidzonshock.acase.acase.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateEventClient extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    Button btnEventDate,btnEventTime, btnCreateEvent;
    TextInputLayout layoutEventTitle,layoutEventLocation, layoutEventDate, layoutEventTime;
    TextInputEditText inputEventTitle, inputEventLocation, inputEventDate, inputEventTime;
    EditText eventDetails;
    Spinner spinnerLawyer,spinnerEventType;
    ArrayList<String> spinnerLawyerArray;

    String lawyer_id,client_id;
    HashMap<String ,String> hmLawyer;
    ACProgressFlower dialog;
    LinearLayout loading;
    Bundle b;
    private final String TAG = "CreateEventClient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_client);

        client_id = PreferenceDataClient.getLoggedInClientid(CreateEventClient.this);
        loading = findViewById(R.id.linlaHeaderProgress);
        hmLawyer = new HashMap<String,String>();
        getLawyer();
        loading.setVisibility(View.VISIBLE);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event");

        dialog = new ACProgressFlower.Builder(CreateEventClient.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        btnCreateEvent = findViewById(R.id.btnCreateEvent);
        btnEventDate = findViewById(R.id.btnEventDate);
        btnEventTime = findViewById(R.id.btnEventTime);

        layoutEventTitle = findViewById(R.id.layoutEventTitle);
        layoutEventLocation = findViewById(R.id.layoutEventLocation);
        layoutEventDate = findViewById(R.id.layoutEventDate);
        layoutEventTime = findViewById(R.id.layoutEventTime);

        inputEventTitle = findViewById(R.id.inputEventTitle);
        inputEventLocation = findViewById(R.id.inputEventLocation);
        inputEventDate = findViewById(R.id.inputEventDate);
        inputEventTime = findViewById(R.id.inputEventTime);

        eventDetails = findViewById(R.id.eventDetails);

        spinnerLawyerArray = new ArrayList<String>();
        spinnerLawyerArray.add("Select Lawyer");
        spinnerEventType = findViewById(R.id.spinnerEventType);
        spinnerLawyer = findViewById(R.id.spinnerClient);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerLawyerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLawyer.setAdapter(adapter);


        btnEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lawyer_name,title,location,details,date,time,type;
                title = inputEventTitle.getText().toString();
                location = inputEventLocation.getText().toString();
                details = eventDetails.getText().toString();
                date = inputEventDate.getText().toString();
                time = inputEventTime.getText().toString();
                type = spinnerEventType.getSelectedItem().toString();
                lawyer_name = spinnerLawyer.getSelectedItem().toString();
                for(String key: hmLawyer.keySet()) {
                    if(hmLawyer.get(key).equals(lawyer_name)) {
                        lawyer_id = key;
                    }
                }
                if(validateForm(title,location,date,time)){
                    dialog.show();
                    createEvent(lawyer_id,title,location,details,date,time,type, client_id);
                }
            }
        });

    }

    private void createEvent(String lawyer_id, String title, String location, String details, String date, String time, String type, String owner) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.createEventClient(client_id,new CreateEventModelClient(lawyer_id,title,location,details,date,time,type,owner));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                dialog.dismiss();
                if(!resp.isError()){
                    eventDetails.setText("");
                    inputEventTime.setText("");
                    inputEventDate.setText("");
                    inputEventLocation.setText("");
                    inputEventTitle.setText("");
                    spinnerLawyer.setSelection(getIndex(spinnerLawyer,"Select Lawyer"));
                    spinnerEventType.setSelection(getIndex(spinnerEventType,"Meeting"));
                    Toast.makeText(CreateEventClient.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateEventClient.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CreateEventClient.this, "Error in creating EVENT", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currenDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        inputEventDate.setText(currenDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String format;
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        String time = hourOfDay + ":" + String.format("%02d",minute) + " "+format;
        inputEventTime.setText(time);
    }

    private void getLawyer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<ListLawyer> listLawyerCall = service.listLawyer(client_id);
        listLawyerCall.enqueue(new Callback<ListLawyer>() {
            @Override
            public void onResponse(Call<ListLawyer> call, Response<ListLawyer> response) {
                ListLawyer listLawyer = response.body();
                loading.setVisibility(View.GONE);
                if(!listLawyer.isError()){
                    ArrayList<ClientListCase> list_lawyers = response.body().getList_lawyers();
                    String lawyer_id,name;
                    for(int i=0; i < list_lawyers.size(); i++){
                        lawyer_id = list_lawyers.get(i).getLawyer_id();
                        name = list_lawyers.get(i).getLawyer().getFirst_name()+" "+list_lawyers.get(i).getLawyer().getLast_name();
                        spinnerLawyerArray.add(name);
                        hmLawyer.put(lawyer_id,name);
                    }
                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(CreateEventClient.this, listLawyer.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ListLawyer> call, Throwable t) {
                Toast.makeText(CreateEventClient.this, "Unable to list lawyers, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String title, String location, String date, String time){
        boolean valid = true;
        if (TextUtils.isEmpty(time)) {
            layoutEventTime.setError("Required");
            layoutEventTime.requestFocus();
            valid = false;
        } else {
            layoutEventTime.setError(null);
        }

        if (TextUtils.isEmpty(date)) {
            layoutEventDate.setError("Required");
            layoutEventDate.requestFocus();
            valid = false;
        } else {
            layoutEventDate.setError(null);
        }

        if (TextUtils.isEmpty(location)) {
            layoutEventLocation.setError("Required");
            layoutEventLocation.requestFocus();
            valid = false;
        } else {
            layoutEventLocation.setError(null);
        }

        if (TextUtils.isEmpty(title)) {
            layoutEventTitle.setError("Required");
            layoutEventTitle.requestFocus();
            valid = false;
        } else {
            layoutEventTitle.setError(null);
        }
        return valid;
    }
}
