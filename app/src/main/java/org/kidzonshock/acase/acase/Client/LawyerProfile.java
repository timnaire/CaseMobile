package org.kidzonshock.acase.acase.Client;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import org.kidzonshock.acase.acase.Models.DeleteFeedback;
import org.kidzonshock.acase.acase.Models.Feedback;
import org.kidzonshock.acase.acase.Models.Feedbacks;
import org.kidzonshock.acase.acase.Models.PreAppointment;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.SoloFeedback;
import org.kidzonshock.acase.acase.R;

import java.net.SocketOption;

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
    String client_id,lawyer_id,name,email,phone,office,prof_url,fid;
    private GoogleMap map;
    GoogleApiClient mGoogleApiClient;

    RatingBar ratingBar;
    Dialog rateDialog;
    Float rate;
    String feedback;
    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);

    String frate,fbody;
    LinearLayout loading;

    private final String TAG = "LawyerProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_profile);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lawyer Information");

        client_id = PreferenceDataClient.getLoggedInClientid(LawyerProfile.this);

//        loading = findViewById(R.id.linlaHeaderProgress);
//        loading.setVisibility(View.VISIBLE);

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
        fid = prev.getStringExtra("fid");

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<SoloFeedback> fb = service.getFeedback(client_id,fid);
        fb.enqueue(new Callback<SoloFeedback>() {
            @Override
            public void onResponse(Call<SoloFeedback> call, Response<SoloFeedback> response) {
                SoloFeedback resp = response.body();
//                loading.setVisibility(View.GONE);
                if(!resp.isError()) {
                    frate = resp.getFeedback().getRating();
                    fbody = resp.getFeedback().getFeedback();
                }
            }

            @Override
            public void onFailure(Call<SoloFeedback> call, Throwable t) {
//                loading.setVisibility(View.GONE);
            }
        });



        btnMessageLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                sendSMS(phone);
            }
        });

        btnCallLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                dialPhoneNumber(phone);
            }
        });

        btnRateLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);

                rateDialog = new Dialog(LawyerProfile.this, R.style.FullHeightDialog);
                rateDialog.setContentView(R.layout.rate_dialog);
                rateDialog.setCancelable(true);
                ratingBar = rateDialog.findViewById(R.id.dialog_ratingbar);

                if(frate != null){
                    ratingBar.setRating(Float.parseFloat(frate));
                } else {
                    ratingBar.setRating(ratingBar.getRating());
                }

                TextView lName = rateDialog.findViewById(R.id.lawName);
                final EditText lawyerFeedback = rateDialog.findViewById(R.id.lawyerFeedback);
                lName.setText(name);
                lawyerFeedback.setText(fbody);

                Button rateButton = rateDialog.findViewById(R.id.rank_dialog_button);
                Button deleteButton = rateDialog.findViewById(R.id.deleteFeedback);
                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rate = ratingBar.getRating();
                        feedback = lawyerFeedback.getText().toString();
                        sendFeedback(rate,feedback, fid);
                        rateDialog.dismiss();
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFeedback(fid);
                        rateDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rateDialog.show();
            }
        });

    }

    public void deleteFeedback(String fid){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> deleteFeedback = service.deleteFeedback(client_id,new DeleteFeedback(fid));
        deleteFeedback.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(!resp.isError()) {
                    Toast.makeText(LawyerProfile.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LawyerProfile.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(LawyerProfile.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void sendSMS(String phone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("smsto:"+phone));
//            sendIntent.setType("text/plain");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address",phone);
            smsIntent.putExtra("sms_body","");
            startActivity(smsIntent);
        }
    }

    private void sendFeedback(Float rate, String feedback, String fid){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.sendFeedback(client_id,new Feedback(lawyer_id,rate,feedback,fid));
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
