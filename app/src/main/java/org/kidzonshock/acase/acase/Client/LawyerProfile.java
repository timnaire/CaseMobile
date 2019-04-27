package org.kidzonshock.acase.acase.Client;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kidzonshock.acase.acase.HttpDataHandler;
import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.Feedback;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LawyerProfile extends AppCompatActivity implements OnMapReadyCallback {

    TextView txtName, txtEmail, txtPhone, txtOffice;
    Button btnRateLawyer,btnCallLawyer,btnMessageLawyer;
    ImageView profile_pic;
    String lat,lng;
    String client_id,lawyer_id,name,email,phone,office,prof_url;
    private GoogleMap map;
    GoogleApiClient mGoogleApiClient;

    RatingBar ratingBar;
    Dialog rateDialog;
    Float rate;
    String feedback;

    private final String TAG = "LawyerProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_profile);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Information");

        client_id = PreferenceDataClient.getLoggedInClientid(LawyerProfile.this);

        txtName = findViewById(R.id.lawyerName);
        txtEmail = findViewById(R.id.lawyerEmail);
        txtOffice = findViewById(R.id.lawyerOfficeAddress);
        profile_pic = findViewById(R.id.lawyerProfile);

        btnRateLawyer = findViewById(R.id.btnRateLawyer);
        btnCallLawyer = findViewById(R.id.btnCallLawyer);
        btnMessageLawyer = findViewById(R.id.btnMessageLawyer);

        Intent prev = getIntent();
        lawyer_id = prev.getStringExtra("lawyer_id");
        name = prev.getStringExtra("name");
        email = prev.getStringExtra("email");
        phone = prev.getStringExtra("phone");
        office = prev.getStringExtra("office");
        prof_url = prev.getStringExtra("profile_pic");

        txtName.setText(name);
        txtEmail.setText(email);
        txtOffice.setText(office);

        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.icons8_administrator_male_48_color)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(prof_url).apply(options).into(profile_pic);

        btnRateLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rateDialog = new Dialog(LawyerProfile.this, R.style.FullHeightDialog);
                rateDialog.setContentView(R.layout.rate_dialog);
                rateDialog.setCancelable(true);
                ratingBar = rateDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(ratingBar.getRating());

                TextView lName = rateDialog.findViewById(R.id.lawName);
                final EditText lawyerFeedback = rateDialog.findViewById(R.id.lawyerFeedback);
                lName.setText(name);

                Button rateButton = rateDialog.findViewById(R.id.rank_dialog_button);
                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rate = ratingBar.getRating();
                        feedback = lawyerFeedback.getText().toString();
                        sendFeedback(rate,feedback);
                        rateDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rateDialog.show();
            }
        });

    }

    private void sendFeedback(Float rate, String feedback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.sendFeedback(client_id,new Feedback(lawyer_id,rate,feedback));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(response.isSuccessful() && !resp.isError()){
                    Toast.makeText(LawyerProfile.this, "Thank you for your feedback !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LawyerProfile.this, "Rate and feedback was not saved.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(LawyerProfile.this, "Unable to send feedback, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(googleServicesAvailable()){
            initMap();
        }
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment ) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        new GetCoordinates().execute(office.replace(" ","+"));
    }


    private class GetCoordinates extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();
                Log.d(TAG,"Lat :"+lat);
                Log.d(TAG,"Lng :"+lng);
                double latitude = Double.parseDouble(lat);
                double longitude = Double.parseDouble(lng);
                LatLng latLng = new LatLng(latitude,longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Lawyer's Office Address");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                map.addMarker(markerOptions);
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
