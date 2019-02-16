package org.kidzonshock.acase.acase.Lawyer;

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

import com.google.android.gms.common.internal.service.Common;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.CreateEventModelLawyer;
import org.kidzonshock.acase.acase.Models.DatePickerFragment;
import org.kidzonshock.acase.acase.Models.LawyerListCase;
import org.kidzonshock.acase.acase.Models.ListClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
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

public class CreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    Button btnEventDate,btnEventTime, btnCreateEvent;
    TextInputLayout layoutEventTitle,layoutEventLocation, layoutEventDate, layoutEventTime;
    TextInputEditText inputEventTitle, inputEventLocation, inputEventDate, inputEventTime;
    EditText eventDetails;
    Spinner spinnerClient,spinnerEventType;
    ArrayList<String> spinnerClientArray;

    String lawyer_id,client_id;
    HashMap<String ,String> hmClient;
    ACProgressFlower dialog;
    LinearLayout loading;

    Bundle b;
    ArrayAdapter<String> adapter;

    private final String TAG = "CreateEventModelLawyer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(CreateEvent.this);
        loading = findViewById(R.id.linlaHeaderProgress);
        hmClient = new HashMap<String,String>();
        getClients();
        loading.setVisibility(View.VISIBLE);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event");

        dialog = new ACProgressFlower.Builder(CreateEvent.this)
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

        spinnerClientArray = new ArrayList<String>();
        spinnerClientArray.add("Select Client");
        spinnerEventType = findViewById(R.id.spinnerEventType);
        spinnerClient = findViewById(R.id.spinnerClient);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerClientArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



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
                String client_name,title,location,details,date,time,type;
                title = inputEventTitle.getText().toString();
                location = inputEventLocation.getText().toString();
                details = eventDetails.getText().toString();
                date = inputEventDate.getText().toString();
                time = inputEventTime.getText().toString();
                type = spinnerEventType.getSelectedItem().toString();
                client_name = spinnerClient.getSelectedItem().toString();
                for(String key: hmClient.keySet()) {
                    if(hmClient.get(key).equals(client_name)) {
                        client_id = key;
                    }
                }
                if(validateForm(title,location,date,time)){
                    dialog.show();
                    if(btnCreateEvent.getText().toString().equals("Update Event")){
                        Intent prev = getIntent();
                        String event_id = prev.getStringExtra("event_id");
                        updateEvent(event_id,client_id,title,location,details,date,time,type,lawyer_id);
                    } else {
                        createEvent(client_id, title, location, details, date, time, type, lawyer_id);
                    }
                }
            }
        });

    }

    private void updateEvent(String eventId, String client_id,String title,String location, String details, String date, String time, String type, String lawyer_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.updateEventLawyer(lawyer_id,new UpdateEvent(eventId,client_id,title,location,details,date,time,type,lawyer_id));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(!resp.isError()){
                    Toast.makeText(CreateEvent.this, "Event Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateEvent.this, "Event was not updated.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG,"Error: "+ t.getMessage());
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

    private void createEvent(String client_id, String title, String location, String details, String date, String time, String type, String owner) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.createEventLawyer(lawyer_id,new CreateEventModelLawyer(client_id,title,location,details,date,time,type, owner));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                dialog.dismiss();
                if(!resp.isError()){
                    inputEventTime.setText("");
                    inputEventDate.setText("");
                    eventDetails.setText("");
                    inputEventLocation.setText("");
                    inputEventTitle.setText("");
                    spinnerClient.setSelection(getIndex(spinnerClient,"Select Client"));
                    spinnerEventType.setSelection(getIndex(spinnerEventType,"Meeting"));
                    Toast.makeText(CreateEvent.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateEvent.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(CreateEvent.this, "Error in creating EVENT", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void getClients() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Case service = retrofit.create(Case.class);
        Call<ListClient> listClientCall = service.listClient(lawyer_id);
        listClientCall.enqueue(new Callback<ListClient>() {
            @Override
            public void onResponse(Call<ListClient> call, Response<ListClient> response) {
                ListClient listClient = response.body();
                loading.setVisibility(View.GONE);
                if(!listClient.isError()){
                    ArrayList<LawyerListCase> list_clients = response.body().getList_clients();
                    String client_id,name;
                    b = getIntent().getExtras();
                    for(int i=0; i < list_clients.size(); i++){
                        client_id = list_clients.get(i).getClient_id();
                        name = list_clients.get(i).getClient().getFirst_name()+" "+list_clients.get(i).getClient().getLast_name();
                        spinnerClientArray.add(name);
                        hmClient.put(client_id,name);
                    }
                    spinnerClient.setAdapter(adapter);
                    if(b !=null){
                        spinnerClient.setSelection(getIndex(spinnerClient,b.getString("event_with")));
                        inputEventTitle.setText(b.getString("event_title"));
                        inputEventLocation.setText(b.getString("event_location"));
                        eventDetails.setText(b.getString("event_details"));
                        inputEventDate.setText(b.getString("event_date"));
                        inputEventTime.setText(b.getString("event_time"));
                        spinnerEventType.setSelection(getIndex(spinnerEventType,b.getString("event_type")));
                        btnCreateEvent.setText("Update Event");
                    }
                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(CreateEvent.this, listClient.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ListClient> call, Throwable t) {
                Toast.makeText(CreateEvent.this, "Unable to list clients, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
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
