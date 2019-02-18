package org.kidzonshock.acase.acase.Client;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import org.kidzonshock.acase.acase.Lawyer.CreateEvent;
import org.kidzonshock.acase.acase.Models.ClientListCase;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.CreateEventModelClient;
import org.kidzonshock.acase.acase.Models.DatePickerFragment;
import org.kidzonshock.acase.acase.Models.ListLawyer;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.TimePickerFragment;
import org.kidzonshock.acase.acase.Models.UpdateEvent;
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

    Button btnEventDateClient,btnEventTimeClient, btnCreateEventClient;
    TextInputLayout layoutEventTitleClient,layoutEventLocationClient, layoutEventDateClient, layoutEventTimeClient;
    TextInputEditText inputEventTitleClient, inputEventLocationClient, inputEventDateClient, inputEventTimeClient;
    EditText eventDetailsClient;
    Spinner spinnerLawyer,spinnerEventType;
    ArrayList<String> spinnerLawyerArray;

    String lawyer_id,client_id;
    ArrayAdapter<String> adapter;
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

        btnCreateEventClient = findViewById(R.id.btnCreateEventClient);
        btnEventDateClient = findViewById(R.id.btnEventDateClient);
        btnEventTimeClient = findViewById(R.id.btnEventTimeClient);

        layoutEventTitleClient = findViewById(R.id.layoutEventTitleClient);
        layoutEventLocationClient = findViewById(R.id.layoutEventLocationClient);
        layoutEventDateClient = findViewById(R.id.layoutEventDateClient);
        layoutEventTimeClient = findViewById(R.id.layoutEventTimeClient);

        inputEventTitleClient = findViewById(R.id.inputEventTitleClient);
        inputEventLocationClient = findViewById(R.id.inputEventLocationClient);
        inputEventDateClient = findViewById(R.id.inputEventDateClient);
        inputEventTimeClient = findViewById(R.id.inputEventTimeClient);

        eventDetailsClient = findViewById(R.id.eventDetailsClient);

        spinnerLawyerArray = new ArrayList<String>();
        spinnerLawyerArray.add("Select Lawyer");
        spinnerEventType = findViewById(R.id.spinnerEventType);
        spinnerLawyer = findViewById(R.id.spinnerClient);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerLawyerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        btnEventDateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnEventTimeClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btnCreateEventClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lawyer_name,title,location,details,date,time,type;
                title = inputEventTitleClient.getText().toString();
                location = inputEventLocationClient.getText().toString();
                details = eventDetailsClient.getText().toString();
                date = inputEventDateClient.getText().toString();
                time = inputEventTimeClient.getText().toString();
                type = spinnerEventType.getSelectedItem().toString();
                lawyer_name = spinnerLawyer.getSelectedItem().toString();
                for(String key: hmLawyer.keySet()) {
                    if(hmLawyer.get(key).equals(lawyer_name)) {
                        lawyer_id = key;
                    }
                }
                if(validateForm(title,location,date,time)){
                    dialog.show();
                    if(btnCreateEventClient.getText().toString().equals("Update Event")){
                        Intent prev = getIntent();
                        String event_id = prev.getStringExtra("event_id");
                        updateEvent(event_id,lawyer_id,title,location,details,date,time,type,client_id);
                    } else {
                        createEvent(lawyer_id,title,location,details,date,time,type, client_id);
                    }
                }
            }
        });

    }

    private void updateEvent(String eventId, String lawyer_id,String title,String location, String details, String date, String time, String type, String client_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.updateEventClient(client_id,new UpdateEvent(eventId,lawyer_id,title,location,details,date,time,type,client_id));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                dialog.dismiss();
                if(!resp.isError()){
                    Toast.makeText(CreateEventClient.this, "Event Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateEventClient.this, "Event was not updated.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG,"Error: "+ t.getMessage());
                dialog.dismiss();
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
                    eventDetailsClient.setText("");
                    inputEventTimeClient.setText("");
                    inputEventDateClient.setText("");
                    inputEventLocationClient.setText("");
                    inputEventTitleClient.setText("");
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
        inputEventDateClient.setText(currenDateString);
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
        inputEventTimeClient.setText(time);
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
                    b = getIntent().getExtras();
                    String lawyer_id,name;
                    for(int i=0; i < list_lawyers.size(); i++){
                        lawyer_id = list_lawyers.get(i).getLawyer_id();
                        name = list_lawyers.get(i).getLawyer().getFirst_name()+" "+list_lawyers.get(i).getLawyer().getLast_name();
                        spinnerLawyerArray.add(name);
                        hmLawyer.put(lawyer_id,name);
                    }
                    spinnerLawyer.setAdapter(adapter);
                    if(b !=null){
                        spinnerLawyer.setSelection(getIndex(spinnerLawyer,b.getString("event_with")));
                        inputEventTitleClient.setText(b.getString("event_title"));
                        inputEventLocationClient.setText(b.getString("event_location"));
                        eventDetailsClient.setText(b.getString("event_details"));
                        inputEventDateClient.setText(b.getString("event_date"));
                        inputEventTimeClient.setText(b.getString("event_time"));
                        spinnerEventType.setSelection(getIndex(spinnerEventType,b.getString("event_type")));
                        btnCreateEventClient.setText("Update Event");
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
            layoutEventTimeClient.setError("Required");
            layoutEventTimeClient.requestFocus();
            valid = false;
        } else {
            layoutEventTimeClient.setError(null);
        }

        if (TextUtils.isEmpty(date)) {
            layoutEventDateClient.setError("Required");
            layoutEventDateClient.requestFocus();
            valid = false;
        } else {
            layoutEventDateClient.setError(null);
        }

        if (TextUtils.isEmpty(location)) {
            layoutEventLocationClient.setError("Required");
            layoutEventLocationClient.requestFocus();
            valid = false;
        } else {
            layoutEventLocationClient.setError(null);
        }

        if (TextUtils.isEmpty(title)) {
            layoutEventTitleClient.setError("Required");
            layoutEventTitleClient.requestFocus();
            valid = false;
        } else {
            layoutEventTitleClient.setError(null);
        }
        return valid;
    }
}
