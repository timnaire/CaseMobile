package org.kidzonshock.acase.acase.Service;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.PreAppointResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyService extends IntentService {

    public static final String Accept = "Accept";
    public static final String Reject = "Reject";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String lawyer_id,client_id, relation_id, status;
        final String action = intent.getAction();
        if (Accept.equals(action)) {
            lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(MyService.this);
            client_id = intent.getStringExtra("client_id");
            relation_id = intent.getStringExtra("relation_id");
            status = "Accepted";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Case.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Case service = retrofit.create(Case.class);
            Call<ResponseBody> preAppointResponseCall = service.appointResponse(client_id,new PreAppointResponse(lawyer_id,relation_id,status));
            preAppointResponseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(MyService.this, "Client Accepted!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        } else if (Reject.equals(action)) {
            lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(MyService.this);
            client_id = intent.getStringExtra("client_id");
            relation_id = intent.getStringExtra("relation_id");
            status = "Rejected";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Case.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Case service = retrofit.create(Case.class);
            Call<ResponseBody> preAppointResponseCall = service.appointResponse(client_id,new PreAppointResponse(lawyer_id,relation_id,status));
            preAppointResponseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(MyService.this, "Client Rejected!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        } else {
            throw new IllegalArgumentException("Unsupported action: " + action);
        }

    }

    public void clientAccepted(){

    }
}
